import { Label, Pagination, Select, Table } from "flowbite-react";
import React, { useEffect, useState } from "react";
import UserReport from "./UserReport";

function UserReportsTable({ userReports }) {
  const [currentPage, setCurrentPage] = useState(1);
  const [maxUserReportsOnPage, setMaxUserReportsOnMage] = useState(10);
  const [totalPages, setTotalPages] = useState(
    Math.ceil(userReports.length / maxUserReportsOnPage)
  );

  const selectMaxUserReportsOnPage = [10, 25, 50, 100, 200, 300, 400, 500];

  useEffect(() => {
    setTotalPages(Math.ceil(userReports.length / maxUserReportsOnPage));
  }, [maxUserReportsOnPage, userReports.length]);

  const onPageChange = (page) => {
    setCurrentPage(page);
  };

  const calculateItemIndex = (index) => {
    return (currentPage - 1) * maxUserReportsOnPage + index + 1;
  };

  const calculateUserReportsOnPage = () => {
    return userReports.slice(
      (currentPage - 1) * maxUserReportsOnPage,
      currentPage * maxUserReportsOnPage
    );
  };
  return (
    <div>
      <div className="flex py-6 px-4">
        <div className="flex items-center text-xl m-auto ml-0">
          <span className="flex text-blue-500 font-bold">
            Reports in table:&nbsp;
          </span>
          <span>{userReports.length}</span>
        </div>
        <div className="flex items-center m-auto mr-0">
          <Label
            id={(Math.random() + 1).toString(36).substring(7)}
            className="text-blue-500 text-xl font-bold"
            htmlFor="max-user-reports-on-page-select"
            value="Max Reports on page:&nbsp;"
          />
          <Select
            id="max-user-reports-on-page-select"
            className="w-32"
            onChange={(e) => setMaxUserReportsOnMage(e.target.value)}
            required
          >
            {selectMaxUserReportsOnPage.map((value) => {
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
              User UUID
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Summary
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center px-40">
              Description
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Status
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Comments
            </Table.HeadCell>
          </Table.Head>
          <Table.Body className="divide-y">
            {calculateUserReportsOnPage().map((userReport, index) => {
              return (
                <UserReport
                  key={userReport.uuid}
                  userReport={userReport}
                  index={calculateItemIndex(index)}
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

export default UserReportsTable;
