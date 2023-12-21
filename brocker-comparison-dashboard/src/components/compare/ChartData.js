import React, { useState } from "react";
import { randomDatasetColor } from "../util/ColorUtil";
import { getDateFromTimestampString } from "../util/DateUtil";

const ChartData = (props) => {
  const datasetColor = randomDatasetColor();
  const [testUUID, setTestUUID] = useState("");
  const [brokerType, setBrokerType] = useState("");
  const [datasetName, setDatasetName] = useState("");

  const [testStatus, setTestStatus] = useState(0);

  const [receivedMsgData, setReceivedMsgData] = useState([]);
  const [initialMemoryData, setInitialMemoryData] = useState([]);
  const [usedHeapMemoryData, setUsedHeapMemoryData] = useState([]);
  const [maxHeapMemoryData, setMaxHeapMemoryData] = useState([]);
  const [committedMemoryData, setCommittedMemoryData] = useState([]);
  const [systemCpuData, setSystemCpuData] = useState([]);
  const [appCpuData, setAppCpuData] = useState([]);

  let receivedMsgArray = [];
  let systemCpuArray = [];
  let appCpuArray = [];
  let initMemoArray = [];
  let usedHeapMemoArray = [];
  let maxHeapMemoArray = [];
  let commitedMemoArray = [];

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

  const setConstants = (testUUID) => {
    setTestUUID(testUUID);
    setBrokerType(brokerType);
    setDatasetName(
      "Broker Type: '" + brokerType + "' Test UUID: '" + testUUID + "'"
    );
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

  return {
    testUUID,
    datasetName,
    datasetColor,
    testStatus,
    receivedMsgData,
    initialMemoryData,
    usedHeapMemoryData,
    maxHeapMemoryData,
    committedMemoryData,
    systemCpuData,
    appCpuData,
    setConstants,
    updateChartData,
  };
};

export default ChartData;
