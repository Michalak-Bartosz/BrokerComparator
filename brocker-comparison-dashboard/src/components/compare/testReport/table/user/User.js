import { Table } from "flowbite-react";
import React, { useState } from "react";
import AddressModal from "./address/AddressModal";
import UserReportsModal from "./userReport/UserReportsModal";

function User({ user, index }) {
  const [openAddressModal, setOpenAddressModal] = useState(false);
  const [openReportsModal, setOpenReportsModal] = useState(false);
  return (
    <Table.Row className="bg-white dark:border-gray-700 dark:bg-gray-800">
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {index}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {user.uuid}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {user.testUUID}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap">{user.idNumber}</Table.Cell>
      <Table.Cell>{user.name}</Table.Cell>
      <Table.Cell>{user.lastName}</Table.Cell>
      <Table.Cell>{user.email}</Table.Cell>
      <Table.Cell className="whitespace-nowrap">{user.cellPhone}</Table.Cell>
      <Table.Cell>
        <button
          id="show-test-data-button"
          className="flex mx-auto px-4 py-1 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold"
          onClick={() => setOpenAddressModal(true)}
        >
          Show
        </button>
        <AddressModal
          address={user.address}
          openModal={openAddressModal}
          setOpenModal={setOpenAddressModal}
        />
      </Table.Cell>
      <Table.Cell>
        <button
          id="show-test-data-button"
          className="flex mx-auto px-4 py-1 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold"
          onClick={() => setOpenReportsModal(true)}
        >
          Show
        </button>
        <UserReportsModal
          userReports={user.reports}
          openModal={openReportsModal}
          setOpenModal={setOpenReportsModal}
        />
      </Table.Cell>
    </Table.Row>
  );
}

export default User;
