import { Table } from "flowbite-react";
import React from "react";

function Comment({ comment, index }) {
  return (
    <Table.Row className="bg-white dark:border-gray-700 dark:bg-gray-800">
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {index}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {comment.uuid}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {comment.reportUuid}
      </Table.Cell>
      <Table.Cell className="text-ellipsis whitespace-pre-wrap">
        {comment.description}
      </Table.Cell>
    </Table.Row>
  );
}

export default Comment;
