import BASE_URL from "../constants/BASE_URL";

const reportMessageStreamHandler = () => {
  let evetSource;

  const startListenReportMessageStream = () => {
    console.log("START LISTEN REPORT STREAM");
    evetSource = new EventSource(BASE_URL.STREAM_DATA_API_URL + "/report");
    evetSource.onmessage = function (event) {
      console.log("ON MESSAGE");
      console.log(event.data);
    };
  };

  const stopListenReportMessageStream = () => {
    console.log("STOP LISTEN REPORT STREAM");
    evetSource.close();
  };

  return {
    startListenReportMessageStream,
    stopListenReportMessageStream,
  };
};

export default reportMessageStreamHandler;
