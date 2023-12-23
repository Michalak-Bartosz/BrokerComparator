import { Pagination } from "flowbite-react";
import React, { useState } from "react";

function DebugInfoTable({ debugInfoList }) {
  const [currentPage, setCurrentPage] = useState(1);

  const onPageChange = (page) => setCurrentPage(page);
  return (
    <div id="debug-info-table-wrapper">
      <h1>DEBUG INFO TABLE:{debugInfoList.length}</h1>
      <Pagination
        currentPage={currentPage}
        totalPages={100}
        onPageChange={onPageChange}
      />
    </div>
  );
}

export default DebugInfoTable;
