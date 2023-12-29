import React, { useState } from "react";
import CPUMetric from "./metrics/CPUMetric";
import MemoryMetric from "./metrics/MemoryMetric";
import TestDataModal from "./TestDataModal";
import { FaDotCircle } from "react-icons/fa";
import TimeMetric from "./metrics/TimeMetric";
import { FaArrowAltCircleLeft } from "react-icons/fa";
import { FaArrowAltCircleRight } from "react-icons/fa";

function TestReportSummary({
  testReport,
  isReportFocused,
  increaseCurrentTestReportIndex,
  decreseCurrentTestReportIndex,
  setOpenFullscreenDataOverviewModal,
}) {
  const [openTestDataModal, setOpenTestDataModal] = useState(false);
  const navigationComponent = () => {
    return (
      <div className="block py-2">
        <div
          id="focused-test-report-navigate"
          className="relative flex text-2xl"
        >
          <div className="flex m-auto ml-0">
            <button
              className="flex items-center font-bold hover:text-blue-500"
              onClick={decreseCurrentTestReportIndex}
            >
              <FaArrowAltCircleLeft />
              <span className="ml-2">Prev</span>
            </button>
          </div>
          <div className="flex m-auto items-center text-2xl">
            <span className="font-bold ml-auto text-blue-500">
              Test UUID:&nbsp;
            </span>
            <span className="mr-auto">{testReport.testUUID}</span>
          </div>
          <div className="flex m-auto mr-0">
            <button
              className="flex items-center font-bold hover:text-blue-500"
              onClick={increaseCurrentTestReportIndex}
            >
              <span className="mr-2">Next</span>
              <FaArrowAltCircleRight />
            </button>
          </div>
        </div>
      </div>
    );
  };
  return (
    isReportFocused && (
      <div id="test-report-wrapper" className="block text-xl">
        {navigationComponent()}
        <div className="block mt-6 bg-slate-950 bg-opacity-20 p-6 rounded-lg">
          <h1 className="text-center text-3xl font-bold border-y-2 border-slate-600 rounded-md pl-6 py-2 w-full">
            General Info
          </h1>
          <div className="flex flex-row">
            <div className="flex m-auto items-center text-2xl my-6">
              <FaDotCircle className="text-blue-500 ml-auto mr-2" />
              <span className="text-blue-500 font-bold">
                Broker Type:&nbsp;
              </span>
              <span className="mr-auto">{testReport.brokerTypeList}</span>
            </div>
            <div className="flex m-auto items-center text-2xl my-6">
              <FaDotCircle className="text-blue-500 ml-auto mr-2" />
              <span className="text-blue-500 font-bold">
                Users in test:&nbsp;
              </span>
              <span className="mr-auto">{testReport.userList.length}</span>
            </div>
            <div className="flex m-auto items-center text-2xl my-6">
              <FaDotCircle className="text-blue-500 ml-auto mr-2" />
              <span className="text-blue-500 font-bold">
                Number Of attempts:&nbsp;
              </span>
              <span className="mr-auto">{testReport.numberOfAttempts}</span>
            </div>
          </div>
        </div>
        <div className="bg-slate-950 bg-opacity-20 p-6 rounded-lg mt-6">
          <div className="flex items-center pb-2">
            <FaDotCircle className="text-blue-500 mr-2" />
            <span className="font-bold text-3xl text-blue-500">
              Time Metrics
            </span>
          </div>
          <TimeMetric timeMetric={testReport.reportTimeMetric} />
        </div>
        <div className="bg-slate-950 bg-opacity-20 p-6 rounded-lg mt-6">
          <div className="flex items-center pb-2">
            <FaDotCircle className="text-blue-500 mr-2" />
            <span className="font-bold text-3xl text-blue-500">
              Producer Metrics
            </span>
          </div>
          <CPUMetric cpuMetric={testReport.producerReportCPUMetric} />
          <MemoryMetric memoryMetric={testReport.producerReportMemoryMetric} />
        </div>
        <div className="bg-slate-950 bg-opacity-20 p-6 rounded-lg mt-6">
          <div className="flex items-center pt-6 pb-2">
            <FaDotCircle className="text-blue-500 mr-2" />
            <span className="font-bold text-3xl text-blue-500">
              Consumer Metrics
            </span>
          </div>
          <CPUMetric cpuMetric={testReport.consumerReportCPUMetric} />
          <MemoryMetric memoryMetric={testReport.consumerReportMemoryMetric} />
        </div>
        <button
          id="show-test-data-button"
          className="flex mx-auto mt-6 px-4 py-2 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold text-2xl"
          onClick={() => setOpenTestDataModal(true)}
        >
          Show Test Data
        </button>
        <TestDataModal
          userList={testReport.userList}
          debugInfoList={testReport.debugInfoList}
          openModal={openTestDataModal}
          setOpenModal={setOpenTestDataModal}
        />
      </div>
    )
  );
}

export default TestReportSummary;
