import { Label, Pagination, Select, Table } from "flowbite-react";
import React, { useEffect, useState } from "react";
import Comment from "./Comment";

function CommentTable({ comments }) {
  const [currentPage, setCurrentPage] = useState(1);
  const [maxCommentsOnPage, setMaxCommentsOnMage] = useState(10);
  const [totalPages, setTotalPages] = useState(
    Math.ceil(comments.length / maxCommentsOnPage)
  );

  const selectMaxCommentsOnPage = [10, 25, 50, 100, 200, 300, 400, 500];

  useEffect(() => {
    setTotalPages(Math.ceil(comments.length / maxCommentsOnPage));
  }, [maxCommentsOnPage, comments.length]);

  const onPageChange = (page) => {
    setCurrentPage(page);
  };

  const calculateItemIndex = (index) => {
    return (currentPage - 1) * maxCommentsOnPage + index + 1;
  };

  const calculateCommmentsOnPage = () => {
    return comments.slice(
      (currentPage - 1) * maxCommentsOnPage,
      currentPage * maxCommentsOnPage
    );
  };
  return (
    <div>
      <div className="flex py-6 px-4">
        <div className="flex items-center text-xl m-auto ml-0">
          <span className="flex text-blue-500 font-bold">
            Comments in table:&nbsp;
          </span>
          <span>{comments.length}</span>
        </div>
        <div className="flex items-center m-auto mr-0">
          <Label
            id={(Math.random() + 1).toString(36).substring(7)}
            className="text-blue-500 text-xl font-bold"
            htmlFor="max-comments-on-page-select"
            value="Max Comments on page:&nbsp;"
          />
          <Select
            id="max-comments-on-page-select"
            className="w-32"
            onChange={(e) => setMaxCommentsOnMage(e.target.value)}
            required
          >
            {selectMaxCommentsOnPage.map((value) => {
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
              Report UUID
            </Table.HeadCell>
            <Table.HeadCell className="text-blue-500 font-bold text-xl text-center px-40">
              description
            </Table.HeadCell>
          </Table.Head>
          <Table.Body className="divide-y">
            {calculateCommmentsOnPage().map((comment, index) => {
              return (
                <Comment
                  key={comment.uuid}
                  comment={comment}
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

export default CommentTable;
