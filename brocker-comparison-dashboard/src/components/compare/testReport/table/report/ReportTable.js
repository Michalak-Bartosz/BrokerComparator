import { Label, Pagination, Select, Table } from "flowbite-react";
import React, { useEffect, useState } from "react";
import Report from "./Report";

function ReportTable({
  testReportArray,
  tabsRef,
  setTestReport,
  testReportUUID,
}) {
  const [currentPage, setCurrentPage] = useState(1);
  const [maxUsersOnPage, setMaxUsersOnMage] = useState(25);
  const [totalPages, setTotalPages] = useState(
    Math.ceil(testReportArray.length / maxUsersOnPage)
  );

  const selectMaxUsersOnPage = [25, 50, 100, 200, 300, 400, 500];

  useEffect(() => {
    setTotalPages(Math.ceil(testReportArray.length / maxUsersOnPage));
  }, [maxUsersOnPage, testReportArray.length]);

  const onPageChange = (page) => {
    setCurrentPage(page);
  };

  const calculateReportsOnPage = () => {
    return testReportArray.slice(
      (currentPage - 1) * maxUsersOnPage,
      currentPage * maxUsersOnPage
    );
  };

  return (
    <div>
      <div className="flex pb-6 pt-2 px-4">
        <div className="flex items-center text-xl m-auto ml-0">
          <span className="flex text-blue-500 font-bold">
            Number of reports:&nbsp;
          </span>
          <span>{testReportArray.length}</span>
        </div>
        <div className="flex items-center m-auto mr-0">
          <Label
            className="text-blue-500 text-xl font-bold"
            htmlFor="max-reports-on-page-select"
            value="Max users on page:&nbsp;"
          />
          <Select
            id="max-reports-on-page-select"
            className="w-32"
            onChange={(e) => setMaxUsersOnMage(e.target.value)}
            required
          >
            {selectMaxUsersOnPage.map((value) => {
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
              Number of users in test
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center bg-slate-900">
              Number of attempts
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center bg-slate-900">
              Test report overview
            </Table.HeadCell>
          </Table.Head>
          <Table.Body>
            {calculateReportsOnPage().map((report, index) => {
              return (
                <Report
                  key={report.testUUID}
                  report={report}
                  index={index + 1}
                  tabsRef={tabsRef}
                  setTestReport={setTestReport}
                  isCurrentReport={
                    testReportUUID === report.testUUID ? true : false
                  }
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
