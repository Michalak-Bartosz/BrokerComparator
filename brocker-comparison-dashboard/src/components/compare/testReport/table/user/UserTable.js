import { Label, Pagination, Select, Table } from "flowbite-react";
import React, { useEffect, useState } from "react";
import User from "./User";

function UserTable({ userList }) {
  const [currentPage, setCurrentPage] = useState(1);
  const [maxUsersOnPage, setMaxUsersOnMage] = useState(25);
  const [totalPages, setTotalPages] = useState(
    Math.ceil(userList.length / maxUsersOnPage)
  );

  const selectMaxUsersOnPage = [25, 50, 100, 200, 300, 400, 500];

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
            htmlFor="max-user-on-page-select"
            value="Max users on page:&nbsp;"
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

      <div className="overflow-x-scroll">
        <Table striped>
          <Table.Head>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              UUID
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              Test UUID
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              ID Number
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              Name
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              Last Name
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              Email
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              Cell Phone
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              Address
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl">
              Show reports
            </Table.HeadCell>
          </Table.Head>
          <Table.Body className="divide-y">
            {calculateUsersOnPage().map((user) => {
              return <User key={user.uuid} user={user} />;
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
