import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import {
  addConsumerTestInfoArrayAction,
  clearConsumerTestInfoArrayAction,
} from "../../../redux/actions/testInfoArrayActions";
import { randomDatasetColor } from "../../util/ColorUtil";
import BASE_URL from "../../../connections/constants/BASE_URL";
import { signal } from "@preact/signals-react";
import ProgressBar from "../ProgressBar";
import { getDateFromTimestampString } from "../../util/DateUtil";
import LiveChart from "../chart/LiveChart";
import Counter from "../Counter";
import UsageBar from "../UsageBar";

function ConsumerDataVisualization(props) {
  const dispatch = useDispatch();
  const [testStatus, setTestStatus] = useState(0);
  const [consumedMessages, setConsumedMessages] = useState(0);
  const [consumedMsgChartData, setConsumedMsgChartData] = useState([]);
  const [systemCpuChartData, setSystemCpuChartData] = useState([]);
  const [appCpuChartData, setAppCpuChartData] = useState([]);
  const [datasetColor, setDatasetColor] = useState(null);
  const [datasetName, setDatasetName] = useState(null);
  const [initialMemoryGB, setInitialMemoryGB] = useState(0);
  const [usedHeapMemoryGB, setUsedHeapMemoryGB] = useState(0);
  const [maxHeapMemoryGB, setMaxHeapMemoryGB] = useState(0);
  const [committedMemoryGB, setCommittedMemoryGB] = useState(0);

  useEffect(() => {
    let eventSource;
    let consumedMsgArray = [];
    let systemCpuArray = [];
    let appCpuArray = [];
    const testInfo = signal(null);
    const resetTestStates = () => {
      dispatch(clearConsumerTestInfoArrayAction());
      setTestStatus(0);
      setConsumedMessages(0);
      setConsumedMsgChartData([]);
      setSystemCpuChartData([]);
      setAppCpuChartData([]);
      setDatasetColor(null);
      setDatasetName([]);
    };

    const startListenDebugInfoMessageStream = () => {
      eventSource = new EventSource(
        BASE_URL.CONSUMER_STREAM_DATA_API_URL + "/debug-info"
      );
      eventSource.onopen = (event) => {
        console.log("START CONSUMER LISTEN DEBUG INFO STREAM");
      };
      eventSource.onmessage = (event) => {
        if (event.data) {
          const debugInfo = JSON.parse(event.data);
          upateChartData(
            debugInfo,
            consumedMsgArray,
            systemCpuArray,
            appCpuArray
          );
          addTestInfoElement(debugInfo);
          if (debugInfo.testStatusPercentage === 100) {
            endListenDebugInfoMessageStream();
          }
        }
      };
      eventSource.onerror = (event) => {
        errorHandler(event);
      };
    };

    const errorHandler = (event) => {
      console.log(event.target.readyState);
      if (event.target.readyState === EventSource.CLOSED) {
        console.log(
          "CONSUMER EVENTSOURCE CLOSED (" + event.target.readyState + ")"
        );
      }
      eventSource.close();
    };

    const endListenDebugInfoMessageStream = () => {
      eventSource.close();
      console.log("STOP CONSUMER LISTEN DEBUG INFO DATA STREAM");
      saveData();
      testInfo.value = null;
    };

    async function saveData() {
      dispatch(addConsumerTestInfoArrayAction(testInfo.value));
    }

    const upateChartData = (
      debugInfo,
      consumedMsgArray,
      systemCpuArray,
      appCpuArray
    ) => {
      setTestStatus(debugInfo.testStatusPercentage);
      setInitialMemoryGB(debugInfo.consumerMemoryMetrics.initialMemoryGB);
      setUsedHeapMemoryGB(debugInfo.consumerMemoryMetrics.usedHeapMemoryGB);
      setMaxHeapMemoryGB(debugInfo.consumerMemoryMetrics.maxHeapMemoryGB);
      setCommittedMemoryGB(debugInfo.consumerMemoryMetrics.committedMemoryGB);
      setConsumedMessages(debugInfo.countOfConsumedMessages);
      let consumedMsgData = {
        xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
        yVal: debugInfo.countOfConsumedMessages,
      };
      consumedMsgArray.push(consumedMsgData);
      setConsumedMsgChartData([...consumedMsgArray]);
      let systemCpuData = {
        xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
        yVal: debugInfo.consumerCPUMetrics.systemCpuUsagePercentage,
      };
      systemCpuArray.push(systemCpuData);
      setSystemCpuChartData([...systemCpuArray]);
      let appCpuData = {
        xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
        yVal: debugInfo.consumerCPUMetrics.appCpuUsagePercentage,
      };
      appCpuArray.push(appCpuData);
      setAppCpuChartData([...appCpuArray]);
    };

    const createTestInfo = (testUUID) => {
      return {
        testUUID: testUUID,
        values: null,
        datasetColor: randomDatasetColor(),
      };
    };

    const addTestInfoElement = (debugInfo) => {
      if (testInfo.value.values) {
        testInfo.value.values = [...testInfo.value.values, debugInfo];
      } else {
        testInfo.value.values = [debugInfo];
      }
    };

    const handleStartTest = () => {
      resetTestStates();
      testInfo.value = createTestInfo(props.testUUID);
      setDatasetName("Test UUID: " + props.testUUID);
      setDatasetColor(randomDatasetColor());
      startListenDebugInfoMessageStream();
    };

    if (props.testUUID !== null) {
      handleStartTest();
    }
  }, [dispatch, props.testUUID]);

  return (
    <div className="block">
      <h1 className="text-3xl font-bold text-teal-600 rounded-md border-y-4 border-slate-600 py-4 m-auto mx-8">
        Consumer App
      </h1>
      <ProgressBar testStatus={testStatus} />
      <Counter name={"Consumed massages"} value={consumedMessages} />
      <LiveChart
        data={consumedMsgChartData}
        datasetColor={datasetColor}
        datasetName={datasetName}
        chartName={"Consumed message in time"}
        yAxisName={"Consumed messages"}
      />
      <div>
        <h1 className="font-bold text-2xl text-teal-600 rounded-md border-y-2 border-slate-600 py-4 m-auto mx-8">
          Memory Metrics
        </h1>
        <div className="py-4">
          <UsageBar
            usageLabel={"Initial Memory"}
            usageStatus={initialMemoryGB}
          />
          <UsageBar
            usageLabel={"Used Heap Memory"}
            usageStatus={usedHeapMemoryGB}
          />
          <UsageBar
            usageLabel={"Max Heap Memory"}
            usageStatus={maxHeapMemoryGB}
          />
          <UsageBar
            usageLabel={"Committed Memory"}
            usageStatus={committedMemoryGB}
          />
        </div>
      </div>
      <div>
        <h1 className="font-bold text-2xl text-teal-600 rounded-md border-y-2 border-slate-600 py-4 m-auto mx-8">
          CPU Metrics
        </h1>
        <div className="flex">
          <div className="w-1/2">
            <LiveChart
              data={systemCpuChartData}
              datasetColor={datasetColor}
              datasetName={datasetName}
              chartName={"System CPU usage [%] in time"}
              yAxisName={"CPU usage [%]"}
            />
          </div>
          <div className="w-1/2">
            <LiveChart
              data={appCpuChartData}
              datasetColor={datasetColor}
              datasetName={datasetName}
              chartName={"Consumer App CPU usage [%] in time"}
              yAxisName={"CPU usage [%]"}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default ConsumerDataVisualization;
