import { Table } from "flowbite-react";
import { HiOutlinePresentationChartLine } from "react-icons/hi";
import React from "react";

function Report({
  report,
  index,
  addReportToFocusedTestReportArray,
  removeReportFromFocusedTestReportArray,
  isFocusedReport,
}) {
  const handleOnClick = () => {
    if (isFocusedReport) {
      removeReportFromFocusedTestReportArray(report);
    } else {
      addReportToFocusedTestReportArray(report);
    }
  };

  return (
    <Table.Row className="text-center odd:bg-slate-200 even:bg-slate-300">
      <Table.Cell
        className={`whitespace-nowrap font-bold text-gray-900 ${
          isFocusedReport ? "bg-blue-300" : ""
        }`}
      >
        {index}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap font-bold text-gray-900 ${
          isFocusedReport ? "bg-blue-300" : ""
        }`}
      >
        {report.testUUID}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap font-bold text-gray-900 ${
          isFocusedReport ? "bg-blue-300" : ""
        }`}
      >
        {report.brokerTypeList}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap font-medium text-gray-900 ${
          isFocusedReport ? "bg-blue-300" : ""
        }`}
      >
        {report.userList.length}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap font-medium text-gray-900 ${
          isFocusedReport ? "bg-blue-300" : ""
        }`}
      >
        {report.numberOfAttempts}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap text-center font-medium text-gray-900  ${
          isFocusedReport ? "bg-blue-300" : ""
        }`}
      >
        <button
          className={`flex m-auto items-center text-white font-bold py-2 rounded-md outline outline-offset-2 ${
            isFocusedReport
              ? "bg-rose-900 outline-rose-800 px-4 hover:bg-rose-800"
              : "bg-blue-500 outline-blue-500 px-4 hover:bg-blue-400"
          }`}
          onClick={handleOnClick}
        >
          <HiOutlinePresentationChartLine className="mr-2 text-xl" />
          {isFocusedReport ? `NO` : `YES`}
        </button>
      </Table.Cell>
    </Table.Row>
  );
}

export default Report;
