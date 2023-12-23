import { Table } from "flowbite-react";
import React from "react";

function User({ user }) {
  return (
    <Table.Row className="bg-white dark:border-gray-700 dark:bg-gray-800">
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
      <Table.Cell>User Address</Table.Cell>
      <Table.Cell>Show Reports</Table.Cell>
    </Table.Row>
  );
}

export default User;
