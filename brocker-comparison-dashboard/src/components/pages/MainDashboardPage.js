import React, { useEffect, useState } from "react";
import useApi from "../../connections/api/useApi";
import ConsumerDataVisualization from "../compare/consumer/ConsumerDataVisualization";
import ProducerDataVisualization from "../compare/producer/ProducerDataVisualization";
import TestSettingsMenu from "../compare/TestSettingsMenu";
import { KAFKA_BROKER } from "../compare/constants/brokerType";
import DataOverview from "../compare/DataOverview";
import DataOverviewModal from "../compare/DataOverviewModal";
import { useDispatch } from "react-redux";
import {
  addTestReportToFocuedAction,
  clearAllForcusedReportsAction,
  removeTestReportFromFocusedAction,
} from "../../redux/actions/testReportActions";
import DataStatusToast from "../compare/progress/DataStatusToast";

function MainDashboardPage() {
  const api = useApi();
  const dispatch = useDispatch();
  const [asyncTestInProgress, setAsyncTestInProgress] = useState(false);
  const [generatingData, setGeneratingData] = useState(false);
  const [isWaitingForReports, setIsWaitingForReports] = useState(false);
  const [testInProgressProducer, setTestInProgressProducer] =
    useState("NOT_RUNNED");
  const [testInProgressConsumer, setTestInProgressConsumer] =
    useState("NOT_RUNNED");
  const [lastTestUUID, setLastTestUUID] = useState(null);
  const [isSync, setIsSync] = useState(true);
  const [numberOfMessagesToSend, setNumberOfMessagesToSend] = useState(10);
  const [numberOfAttempts, setNumberOfAttempts] = useState(1);
  const [delayInMilliseconds, setDelayInMilliseconds] = useState(0);
  const [brokerTypes, setBrokerTypes] = useState(KAFKA_BROKER.value);
  const [focusedTestReportArray, setFocusedTestReportArray] = useState([]);
  const [testReportArray, setTestReportArray] = useState([]);
  const [openFullscreenDataOverviewModal, setOpenFullscreenDataOverviewModal] =
    useState(false);

  async function performTest() {
    try {
      let testSettings = {
        isSync: isSync,
        brokerTypes: brokerTypes,
        numberOfMessagesToSend: numberOfMessagesToSend,
        numberOfAttempts: numberOfAttempts,
        delayInMilliseconds: delayInMilliseconds,
      };

      if (!isSync) {
        setAsyncTestInProgress(true);
      }
      const response = await api.performTest(testSettings);
      setLastTestUUID(response.testUUID);
      if (isSync) {
        setTestInProgressProducer("IN_PROGRESS");
        setTestInProgressConsumer("IN_PROGRESS");
        setGeneratingData(true);
      } else {
        setAsyncTestInProgress(false);
        getTestReportArrayAndSetCurrentReport();
      }
    } catch (e) {
      console.log(e);
      setAsyncTestInProgress(false);
      setGeneratingData(false);
      setTestInProgressProducer("NOT_RUNNED");
      setTestInProgressConsumer("NOT_RUNNED");
    }
  }

  async function finishTest() {
    try {
      let finishTest = {
        testUUID: lastTestUUID,
        numberOfReceivedMessagesProducer:
          numberOfMessagesToSend * numberOfAttempts * brokerTypes.length,
        numberOfReceivedMessagesConsumer:
          numberOfMessagesToSend * numberOfAttempts * brokerTypes.length,
      };
      const response = await api.finishTest(finishTest);
      if (response?.producerFinishTest && response?.consumerFinishTest) {
        getTestReportArrayAndSetCurrentReport();
      }
      setTestInProgressProducer("NOT_RUNNED");
      setTestInProgressConsumer("NOT_RUNNED");
    } catch (e) {
      console.log(e);
    }
  }

  const clearFocusedTestReportArray = () => {
    setFocusedTestReportArray([]);
    dispatch(clearAllForcusedReportsAction());
  };

  const addReportToFocusedTestReportArray = (newReport) => {
    const isExist = focusedTestReportArray.find(
      (report) => report.testUUID === newReport.testUUID
    );
    if (!isExist) {
      if (focusedTestReportArray.length === 0) {
        setFocusedTestReportArray([newReport]);
      } else {
        setFocusedTestReportArray((prevArray) => [...prevArray, newReport]);
      }
      dispatch(addTestReportToFocuedAction(newReport.testUUID));
    }
  };

  const removeReportFromFocusedTestReportArray = (reportToRemove) => {
    setFocusedTestReportArray((prevArray) => [
      ...prevArray.filter(
        (report) => report.testUUID !== reportToRemove.testUUID
      ),
    ]);
    dispatch(removeTestReportFromFocusedAction(reportToRemove.testUUID));
  };

  const updateFocusedTestReportArray = (allReportsArray) => {
    if (lastTestUUID) {
      const lastTestReport = allReportsArray.find(
        (report) => report.testUUID === lastTestUUID
      );
      const isExist = focusedTestReportArray.find(
        (report) => report.testUUID === lastTestReport.testUUID
      );
      if (!isExist) {
        addReportToFocusedTestReportArray(lastTestReport);
      }
    }
  };

  async function getTestReportArrayAndSetCurrentReport() {
    try {
      setIsWaitingForReports(true);
      const response = await api.getTestReports();
      if (response) {
        setTestReportArray([...response]);
        updateFocusedTestReportArray(response);
        setIsWaitingForReports(false);
      }
    } catch (e) {
      setIsWaitingForReports(false);
      console.log(e);
    }
  }

  useEffect(() => {
    getTestReportArrayAndSetCurrentReport();
    dispatch(clearAllForcusedReportsAction());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [asyncTestInProgress]);

  useEffect(() => {
    if (
      testInProgressProducer === "COMPLETE" &&
      testInProgressConsumer === "COMPLETE" &&
      lastTestUUID
    ) {
      finishTest();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [testInProgressProducer, testInProgressConsumer, lastTestUUID]);

  return (
    <div className="block m-8">
      <div className="grid grid-cols-3 gap-6">
        <TestSettingsMenu
          numberOfMessagesToSend={numberOfMessagesToSend}
          setNumberOfMessagesToSend={setNumberOfMessagesToSend}
          isSync={isSync}
          setIsSync={setIsSync}
          numberOfAttempts={numberOfAttempts}
          setNumberOfAttempts={setNumberOfAttempts}
          delayInMilliseconds={delayInMilliseconds}
          setDelayInMilliseconds={setDelayInMilliseconds}
          setBrokerTypes={setBrokerTypes}
          testInProgressProducer={testInProgressProducer}
          testInProgressConsumer={testInProgressConsumer}
          asyncTestInProgress={asyncTestInProgress}
          performTest={performTest}
        />
        <div className="col-span-2">
          <DataOverview
            focusedTestReportArray={focusedTestReportArray}
            clearFocusedTestReportArray={clearFocusedTestReportArray}
            addReportToFocusedTestReportArray={
              addReportToFocusedTestReportArray
            }
            removeReportFromFocusedTestReportArray={
              removeReportFromFocusedTestReportArray
            }
            testReportArray={testReportArray}
            setOpenFullscreenDataOverviewModal={
              setOpenFullscreenDataOverviewModal
            }
          />
          <DataOverviewModal
            focusedTestReportArray={focusedTestReportArray}
            clearFocusedTestReportArray={clearFocusedTestReportArray}
            addReportToFocusedTestReportArray={
              addReportToFocusedTestReportArray
            }
            removeReportFromFocusedTestReportArray={
              removeReportFromFocusedTestReportArray
            }
            testReportArray={testReportArray}
            openFullscreenDataOverviewModal={openFullscreenDataOverviewModal}
            setOpenFullscreenDataOverviewModal={
              setOpenFullscreenDataOverviewModal
            }
          />
        </div>
      </div>

      <div
        className={`flex text-center my-10 transition-all 
      ${asyncTestInProgress ? "opacity-30 pointer-events-none" : ""}`}
      >
        <div id="content-wrapper" className="w-full">
          <ProducerDataVisualization
            testUUID={lastTestUUID}
            brokerTypes={brokerTypes}
            numberOfMessagesToSend={numberOfMessagesToSend}
            numberOfAttempts={numberOfAttempts}
            isInProgress={testInProgressProducer}
            generatingData={generatingData}
            setGeneratingData={setGeneratingData}
            setIsInProgress={setTestInProgressProducer}
            focusedTestReportArray={focusedTestReportArray}
          />
          <ConsumerDataVisualization
            testUUID={lastTestUUID}
            brokerTypes={brokerTypes}
            numberOfMessagesToSend={numberOfMessagesToSend}
            numberOfAttempts={numberOfAttempts}
            isInProgress={testInProgressConsumer}
            setIsInProgress={setTestInProgressConsumer}
            focusedTestReportArray={focusedTestReportArray}
          />
        </div>
      </div>

      <DataStatusToast
        generatingData={generatingData}
        asyncTestInProgress={asyncTestInProgress}
        isWaitingForReports={isWaitingForReports}
      />
    </div>
  );
}

export default MainDashboardPage;
