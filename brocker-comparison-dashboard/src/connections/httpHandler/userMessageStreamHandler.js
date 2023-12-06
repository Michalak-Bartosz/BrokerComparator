import BASE_URL from "../constants/BASE_URL";

const userMessageStreamHandler = () => {
  let evetSource;

  const startListenUserMessageStream = () => {
    console.log("START LISTEN USER DATA STREAM");
    evetSource = new EventSource(BASE_URL.STREAM_DATA_API_URL + "/user");
    evetSource.onmessage = function (event) {
      console.log("ON MESSAGE");
      console.log(event.data);
    };
  };

  const stopListenUserMessageStream = () => {
    console.log("STOP LISTEN USER DATA STREAM");
    evetSource.close();
  };

  return {
    startListenUserMessageStream,
    stopListenUserMessageStream,
  };
};

export default userMessageStreamHandler;
