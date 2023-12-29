import { Label, Pagination, Select, Table } from "flowbite-react";
import React, { useEffect, useState } from "react";
import DebugInfo from "./DebugInfo";

function DebugInfoTable({ debugInfoList }) {
  const [currentPage, setCurrentPage] = useState(1);
  const [maxDebugInfosOnPage, setMaxDebugInfosOnMage] = useState(10);
  const [totalPages, setTotalPages] = useState(
    Math.ceil(debugInfoList.length / maxDebugInfosOnPage)
  );

  const selectMaxDebugInfosOnPage = [10, 25, 50, 100, 200, 300, 400, 500];

  useEffect(() => {
    setTotalPages(Math.ceil(debugInfoList.length / maxDebugInfosOnPage));
  }, [maxDebugInfosOnPage, debugInfoList.length]);

  const onPageChange = (page) => {
    setCurrentPage(page);
  };

  const calculateDebugInfoOnPage = () => {
    return debugInfoList
      .sort(function (a, b) {
        return a.testStatusPercentage - b.testStatusPercentage;
      })
      .slice(
        (currentPage - 1) * maxDebugInfosOnPage,
        currentPage * maxDebugInfosOnPage
      );
  };

  return (
    <div id="debug-info-table-wrapper">
      <div className="flex py-6 px-4">
        <div className="flex items-center text-xl m-auto ml-0">
          <span className="flex text-blue-500 font-bold">
            DebugInfos in table:&nbsp;
          </span>
          <span>{debugInfoList.length}</span>
        </div>
        <div className="flex items-center m-auto mr-0">
          <Label
            id={(Math.random() + 1).toString(36).substring(7)}
            className="text-blue-500 text-xl font-bold"
            htmlFor="max-debug-infos-on-page-select"
            value="Max Debug Infos on page:&nbsp;"
          />
          <Select
            id="max-debug-infos-on-page-select"
            className="w-32"
            onChange={(e) => setMaxDebugInfosOnMage(e.target.value)}
            required
          >
            {selectMaxDebugInfosOnPage.map((value) => {
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
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Nr
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              UUID
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Test UUID
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Broker Type
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Test Status Percentage
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Produced Timestamp
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Consumed Timestamp
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Delta Timestamp [ms]
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Count Of Produced Messages
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Count Of Consumed Messages
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Producer Memory Metrics
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Consumer Memory Metrics
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Producer CPU Metrics
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Consumer CPU Metrics
            </Table.HeadCell>
          </Table.Head>
          <Table.Body className="divide-y">
            {calculateDebugInfoOnPage().map((debugInfo, index) => {
              return (
                <DebugInfo
                  key={debugInfo.uuid}
                  debugInfo={debugInfo}
                  index={index + 1}
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

export default DebugInfoTable;
