import { Table } from "flowbite-react";
import { HiOutlinePresentationChartLine } from "react-icons/hi";
import React from "react";

function Report({ report, index, tabsRef, setTestReport, isCurrentReport }) {
  const handleOnClick = () => {
    tabsRef.current?.setActiveTab(0);
    setTestReport(report);
  };

  return (
    <Table.Row className="text-center odd:bg-slate-200 even:bg-slate-300">
      <Table.Cell
        className={`whitespace-nowrap font-bold text-gray-900 ${
          isCurrentReport ? "bg-blue-300" : ""
        }`}
      >
        {index}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap font-bold text-gray-900 ${
          isCurrentReport ? "bg-blue-300" : ""
        }`}
      >
        {report.testUUID}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap font-medium text-gray-900 ${
          isCurrentReport ? "bg-blue-300" : ""
        }`}
      >
        {report.userList.length}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap font-medium text-gray-900 ${
          isCurrentReport ? "bg-blue-300" : ""
        }`}
      >
        {report.numberOfAttempts}
      </Table.Cell>
      <Table.Cell
        className={`whitespace-nowrap text-center font-medium text-gray-900  ${
          isCurrentReport ? "bg-blue-300" : ""
        }`}
      >
        <button
          className="flex m-auto items-center bg-blue-500 text-white font-bold p-2 rounded-md outline outline-offset-2 outline-blue-500 px-4 hover:bg-blue-400"
          onClick={handleOnClick}
        >
          <HiOutlinePresentationChartLine className="mr-2 text-xl" />
          SHOW
        </button>
      </Table.Cell>
    </Table.Row>
  );
}

export default Report;
