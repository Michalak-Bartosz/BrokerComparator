import BASE_URL from "../constants/BASE_URL";

const debugInfoMessageStreamHandler = () => {
  let evetSource;

  const startListenDebugInfoMessageStream = () => {
    console.log("START LISTEN DEBUG INFO STREAM");
    evetSource = new EventSource(BASE_URL.STREAM_DATA_API_URL + "/debug-info");
    evetSource.onmessage = function (event) {
      console.log("ON MESSAGE");
      console.log(event.data);
    };
  };

  const stopListenDebugInfoMessageStream = () => {
    console.log("STOP LISTEN DEBUG INFO DATA STREAM");
    evetSource.close();
  };

  return {
    startListenDebugInfoMessageStream,
    stopListenDebugInfoMessageStream,
  };
};

export default debugInfoMessageStreamHandler;
