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
import GeneratingDataStatusToast from "../compare/progress/GeneratingDataStatusToast";

function MainDashboardPage() {
  const api = useApi();
  const dispatch = useDispatch();
  const [generatingData, setGeneratingData] = useState(false);
  const [testInProgressProducer, setTestInProgressProducer] = useState(false);
  const [testInProgressConsumer, setTestInProgressConsumer] = useState(false);
  const [lastTestUUID, setLastTestUUID] = useState(null);
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
        brokerTypes: brokerTypes,
        numberOfMessagesToSend: numberOfMessagesToSend,
        numberOfAttempts: numberOfAttempts,
        delayInMilliseconds: delayInMilliseconds,
      };
      const response = await api.performTest(testSettings);
      setLastTestUUID(response.testUUID);
      setTestInProgressProducer(true);
      setTestInProgressConsumer(true);
      setGeneratingData(true);
    } catch (e) {
      console.log(e);
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
      const response = await api.getTestReports();
      if (response) {
        setTestReportArray([...response]);
        updateFocusedTestReportArray(response);
      }
    } catch (e) {
      console.log(e);
    }
  }

  useEffect(() => {
    getTestReportArrayAndSetCurrentReport();
    dispatch(clearAllForcusedReportsAction());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (!testInProgressProducer && !testInProgressConsumer && lastTestUUID) {
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
          numberOfAttempts={numberOfAttempts}
          setNumberOfAttempts={setNumberOfAttempts}
          delayInMilliseconds={delayInMilliseconds}
          setDelayInMilliseconds={setDelayInMilliseconds}
          setBrokerTypes={setBrokerTypes}
          testInProgressProducer={testInProgressProducer}
          testInProgressConsumer={testInProgressConsumer}
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
      <div className="flex text-center my-10">
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
      <GeneratingDataStatusToast
        showToast={generatingData}
        setShowToast={setGeneratingData}
      />
    </div>
  );
}

export default MainDashboardPage;
