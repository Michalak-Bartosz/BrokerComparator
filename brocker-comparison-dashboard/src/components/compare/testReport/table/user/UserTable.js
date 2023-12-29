import { Label, Pagination, Select, Table } from "flowbite-react";
import React, { useEffect, useState } from "react";
import User from "./User";

function UserTable({ userList }) {
  const [currentPage, setCurrentPage] = useState(1);
  const [maxUsersOnPage, setMaxUsersOnMage] = useState(10);
  const [totalPages, setTotalPages] = useState(
    Math.ceil(userList.length / maxUsersOnPage)
  );

  const selectMaxUsersOnPage = [10, 25, 50, 100, 200, 300, 400, 500];

  useEffect(() => {
    setTotalPages(Math.ceil(userList.length / maxUsersOnPage));
  }, [maxUsersOnPage, userList.length]);

  const onPageChange = (page) => {
    setCurrentPage(page);
  };

  const calculateUsersOnPage = () => {
    return userList.slice(
      (currentPage - 1) * maxUsersOnPage,
      currentPage * maxUsersOnPage
    );
  };

  return (
    <div>
      <div className="flex py-6 px-4">
        <div className="flex items-center text-xl m-auto ml-0">
          <span className="flex text-blue-500 font-bold">
            Users in table:&nbsp;
          </span>
          <span>{userList.length}</span>
        </div>
        <div className="flex items-center m-auto mr-0">
          <Label
            id={(Math.random() + 1).toString(36).substring(7)}
            className="text-blue-500 text-xl font-bold"
            htmlFor="max-user-on-page-select"
            value="Max Users on page:&nbsp;"
          />
          <Select
            id="max-user-on-page-select"
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
              ID Number
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Name
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Last Name
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Email
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Cell Phone
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Address
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center">
              Reports
            </Table.HeadCell>
          </Table.Head>
          <Table.Body className="divide-y">
            {calculateUsersOnPage().map((user, index) => {
              return <User key={user.uuid} user={user} index={index + 1} />;
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

export default UserTable;
