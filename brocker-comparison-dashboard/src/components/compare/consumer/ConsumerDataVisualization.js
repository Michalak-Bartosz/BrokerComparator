import React, { useEffect, useState } from "react";
import BASE_URL from "../../../connections/constants/BASE_URL";
import ProgressBar from "../progress/ProgressBar";
import Counter from "../progress/Counter";
import LiveChart from "../chart/LiveChart";
import { randomDatasetColor } from "../../util/ColorUtil";
import {
  getDateFromTimestampString,
  getDurationInMilliseconds,
} from "../../util/DateTimeUtil";
import TestStatus from "../progress/TestStatus";

function ConsumerDataVisualization(props) {
  const [datasetName, setDatasetName] = useState("");
  const [testStatus, setTestStatus] = useState(0);

  const [receivedMsgData, setReceivedMsgData] = useState([]);
  const [deltaTimeData, setDeltaTimeData] = useState([]);
  const [initialMemoryData, setInitialMemoryData] = useState([]);
  const [usedHeapMemoryData, setUsedHeapMemoryData] = useState([]);
  const [maxHeapMemoryData, setMaxHeapMemoryData] = useState([]);
  const [committedMemoryData, setCommittedMemoryData] = useState([]);
  const [systemCpuData, setSystemCpuData] = useState([]);
  const [appCpuData, setAppCpuData] = useState([]);

  const [chartDataArray, setChartDataArray] = useState([]);

  let receivedMsgArray = [];
  let deltaTimeDataArray = [];
  let initMemoArray = [];
  let usedHeapMemoArray = [];
  let maxHeapMemoArray = [];
  let commitedMemoArray = [];
  let systemCpuArray = [];
  let appCpuArray = [];
  let chartDataArrayValue = [];

  const resetTestStates = () => {
    receivedMsgArray = [];
    deltaTimeDataArray = [];
    initMemoArray = [];
    usedHeapMemoArray = [];
    maxHeapMemoArray = [];
    commitedMemoArray = [];
    systemCpuArray = [];
    appCpuArray = [];
  };

  const updateConsumedMsgDataArray = (debugInfo) => {
    let consumedMsgData = {
      xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
      yVal: debugInfo.countOfConsumedMessages,
    };
    receivedMsgArray.push(consumedMsgData);
    setReceivedMsgData([...receivedMsgArray]);
  };

  const updateDeltaTimeDataArray = (debugInfo) => {
    let deltaTimeData = {
      xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
      yVal: getDurationInMilliseconds(debugInfo.deltaTimestamp),
    };
    deltaTimeDataArray.push(deltaTimeData);
    setDeltaTimeData([...deltaTimeDataArray]);
  };

  const updateSystemCpuDataArray = (debugInfo) => {
    let systemCpuData = {
      xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
      yVal: debugInfo.consumerCPUMetrics.systemCpuUsagePercentage,
    };
    systemCpuArray.push(systemCpuData);
    setSystemCpuData([...systemCpuArray]);
  };

  const updateAppCpuData = (debugInfo) => {
    let appCpuData = {
      xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
      yVal: debugInfo.consumerCPUMetrics.appCpuUsagePercentage,
    };
    appCpuArray.push(appCpuData);
    setAppCpuData([...appCpuArray]);
  };

  const updateInitialMemoryData = (debugInfo) => {
    let initMemoData = {
      xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
      yVal: debugInfo.consumerMemoryMetrics.initialMemoryGB,
    };
    initMemoArray.push(initMemoData);
    setInitialMemoryData([...initMemoArray]);
  };

  const updateUsedHeapMemoryData = (debugInfo) => {
    let usedHeapMemoData = {
      xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
      yVal: debugInfo.consumerMemoryMetrics.usedHeapMemoryGB,
    };
    usedHeapMemoArray.push(usedHeapMemoData);
    setUsedHeapMemoryData([...usedHeapMemoArray]);
  };

  const updateMaxHeapMemoryData = (debugInfo) => {
    let maxHeapMemoData = {
      xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
      yVal: debugInfo.consumerMemoryMetrics.maxHeapMemoryGB,
    };
    maxHeapMemoArray.push(maxHeapMemoData);
    setMaxHeapMemoryData([...maxHeapMemoArray]);
  };

  const updateCommittedMemoryData = (debugInfo) => {
    let committedMemoData = {
      xVal: getDateFromTimestampString(debugInfo.consumedTimestamp),
      yVal: debugInfo.consumerMemoryMetrics.committedMemoryGB,
    };
    commitedMemoArray.push(committedMemoData);
    setCommittedMemoryData([...commitedMemoArray]);
  };

  const getDatasetName = (brokerType, testUUID) => {
    let currentTestUUID = testUUID ? testUUID : props.testUUID;
    return (
      "Broker Type: '" + brokerType + "' Test UUID: '" + currentTestUUID + "'"
    );
  };

  const getNewChartDataArrayValue = (brokerType, testUUID) => {
    return {
      datasetName: getDatasetName(brokerType, testUUID),
      datasetColor: randomDatasetColor(),
      receivedMsgData: receivedMsgArray,
      deltaTimeData: deltaTimeDataArray,
      initialMemoryData: initMemoArray,
      usedHeapMemoryData: usedHeapMemoArray,
      maxHeapMemoryData: maxHeapMemoArray,
      committedMemoryData: commitedMemoArray,
      systemCpuData: systemCpuArray,
      appCpuData: appCpuArray,
    };
  };

  const updateChartData = (debugInfo) => {
    updateConsumedMsgDataArray(debugInfo);
    updateDeltaTimeDataArray(debugInfo);
    updateSystemCpuDataArray(debugInfo);
    updateAppCpuData(debugInfo);
    updateInitialMemoryData(debugInfo);
    updateUsedHeapMemoryData(debugInfo);
    updateMaxHeapMemoryData(debugInfo);
    updateCommittedMemoryData(debugInfo);
  };

  useEffect(() => {
    const fillOutChartDataByBrokerType = (brokerTypeList, debugInfoList) => {
      brokerTypeList?.forEach((brokerType) => {
        const testUUID = debugInfoList[0].testUUID;
        debugInfoList
          .filter((debugInfo) => debugInfo.brokerType === brokerType)
          .sort(function (a, b) {
            return a.testStatusPercentage - b.testStatusPercentage;
          })
          .map((debugInfo) => updateChartData(debugInfo));
        chartDataArrayValue.push(
          getNewChartDataArrayValue(brokerType, testUUID)
        );
        resetTestStates();
      });
      setChartDataArray([...new Set(chartDataArrayValue)]);
    };

    const sortFocusedTestReportArray = () => {
      return props.focusedTestReportArray.sort(function (a, b) {
        return (
          getDateFromTimestampString(a.debugInfoList?.at(0).producedTimestamp) -
          getDateFromTimestampString(b.debugInfoList?.at(0).producedTimestamp)
        );
      });
    };

    if (props.focusedTestReportArray.length > 0) {
      resetTestStates();
      sortFocusedTestReportArray().forEach((testReport) => {
        fillOutChartDataByBrokerType(
          testReport.brokerTypeList,
          testReport.debugInfoList
        );
      });
    } else {
      setChartDataArray([]);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.focusedTestReportArray]);

  useEffect(() => {
    let eventSource;

    const updateTestStatus = (debugInfo) => {
      setTestStatus(debugInfo.testStatusPercentage);
    };

    const updateChartDataArray = (brokerType) => {
      chartDataArrayValue.push(getNewChartDataArrayValue(brokerType));
      if (chartDataArray.length === 0) {
        setChartDataArray([...new Set(chartDataArrayValue)]);
      } else {
        setChartDataArray((prevArray) => [
          ...new Set([...prevArray, ...chartDataArrayValue]),
        ]);
      }
    };

    const calculateTotalMessagesInTest = () => {
      return (
        props.numberOfMessagesToSend *
        props.numberOfAttempts *
        props.brokerTypes.length
      );
    };

    const handleNewBrokerType = (currentBrokerType, newBrokerType) => {
      if (currentBrokerType && currentBrokerType !== newBrokerType) {
        updateChartDataArray(currentBrokerType);
        resetTestStates();
      }
    };

    const startListenDebugInfoMessageStream = () => {
      let totalMessagesInTest = calculateTotalMessagesInTest();
      let receivedMsgCounter = 0;
      let currentBrokerType;
      eventSource = new EventSource(
        BASE_URL.CONSUMER_STREAM_DATA_API_URL + "/debug-info"
      );

      eventSource.onopen = (event) => {
        console.log("START CONSUMER LISTEN DEBUG INFO STREAM.");
      };

      eventSource.onmessage = (event) => {
        if (event.data) {
          const debugInfo = JSON.parse(event.data);
          let newBrokerType = debugInfo.brokerType;
          handleNewBrokerType(currentBrokerType, newBrokerType);
          currentBrokerType = newBrokerType;
          setDatasetName(getDatasetName(currentBrokerType));
          updateTestStatus(debugInfo);
          updateChartData(debugInfo);
          receivedMsgCounter++;
          if (receivedMsgCounter === totalMessagesInTest) {
            endListenDebugInfoMessageStream(currentBrokerType);
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
      props.setIsInProgress(false);
      eventSource.close();
    };

    const endListenDebugInfoMessageStream = (brokerType) => {
      console.log("STOP CONSUMER LISTEN DEBUG INFO DATA STREAM.");
      updateChartDataArray(brokerType);
      props.setIsInProgress(false);
      eventSource.close();
    };

    const handleStartTest = () => {
      resetTestStates();
      startListenDebugInfoMessageStream();
    };

    if (props.isInProgress) {
      handleStartTest();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.isInProgress]);

  const getTestProgressStatus = () => {
    return testStatus;
  };

  const getConsumedMessages = () => {
    return receivedMsgData?.at(-1) ? receivedMsgData?.at(-1).yVal : 0;
  };

  const createChartDataElement = (data, datasetName, datasetColor) => {
    return {
      dataArray: data,
      datasetName: datasetName,
      datasetColor: datasetColor,
    };
  };

  const getConsumedMessagesChartData = () => {
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

  const getDeltaTimeChartData = () => {
    if (props.isInProgress) {
      return [createChartDataElement(deltaTimeData, datasetName)];
    }
    return [
      ...chartDataArray.map((chartData) =>
        createChartDataElement(
          chartData.deltaTimeData,
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
    <div className="bg-teal-200 bg-opacity-40 rounded-lg p-4 shadow-lg">
      <div className="block p-8">
        <h1 className="text-3xl font-bold text-teal-800 rounded-md border-y-4 border-slate-600 py-4 m-auto">
          Consumer App
        </h1>
        <ProgressBar testStatus={getTestProgressStatus()} />
        <div className="grid grid-cols-2 gap-6">
          <TestStatus
            isInProgress={props.isInProgress}
            testStatus={getTestProgressStatus()}
          />
          <Counter name={"Produced messages"} value={getConsumedMessages()} />
        </div>
        <LiveChart
          chartData={getConsumedMessagesChartData()}
          chartName={"Consumed messages in time"}
          yAxisName={"Consumed messages"}
          heightPercentage={"25%"}
        />
        <LiveChart
          chartData={getDeltaTimeChartData()}
          chartName={
            "Delta time [ms] between produced and consumed messages in time"
          }
          yAxisName={"Delta Time [ms]"}
          heightPercentage={"25%"}
        />
        <div>
          <h1 className="font-bold text-2xl text-teal-800 rounded-md border-y-2 border-slate-600 py-4 m-auto">
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
          <h1 className="font-bold text-2xl text-teal-800 rounded-md border-y-2 border-slate-600 py-4 m-auto">
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

export default ConsumerDataVisualization;
