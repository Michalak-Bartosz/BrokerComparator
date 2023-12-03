import React from "react";
import { signal } from "@preact/signals-react";
import BASE_URL from "../constants/BASE_URL";
import {
  StompSessionProvider,
  useStompClient,
  useSubscription,
} from "react-stomp-hooks";

export const users = signal([]);
export const reports = signal([]);

export function WsClient() {
  const UserListener = () => {
    useSubscription("/topic/user-group", (msg) => {
      let user = JSON.parse(msg.body);
      users.value = users.value.concat(user);
      console.log(msg);
    });
  };

  const ReportListener = () => {
    useSubscription("/topic/report-group", (msg) => {
      let report = JSON.parse(msg.body);
      reports.value = reports.value.concat(report);
    });
  };

  const PublishMessage = () => {
    const stompClient = useStompClient();

    const publishMessage = () => {
      if (stompClient) {
        stompClient.publish({
          destination: "/app/userBroadcast",
          body: JSON.parse('{"uuid": "7946434e-2cfa-475d-b941-0812c6801c35"}'),
        });
      }
    };
    return (
      <button className="bg-green-500 border-2 p-2" onClick={publishMessage}>
        {" "}
        Send message{" "}
      </button>
    );
  };

  return (
    <div>
      <StompSessionProvider url={BASE_URL.WS_URL}>
        <UserListener />
        <ReportListener />
        {/* <PublishMessage /> */}
      </StompSessionProvider>
    </div>
  );
}
