import React, { useEffect, useState } from "react";
import CPUMetric from "./metrics/CPUMetric";
import MemoryMetric from "./metrics/MemoryMetric";
import TestDataModal from "./TestDataModal";
import { FaDotCircle } from "react-icons/fa";
import TimeMetric from "./metrics/TimeMetric";
import { FaArrowAltCircleLeft } from "react-icons/fa";
import { FaArrowAltCircleRight } from "react-icons/fa";
import GeneralInfo from "./metrics/GeneralInfo";
import DataSizeMetric from "./metrics/DataSizeMetric";

function TestReportSummary({
  testReport,
  isReportFocused,
  increaseCurrentTestReportIndex,
  decreseCurrentTestReportIndex,
}) {
  const [openTestDataModal, setOpenTestDataModal] = useState(false);

  useEffect(() => {
    const listener = (event) => {
      if (event.code === "ArrowLeft") {
        event.preventDefault();
        decreseCurrentTestReportIndex();
      } else if (event.code === "ArrowRight") {
        event.preventDefault();
        increaseCurrentTestReportIndex();
      }
    };
    document.addEventListener("keydown", listener);
    return () => {
      document.removeEventListener("keydown", listener);
    };
  }, [decreseCurrentTestReportIndex, increaseCurrentTestReportIndex]);

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
        <div className="block mt-6 bg-indigo-300 bg-opacity-40 p-6 rounded-lg">
          <h1 className="text-center text-3xl font-bold border-y-2 border-slate-600 rounded-md pl-6 py-2 w-full">
            General Info
          </h1>
          <GeneralInfo testReport={testReport} />
        </div>
        <div className="bg-indigo-300 bg-opacity-40 p-6 rounded-lg mt-6">
          <div className="flex items-center pb-2">
            <FaDotCircle className="text-blue-800 mr-2" />
            <span className="font-bold text-3xl text-blue-800">
              Time Metrics
            </span>
          </div>
          <TimeMetric
            timeMetric={testReport.reportTimeMetric}
            brokerInfoDataList={testReport?.brokerInfoDataList}
          />
        </div>
        <div className="bg-emerald-200 bg-opacity-40 p-6 rounded-lg mt-6">
          <div className="flex items-center pb-2">
            <FaDotCircle className="text-emerald-900 mr-2" />
            <span className="font-bold text-3xl text-emerald-900">
              Data Size Metrics
            </span>
          </div>
          <DataSizeMetric
            dataSizeMetric={testReport.reportDataSizeMetric}
            brokerInfoDataList={testReport?.brokerInfoDataList}
          />
        </div>
        <div className="bg-stone-300 bg-opacity-40 p-6 rounded-lg mt-6">
          <div className="flex items-center pb-2">
            <FaDotCircle className="text-stone-800 mr-2" />
            <span className="font-bold text-3xl text-stone-800">
              Producer Metrics
            </span>
          </div>
          <CPUMetric
            cpuMetric={testReport.producerReportCPUMetric}
            brokerCpuMetric={[
              ...testReport?.brokerInfoDataList.map(
                (brokerInfoData) => brokerInfoData?.producerReportCPUMetric
              ),
            ]}
          />
          <MemoryMetric
            memoryMetric={testReport.producerReportMemoryMetric}
            brokerMemoryMetric={[
              ...testReport?.brokerInfoDataList.map(
                (brokerInfoData) => brokerInfoData?.producerReportMemoryMetric
              ),
            ]}
          />
        </div>
        <div className="bg-teal-200 bg-opacity-40 p-6 rounded-lg mt-6">
          <div className="flex items-center pb-2">
            <FaDotCircle className="text-teal-800 mr-2" />
            <span className="font-bold text-3xl text-teal-800">
              Consumer Metrics
            </span>
          </div>
          <CPUMetric
            cpuMetric={testReport.consumerReportCPUMetric}
            brokerCpuMetric={[
              ...testReport?.brokerInfoDataList.map(
                (brokerInfoData) => brokerInfoData.consumerReportCPUMetric
              ),
            ]}
          />
          <MemoryMetric
            memoryMetric={testReport.consumerReportMemoryMetric}
            brokerMemoryMetric={[
              ...testReport?.brokerInfoDataList.map(
                (brokerInfoData) => brokerInfoData.consumerReportMemoryMetric
              ),
            ]}
          />
        </div>
        <button
          id="show-test-data-button"
          className="flex mx-auto mt-6 px-4 py-2 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold text-2xl"
          onClick={() => setOpenTestDataModal(true)}
        >
          Show Test Data
        </button>
        <TestDataModal
          userList={testReport?.userList}
          debugInfoList={testReport.debugInfoList}
          openModal={openTestDataModal}
          setOpenModal={setOpenTestDataModal}
        />
      </div>
    )
  );
}

export default TestReportSummary;
