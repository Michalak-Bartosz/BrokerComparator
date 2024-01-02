import { Table } from "flowbite-react";
import React, { useState } from "react";
import CommentModal from "./comment/CommentModal";

function UserReport({ userReport, index }) {
  const [openCommentsModal, setOpenCommentsModal] = useState(false);
  return (
    <Table.Row className="bg-white dark:border-gray-700 dark:bg-gray-800">
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {index}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {userReport.uuid}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {userReport.testUUID}
      </Table.Cell>
      <Table.Cell className="whitespace-nowrap font-medium text-gray-900">
        {userReport.userUuid}
      </Table.Cell>
      <Table.Cell>{userReport.summary}</Table.Cell>
      <Table.Cell>{userReport.description}</Table.Cell>
      <Table.Cell>{userReport.status}</Table.Cell>
      <Table.Cell>
        <button
          id="show-test-data-button"
          className="flex mx-auto px-4 py-1 outline outline-offset-2 outline-blue-700 rounded-md bg-blue-800 hover:bg-blue-600 text-white font-bold"
          onClick={() => setOpenCommentsModal(true)}
        >
          Comments
        </button>
        <CommentModal
          comments={userReport.comments}
          openModal={openCommentsModal}
          setOpenModal={setOpenCommentsModal}
        />
      </Table.Cell>
    </Table.Row>
  );
}

export default UserReport;
