import React from "react";
import { WsClient } from "../../connections/ws/WsClient";
import KafkaMessagesChart from "../charts/KafkaMessagesChart";

function MainDashboardPage(props) {
  return (
    <div className="">
      <WsClient />
      <div className="flex text-center">
        <div id="kafka-content-wrapper" className="w-1/2">
          <h1 className="text-5xl font-bold text-blue-500">Kafka</h1>
          <KafkaMessagesChart />
        </div>
        <div className="flex-none w-1 min-h-full rounded-md bg-slate-500 mx-4" />
        <div id="rabbitmq-content-wrapper" className="w-1/2">
          <h1 className="grow text-5xl font-bold text-blue-500">Rabbit Mq</h1>
        </div>
      </div>
    </div>
  );
}

export default MainDashboardPage;
