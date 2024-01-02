import { Label, Pagination, Select, Table } from "flowbite-react";
import React, { useEffect, useState } from "react";
import Report from "./Report";
import { getDateFromTimestampString } from "../../../../util/DateTimeUtil";

function ReportTable({
  testReportArray,
  focusedTestReportUUIDArray,
  clearFocusedTestReportArray,
  addReportToFocusedTestReportArray,
  removeReportFromFocusedTestReportArray,
}) {
  const [currentPage, setCurrentPage] = useState(1);
  const [maxReportsOnPage, setMaxReportsOnPage] = useState(10);
  const [totalPages, setTotalPages] = useState(
    Math.ceil(testReportArray.length / maxReportsOnPage)
  );

  const selectMaxReportsOnPage = [10, 25, 50, 100, 200, 300, 400, 500];

  useEffect(() => {
    setTotalPages(Math.ceil(testReportArray.length / maxReportsOnPage));
  }, [maxReportsOnPage, testReportArray.length]);

  const onPageChange = (page) => {
    setCurrentPage(page);
  };

  const calculateItemIndex = (index) => {
    return (currentPage - 1) * maxReportsOnPage + index + 1;
  };

  const sortTestReportArray = (reportsOnPage) => {
    return reportsOnPage.sort(function (a, b) {
      return (
        getDateFromTimestampString(a.debugInfoList[0].producedTimestamp) -
        getDateFromTimestampString(b.debugInfoList[0].producedTimestamp)
      );
    });
  };

  const calculateReportsOnPage = () => {
    const reportsOnPage = testReportArray.slice(
      (currentPage - 1) * maxReportsOnPage,
      currentPage * maxReportsOnPage
    );
    return sortTestReportArray(reportsOnPage);
  };

  const isFocusedReport = (reportTestUUID) => {
    if (focusedTestReportUUIDArray.find((uuid) => uuid === reportTestUUID)) {
      return true;
    }
    return false;
  };

  let selectId = "max-reports-on-page-select" + Math.random();

  return (
    <div>
      <div className="flex pb-6 pt-2 px-4">
        <div className="flex items-center text-xl m-auto ml-0">
          <span className="flex text-blue-500 font-bold">
            Number of reports:&nbsp;
          </span>
          <span>{testReportArray.length}</span>
        </div>
        <button
          className="flex px-2 mx-4 items-center text-white font-bold rounded-md outline outline-offset-2 bg-rose-900 outline-rose-800 hover:bg-rose-800"
          onClick={() => clearFocusedTestReportArray()}
        >
          Clear Visiable
        </button>
        <div className="flex items-center m-auto mr-0">
          <Label
            id={(Math.random() + 1).toString(36).substring(7)}
            className="text-blue-500 text-xl font-bold"
            htmlFor={selectId}
            value="Max Reports on page:&nbsp;"
          />
          <Select
            id={selectId}
            className="w-32"
            onChange={(e) => setMaxReportsOnPage(e.target.value)}
            required
          >
            {selectMaxReportsOnPage.map((value) => {
              return (
                <option key={value} value={value}>
                  {value}
                </option>
              );
            })}
          </Select>
        </div>
      </div>

      <div className="overflow-auto">
        <Table striped>
          <Table.Head>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center bg-slate-900">
              Nr
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center bg-slate-900">
              Test UUID
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center bg-slate-900">
              Broker Type
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center bg-slate-900">
              Number of users
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center bg-slate-900">
              Number of attempts
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center bg-slate-900">
              Visible
            </Table.HeadCell>
          </Table.Head>
          <Table.Body>
            {calculateReportsOnPage().map((report, index) => {
              return (
                <Report
                  key={report.testUUID}
                  report={report}
                  index={calculateItemIndex(index)}
                  addReportToFocusedTestReportArray={
                    addReportToFocusedTestReportArray
                  }
                  removeReportFromFocusedTestReportArray={
                    removeReportFromFocusedTestReportArray
                  }
                  isFocusedReport={isFocusedReport(report.testUUID)}
                />
              );
            })}
          </Table.Body>
        </Table>
      </div>

      <div className="flex items-center m-auto">
        <div className="items-center m-auto">
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={onPageChange}
          />
        </div>
      </div>
    </div>
  );
}

export default ReportTable;
