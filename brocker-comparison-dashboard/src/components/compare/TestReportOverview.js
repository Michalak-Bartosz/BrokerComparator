import React from "react";
import TestReport from "./testReport/TestReport";

function TestReportOverview(props) {
  return (
    <div
      id="db-overview"
      className="px-14 py-8 bg-slate-950 bg-opacity-40 rounded-lg shadow-lg w-full max-h-[500px] overflow-y-scroll min-w-fit min-h-full"
    >
      <div className="mb-6 border-y-4 border-slate-600 rounded-md py-4">
        <h1 className="text-4xl text-center text-blue-500 font-bold drop-shadow-[0_1.2px_1.2px_rgba(37,99,235,1)]">
          Test Report Overview
        </h1>
      </div>

      <button
        id="generate-report-button"
        className="flex mx-auto my-6 px-4 py-2 outline outline-offset-2 outline-green-700 rounded-md bg-green-800 hover:bg-green-600 disabled:bg-slate-400 disabled:outline-slate-500 text-white font-bold text-2xl"
        disabled={
          props.testInProgressProducer ||
          props.testInProgressConsumer ||
          !props.testUUID
        }
        onClick={() => props.generateTestReport()}
      >
        Generate Report
      </button>
      {props.testReport && <TestReport testReport={props.testReport} />}
    </div>
  );
}

export default TestReportOverview;
