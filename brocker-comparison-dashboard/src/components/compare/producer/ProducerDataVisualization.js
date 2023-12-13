import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import {
  addProducerTestInfoArrayAction,
  clearProducerTestInfoArrayAction,
} from "../../../redux/actions/testInfoArrayActions";
import { randomDatasetColor } from "../../util/ColorUtil";
import BASE_URL from "../../../connections/constants/BASE_URL";
import { signal } from "@preact/signals-react";
import ProgressBar from "../ProgressBar";
import { getDateFromTimestampString } from "../../util/DateUtil";
import LiveChart from "../chart/LiveChart";
import Counter from "../Counter";

function ProducerDataVisualization(props) {
  const dispatch = useDispatch();
  const [testStatus, setTestStatus] = useState(0);
  const [producedMessages, setProducedMessages] = useState(0);
  const [producedMsgChartData, setProducedMsgChartData] = useState([]);
  const [systemCpuChartData, setSystemCpuChartData] = useState([]);
  const [appCpuChartData, setAppCpuChartData] = useState([]);
  const [datasetColor, setDatasetColor] = useState(null);
  const [datasetName, setDatasetName] = useState(null);

  useEffect(() => {
    let eventSource;
    let producedMsgArray = [];
    let systemCpuArray = [];
    let appCpuArray = [];
    const testInfo = signal(null);
    const resetTestStates = () => {
      dispatch(clearProducerTestInfoArrayAction());
      setTestStatus(0);
      setProducedMessages(0);
      setProducedMsgChartData([]);
      setSystemCpuChartData([]);
      setAppCpuChartData([]);
      setDatasetColor(null);
      setDatasetName([]);
    };

    const startListenDebugInfoMessageStream = () => {
      eventSource = new EventSource(
        BASE_URL.PRODUCER_STREAM_DATA_API_URL + "/debug-info"
      );
      eventSource.onopen = (event) => {
        console.log("START PRODUCER LISTEN DEBUG INFO STREAM");
      };
      eventSource.onmessage = (event) => {
        if (event.data) {
          const debugInfo = JSON.parse(event.data);
          upateChartData(
            debugInfo,
            producedMsgArray,
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
          "PRODUCER EVENTSOURCE CLOSED (" + event.target.readyState + ")"
        );
      }
      eventSource.close();
    };

    const endListenDebugInfoMessageStream = () => {
      eventSource.close();
      console.log("STOP PRODUCER LISTEN DEBUG INFO DATA STREAM");
      saveData();
      testInfo.value = null;
    };

    async function saveData() {
      dispatch(addProducerTestInfoArrayAction(testInfo.value));
    }

    const upateChartData = (
      debugInfo,
      producedMsgArray,
      systemCpuArray,
      appCpuArray
    ) => {
      setTestStatus(debugInfo.testStatusPercentage);
      setProducedMessages(debugInfo.countOfProducedMessages);
      let producedMsgData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.countOfProducedMessages,
      };
      producedMsgArray.push(producedMsgData);
      setProducedMsgChartData([...producedMsgArray]);
      let systemCpuData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.producerCPUMetrics.systemCpuUsagePercentage,
      };
      systemCpuArray.push(systemCpuData);
      setSystemCpuChartData([...systemCpuArray]);
      let appCpuData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.producerCPUMetrics.appCpuUsagePercentage,
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
      <ProgressBar testStatus={testStatus} />
      <Counter name={"Produced messages"} value={producedMessages} />
      <LiveChart
        data={producedMsgChartData}
        datasetColor={datasetColor}
        datasetName={datasetName}
        chartName={"Produced messages in time"}
        yAxisName={"Produced messages"}
      />
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
  );
}

export default ProducerDataVisualization;
