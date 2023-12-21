import { Spinner } from "flowbite-react";
import React from "react";

function TestStatus(props) {
  return (
    <div className="flex m-auto mr-0 items-center shadow-md">
      <span className="font-bold text-2xl p-4 bg-green-800 rounded-l-md bg-opacity-90 border-r-4">
        Test status:
      </span>
      {props.isInProgress ? (
        <div className="flex     items-center bg-blue-500 p-4 rounded-r-md bg-opacity-50">
          <Spinner size="lg" />
          <span className="ml-4 text-2xl text-white">Loading...</span>
        </div>
      ) : props.testStatus === 100 ? (
        <span className="text-2xl text-white bg-green-800 p-4 rounded-r-md bg-opacity-90">
          Complitted
        </span>
      ) : (
        <span className="text-2xl text-white bg-slate-500 p-4 rounded-r-md bg-opacity-80">
          Not runned
        </span>
      )}
    </div>
  );
}

export default TestStatus;
