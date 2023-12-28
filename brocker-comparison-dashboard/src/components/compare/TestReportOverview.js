import React, { useState } from "react";
import CPUMetric from "./testReport/metrics/CPUMetric";
import MemoryMetric from "./testReport/metrics/MemoryMetric";
import TestDataModal from "./testReport/TestDataModal";
import { FaDotCircle } from "react-icons/fa";
import TimeMetric from "./testReport/metrics/TimeMetric";

function TestReportOverview({ testReport }) {
  const [openTestDataModal, setOpenTestDataModal] = useState(false);

  return (
    <div id="test-report-wrapper" className="block text-xl">
      {testReport ? (
        <div>
          <div className="flex m-auto items-center text-2xl pt-4 pb-2">
            <span className="font-bold ml-auto text-blue-500">
              Test UUID:&nbsp;
            </span>
            <span className="mr-auto">{testReport.testUUID}</span>
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
            <MemoryMetric
              memoryMetric={testReport.producerReportMemoryMetric}
            />
          </div>
          <div className="bg-slate-950 bg-opacity-20 p-6 rounded-lg mt-6">
            <div className="flex items-center pt-6 pb-2">
              <FaDotCircle className="text-blue-500 mr-2" />
              <span className="font-bold text-3xl text-blue-500">
                Consumer Metrics
              </span>
            </div>
            <CPUMetric cpuMetric={testReport.consumerReportCPUMetric} />
            <MemoryMetric
              memoryMetric={testReport.consumerReportMemoryMetric}
            />
          </div>
          <div className="flex m-auto items-center text-2xl my-6">
            <FaDotCircle className="text-blue-500 ml-auto mr-2" />
            <span className="text-blue-500 font-bold">
              Users in test:&nbsp;
            </span>
            <span className="mr-auto">{testReport.userList.length}</span>
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
      ) : (
        <h1 className="flex w-max font-bold text-center m-auto mt-12 text-blue-500 text-2xl">
          Choose report or start new test to show test report overview!
        </h1>
      )}
    </div>
  );
}

export default TestReportOverview;
