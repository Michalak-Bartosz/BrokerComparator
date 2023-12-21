import React, { useState } from "react";
import useApi from "../../connections/api/useApi";
import ConsumerDataVisualization from "../compare/consumer/ConsumerDataVisualization";
import ProducerDataVisualization from "../compare/producer/ProducerDataVisualization";
import TestSettingsMenu from "../compare/TestSettingsMenu";
import { KAFKA_BROKER } from "../compare/constants/brokerType";
import DbOverview from "../compare/DbOverview";

function MainDashboardPage() {
  const api = useApi();
  const [testInProgress, setTestInProgress] = useState(null);
  const [testUUID, setTestUUID] = useState(null);
  const [numberOfMessagesToSend, setNumberOfMessagesToSend] = useState(10);
  const [numberOfAttempts, setNumberOfAttempts] = useState(1);
  const [brokerTypes, setBrokerTypes] = useState(KAFKA_BROKER.value);

  async function sendRequest() {
    try {
      let testSettings = {
        brokerTypes: brokerTypes,
        numberOfMessagesToSend: numberOfMessagesToSend,
      };
      const response = await api.performTest(testSettings);
      setTestUUID(response.testUUID);
    } catch (e) {
      console.log(e);
    }
  }

  const transactions = {};

  function doTransaction(name, promiseFunc) {
    transactions[name] = (transactions[name] || Promise.resolve()).then(
      promiseFunc
    );
  }

  function performTest() {
    setTestInProgress(true);
    for (let i = 0; i < numberOfAttempts; i++) {
      doTransaction(i, sendRequest());
    }
    setTestInProgress(false);
  }

  return (
    <div className="block m-8">
      <div className="grid grid-cols-3 gap-6">
        <TestSettingsMenu
          numberOfMessagesToSend={numberOfMessagesToSend}
          setNumberOfMessagesToSend={setNumberOfMessagesToSend}
          numberOfAttempts={numberOfAttempts}
          setNumberOfAttempts={setNumberOfAttempts}
          setBrokerTypes={setBrokerTypes}
          performTest={performTest}
          testInProgress={testInProgress}
        />
        <div className="col-span-2">
          <DbOverview />
        </div>
      </div>

      <div className="flex text-center my-10">
        <div id="content-wrapper" className="w-full">
          <ProducerDataVisualization
            testUUID={testUUID}
            brokerTypes={brokerTypes}
          />
          <ConsumerDataVisualization
            testUUID={testUUID}
            brokerTypes={brokerTypes}
          />
        </div>
      </div>
    </div>
  );
}

export default MainDashboardPage;
