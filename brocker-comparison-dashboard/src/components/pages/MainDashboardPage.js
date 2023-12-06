import React from "react";
import KafkaMessagesChart from "../charts/KafkaMessagesChart";
import debugInfoMessageStreamHandler from "../../connections/httpHandler/debugInfoMessageStreamHandler";

function MainDashboardPage() {
  const debugInfoStream = debugInfoMessageStreamHandler();

  return (
    <div className="">
      <div className="flex text-center">
        <div id="kafka-content-wrapper" className="w-1/2">
          <h1 className="text-5xl font-bold text-blue-500">Kafka</h1>
          <KafkaMessagesChart />
          <button
            onClick={() => debugInfoStream.startListenDebugInfoMessageStream()}
          >
            Start Stream
          </button>
          <button
            onClick={() => debugInfoStream.stopListenDebugInfoMessageStream()}
          >
            Stop Stream
          </button>
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
