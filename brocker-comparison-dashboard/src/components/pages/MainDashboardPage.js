import React, { useState } from "react";
import useApi from "../../connections/api/useApi";
import ConsumerDataVisualization from "../compare/consumer/ConsumerDataVisualization";
import ProducerDataVisualization from "../compare/producer/ProducerDataVisualization";
import TestSettingsMenu from "../compare/TestSettingsMenu";
import { KAFKA_BROKER } from "../compare/constants/brokerType";
import TestReportOverview from "../compare/TestReportOverview";

function MainDashboardPage() {
  const api = useApi();
  const [testInProgressProducer, setTestInProgressProducer] = useState(false);
  const [testInProgressConsumer, setTestInProgressConsumer] = useState(false);
  const [testUUID, setTestUUID] = useState(null);
  const [numberOfMessagesToSend, setNumberOfMessagesToSend] = useState(10);
  const [numberOfAttempts, setNumberOfAttempts] = useState(1);
  const [delayInMilliseconds, setDelayInMilliseconds] = useState(0);
  const [brokerTypes, setBrokerTypes] = useState(KAFKA_BROKER.value);
  const [testReport, setTestReport] = useState(null);

  async function performTest() {
    try {
      let testSettings = {
        brokerTypes: brokerTypes,
        numberOfMessagesToSend: numberOfMessagesToSend,
        numberOfAttempts: numberOfAttempts,
        delayInMilliseconds: delayInMilliseconds,
      };
      const response = await api.performTest(testSettings);
      setTestUUID(response.testUUID);
    } catch (e) {
      console.log(e);
    }
  }

  async function generateTestReport() {
    console.log(testUUID);
    try {
      const response = await api.generateTestReport(testUUID);
      setTestReport(response);
    } catch (e) {
      console.log(e);
    }
  }

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
          testUUID={testUUID}
          performTest={performTest}
        />
        <div className="col-span-2">
          <TestReportOverview
            testReport={testReport}
            testInProgressProducer={testInProgressProducer}
            testInProgressConsumer={testInProgressConsumer}
            testUUID={testUUID}
            generateTestReport={generateTestReport}
          />
        </div>
      </div>
      <div className="flex text-center my-10">
        <div id="content-wrapper" className="w-full">
          <ProducerDataVisualization
            testUUID={testUUID}
            brokerTypes={brokerTypes}
            numberOfMessagesToSend={numberOfMessagesToSend}
            numberOfAttempts={numberOfAttempts}
            isInProgress={testInProgressProducer}
            setIsInProgress={setTestInProgressProducer}
          />
          <ConsumerDataVisualization
            testUUID={testUUID}
            brokerTypes={brokerTypes}
            numberOfMessagesToSend={numberOfMessagesToSend}
            numberOfAttempts={numberOfAttempts}
            isInProgress={testInProgressConsumer}
            setIsInProgress={setTestInProgressConsumer}
          />
        </div>
      </div>
    </div>
  );
}

export default MainDashboardPage;
