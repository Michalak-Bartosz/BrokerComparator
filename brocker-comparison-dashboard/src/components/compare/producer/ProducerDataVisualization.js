import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import {
  addProducerTestInfoArrayAction,
  clearProducerTestInfoArrayAction,
} from "../../../redux/actions/testInfoArrayActions";
import BASE_URL from "../../../connections/constants/BASE_URL";
import ProgressBar from "../ProgressBar";
import Counter from "../Counter";
import LiveChart from "../chart/LiveChart";
import { randomDatasetColor } from "../../util/ColorUtil";
import { getDateFromTimestampString } from "../../util/DateUtil";
import TestStatus from "../TestStatus";

function ProducerDataVisualizationTEST(props) {
  const dispatch = useDispatch();
  const [datasetName, setDatasetName] = useState("");
  const [testStatus, setTestStatus] = useState(0);

  const [receivedMsgData, setReceivedMsgData] = useState([]);
  const [initialMemoryData, setInitialMemoryData] = useState([]);
  const [usedHeapMemoryData, setUsedHeapMemoryData] = useState([]);
  const [maxHeapMemoryData, setMaxHeapMemoryData] = useState([]);
  const [committedMemoryData, setCommittedMemoryData] = useState([]);
  const [systemCpuData, setSystemCpuData] = useState([]);
  const [appCpuData, setAppCpuData] = useState([]);

  const [chartDataArray, setChartDataArray] = useState([]);

  useEffect(() => {
    let eventSource;
    let receivedMsgArray = [];
    let initMemoArray = [];
    let usedHeapMemoArray = [];
    let maxHeapMemoArray = [];
    let commitedMemoArray = [];
    let systemCpuArray = [];
    let appCpuArray = [];

    const updateTestStatus = (debugInfo) => {
      setTestStatus(debugInfo.testStatusPercentage);
    };

    const updateProducedMsgDataArray = (debugInfo) => {
      let producedMsgData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.countOfProducedMessages,
      };
      receivedMsgArray.push(producedMsgData);
      setReceivedMsgData([...receivedMsgArray]);
    };

    const updateSystemCpuDataArray = (debugInfo) => {
      let systemCpuData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.producerCPUMetrics.systemCpuUsagePercentage,
      };
      systemCpuArray.push(systemCpuData);
      setSystemCpuData([...systemCpuArray]);
    };

    const updateAppCpuData = (debugInfo) => {
      let appCpuData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.producerCPUMetrics.appCpuUsagePercentage,
      };
      appCpuArray.push(appCpuData);
      setAppCpuData([...appCpuArray]);
    };

    const updateInitialMemoryData = (debugInfo) => {
      let initMemoData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.producerMemoryMetrics.initialMemoryGB,
      };
      initMemoArray.push(initMemoData);
      setInitialMemoryData([...initMemoArray]);
    };

    const updateUsedHeapMemoryData = (debugInfo) => {
      let usedHeapMemoData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.producerMemoryMetrics.usedHeapMemoryGB,
      };
      usedHeapMemoArray.push(usedHeapMemoData);
      setUsedHeapMemoryData([...usedHeapMemoArray]);
    };

    const updateMaxHeapMemoryData = (debugInfo) => {
      let maxHeapMemoData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.producerMemoryMetrics.maxHeapMemoryGB,
      };
      maxHeapMemoArray.push(maxHeapMemoData);
      setMaxHeapMemoryData([...maxHeapMemoArray]);
    };

    const updateCommittedMemoryData = (debugInfo) => {
      let committedMemoData = {
        xVal: getDateFromTimestampString(debugInfo.producedTimestamp),
        yVal: debugInfo.producerMemoryMetrics.committedMemoryGB,
      };
      commitedMemoArray.push(committedMemoData);
      setCommittedMemoryData([...commitedMemoArray]);
    };

    const updateChartData = (debugInfo) => {
      updateTestStatus(debugInfo);
      updateProducedMsgDataArray(debugInfo);
      updateSystemCpuDataArray(debugInfo);
      updateAppCpuData(debugInfo);
      updateInitialMemoryData(debugInfo);
      updateUsedHeapMemoryData(debugInfo);
      updateMaxHeapMemoryData(debugInfo);
      updateCommittedMemoryData(debugInfo);
    };

    const getNewChartDataArrayValue = (brokerType) => {
      return {
        datasetName: getDatasetName(brokerType),
        datasetColor: randomDatasetColor(),
        receivedMsgData: receivedMsgArray,
        initialMemoryData: initMemoArray,
        usedHeapMemoryData: usedHeapMemoArray,
        maxHeapMemoryData: maxHeapMemoArray,
        committedMemoryData: commitedMemoArray,
        systemCpuData: systemCpuArray,
        appCpuData: appCpuArray,
      };
    };

    const updateChartDataArray = (brokerType) => {
      if (chartDataArray.length === 0) {
        setChartDataArray([getNewChartDataArrayValue(brokerType)]);
      } else {
        setChartDataArray((prevValue) => [
          ...prevValue,
          getNewChartDataArrayValue(brokerType),
        ]);
      }
    };

    const getDatasetName = (brokerType) => {
      return (
        "Broker Type: '" + brokerType + "' Test UUID: '" + props.testUUID + "'"
      );
    };

    const resetTestStates = () => {
      dispatch(clearProducerTestInfoArrayAction());
    };

    const startListenDebugInfoMessageStream = (brokerType) => {
      let totalMessagesInTest =
        props.numberOfMessagesToSend * props.numberOfAttempts;
      let receivedMsgCounter = 0;
      props.setIsInProgress(true);
      setDatasetName(getDatasetName(brokerType));
      eventSource = new EventSource(
        BASE_URL.PRODUCER_STREAM_DATA_API_URL + "/debug-info"
      );
      eventSource.onopen = (event) => {
        console.log(
          "START PRODUCER LISTEN DEBUG INFO STREAM. Broker Type: " + brokerType
        );
      };
      eventSource.onmessage = (event) => {
        if (event.data) {
          const debugInfo = JSON.parse(event.data);
          updateChartData(debugInfo);
          receivedMsgCounter++;
          if (receivedMsgCounter === totalMessagesInTest) {
            endListenDebugInfoMessageStream(brokerType);
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

    const endListenDebugInfoMessageStream = (brokerType) => {
      console.log("STOP PRODUCER LISTEN DEBUG INFO DATA STREAM.");
      updateChartDataArray(brokerType);
      props.setIsInProgress(false);
      eventSource.close();
      saveData();
    };

    async function saveData() {
      // dispatch(addProducerTestInfoArrayAction(testInfo)); TODO
    }

    const handleStartTest = () => {
      props.brokerTypes.forEach((brokerType) => {
        resetTestStates();
        startListenDebugInfoMessageStream(brokerType);
      });
    };

    if (props.testUUID) {
      handleStartTest();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.testUUID]);

  const getTestProgressStatus = () => {
    return testStatus;
  };

  const getProducedMessages = () => {
    return receivedMsgData?.at(-1) ? receivedMsgData?.at(-1).yVal : 0;
  };

  const createChartDataElement = (data, datasetName, datasetColor) => {
    return {
      dataArray: data,
      datasetName: datasetName,
      datasetColor: datasetColor,
    };
  };

  const getProducedMessagesChartData = () => {
    if (props.isInProgress) {
      return [createChartDataElement(receivedMsgData, datasetName)];
    }
    return [
      ...chartDataArray.map((chartData) =>
        createChartDataElement(
          chartData.receivedMsgData,
          chartData.datasetName,
          chartData.datasetColor
        )
      ),
    ];
  };

  const getInitialMemoryChartData = () => {
    if (props.isInProgress) {
      return [createChartDataElement(initialMemoryData, datasetName)];
    }
    return [
      ...chartDataArray.map((chartData) =>
        createChartDataElement(
          chartData.initialMemoryData,
          chartData.datasetName,
          chartData.datasetColor
        )
      ),
    ];
  };

  const getUsedHeapMemoryChartData = () => {
    if (props.isInProgress) {
      return [createChartDataElement(usedHeapMemoryData, datasetName)];
    }
    return [
      ...chartDataArray.map((chartData) =>
        createChartDataElement(
          chartData.usedHeapMemoryData,
          chartData.datasetName,
          chartData.datasetColor
        )
      ),
    ];
  };

  const getMaxHeapMemoryChartData = () => {
    if (props.isInProgress) {
      return [createChartDataElement(maxHeapMemoryData, datasetName)];
    }
    return [
      ...chartDataArray.map((chartData) =>
        createChartDataElement(
          chartData.maxHeapMemoryData,
          chartData.datasetName,
          chartData.datasetColor
        )
      ),
    ];
  };

  const getCommittedMemoryChartData = () => {
    if (props.isInProgress) {
      return [createChartDataElement(committedMemoryData, datasetName)];
    }
    return [
      ...chartDataArray.map((chartData) =>
        createChartDataElement(
          chartData.committedMemoryData,
          chartData.datasetName,
          chartData.datasetColor
        )
      ),
    ];
  };

  const getSystemCpuChartData = () => {
    if (props.isInProgress) {
      return [createChartDataElement(systemCpuData, datasetName)];
    }
    return [
      ...chartDataArray.map((chartData) =>
        createChartDataElement(
          chartData.systemCpuData,
          chartData.datasetName,
          chartData.datasetColor
        )
      ),
    ];
  };

  const getAppCpuChartData = () => {
    if (props.isInProgress) {
      return [createChartDataElement(appCpuData, datasetName)];
    }
    return [
      ...chartDataArray.map((chartData) =>
        createChartDataElement(
          chartData.appCpuData,
          chartData.datasetName,
          chartData.datasetColor
        )
      ),
    ];
  };

  return (
    <div className="bg-lime-200 bg-opacity-40 rounded-lg p-4 mb-8 shadow-lg">
      <div className="block p-8">
        <h1 className="text-3xl font-bold text-lime-800 rounded-md border-y-4 border-slate-600 py-4 m-auto">
          Producer App
        </h1>
        <ProgressBar testStatus={getTestProgressStatus()} />
        <div className="grid grid-cols-2 gap-6">
          <TestStatus
            isInProgress={props.isInProgress}
            testStatus={getTestProgressStatus()}
          />
          <Counter name={"Produced messages"} value={getProducedMessages()} />
        </div>
        <LiveChart
          chartData={getProducedMessagesChartData()}
          chartName={"Produced messages in time"}
          yAxisName={"Produced messages"}
          heightPercentage={"25%"}
        />
        <div>
          <h1 className="font-bold text-2xl text-lime-800 rounded-md border-y-2 border-slate-600 py-4 m-auto">
            Memory Metrics
          </h1>
          <div className="grid grid-cols-2 py-4">
            <LiveChart
              chartData={getInitialMemoryChartData()}
              chartName={"Initial memory [GB] in time"}
              yAxisName={"Initial memory [GB]"}
              heightPercentage={"45%"}
            />
            <LiveChart
              chartData={getUsedHeapMemoryChartData()}
              chartName={"Used Heap Memory [GB] in time"}
              yAxisName={"Used Heap Memory [GB]"}
              heightPercentage={"45%"}
            />
            <LiveChart
              chartData={getMaxHeapMemoryChartData()}
              chartName={"Max Heap Memory [GB] in time"}
              yAxisName={"Max Heap Memory [GB]"}
              heightPercentage={"45%"}
            />
            <LiveChart
              chartData={getCommittedMemoryChartData()}
              chartName={"Committed Memory [GB] in time"}
              yAxisName={"Committed Memory [GB]"}
              heightPercentage={"45%"}
            />
          </div>
        </div>
        <div>
          <h1 className="font-bold text-2xl text-lime-800 rounded-md border-y-2 border-slate-600 py-4 m-auto">
            CPU metrics
          </h1>
          <div className="grid grid-cols-2 py-4">
            <LiveChart
              chartData={getSystemCpuChartData()}
              chartName={"System CPU usage [%] in time"}
              yAxisName={"CPU usage [%]"}
              heightPercentage={"45%"}
            />
            <LiveChart
              chartData={getAppCpuChartData()}
              chartName={"Consumer App CPU usage [%] in time"}
              yAxisName={"CPU usage [%]"}
              heightPercentage={"45%"}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProducerDataVisualizationTEST;
