import React, { useState } from "react";
import useApi from "../../connections/api/useApi";
import ConsumerDataVisualization from "../compare/consumer/ConsumerDataVisualization";
import ProducerDataVisualization from "../compare/producer/ProducerDataVisualization";
import TestSettingsMenu from "../compare/TestSettingsMenu";

function MainDashboardPage() {
  const api = useApi();
  const [testUUID, setTestUUID] = useState(null);
  const [numberOfMessagesToSend, setNumberOfMessagesToSend] = useState(20);
  const [brokerType, setBrokerType] = useState("KAFKA");

  async function performTest() {
    try {
      let testSettings = {
        brokerType: brokerType,
        numberOfMessagesToSend: numberOfMessagesToSend,
      };
      const response = await api.performTest(testSettings);
      setTestUUID(response.testUUID);
    } catch (e) {
      console.log(e);
    }
  }

  return (
    <div className="block m-4">
      <TestSettingsMenu
        numberOfMessagesToSend={numberOfMessagesToSend}
        setNumberOfMessagesToSend={setNumberOfMessagesToSend}
        setBrokerType={setBrokerType}
        performTest={performTest}
      />
      <div className="flex text-center my-10">
        <div id="kafka-content-wrapper" className="w-full">
          <h1 className="text-5xl font-bold text-blue-500 mb-4">Kafka</h1>
          <div className="bg-lime-600 bg-opacity-20 rounded-lg p-4">
            <h1 className="text-3xl font-bold text-lime-600 rounded-md border-y-4 border-slate-600 py-4 mx-6 m-auto">
              Producer App
            </h1>
            <ProducerDataVisualization testUUID={testUUID} />
          </div>
          <div className="bg-teal-600 bg-opacity-20 rounded-lg p-4 m-4">
            <h1 className="text-3xl font-bold text-teal-600 rounded-md border-y-4 border-slate-600 py-4 mx-6 m-auto">
              Consumer App
            </h1>
            <ConsumerDataVisualization testUUID={testUUID} />
          </div>
        </div>
        {/* <div className="flex-none w-1 min-h-full rounded-md bg-slate-500 mx-4" /> */}
        {/* <div id="rabbitmq-content-wrapper" className="w-1/2">
          <h1 className="grow text-5xl font-bold text-blue-500">Rabbit Mq</h1>
        </div> */}
      </div>
    </div>
  );
}

export default MainDashboardPage;
