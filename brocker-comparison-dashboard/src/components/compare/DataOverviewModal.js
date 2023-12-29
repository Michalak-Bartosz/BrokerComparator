import { Modal } from "flowbite-react";
import React from "react";
import DataOverview from "./DataOverview";
import { FaWindowClose } from "react-icons/fa";

function DataOverviewModal(props) {
  const customModalTheme = {
    content: {
      base: "relative h-full w-full p-4",
      inner:
        "relative rounded-lg bg-blue-white shadow dark:bg-gray-700 flex flex-col min-h-full border-4",
    },

    header: {
      title: "text-xl font-medium text-white dark:text-white",
    },
  };
  return (
    <Modal
      show={props.openFullscreenDataOverviewModal}
      className="p-12"
      size="10xl"
      theme={customModalTheme}
      onClose={() => props.setOpenFullscreenDataOverviewModal(false)}
    >
      <Modal.Header className="bg-gray-800">Data Overview</Modal.Header>
      <Modal.Body className="bg-slate-700 text-white">
        <DataOverview
          focusedTestReportArray={props.focusedTestReportArray}
          addReportToFocusedTestReportArray={
            props.addReportToFocusedTestReportArray
          }
          removeReportFromFocusedTestReportArray={
            props.removeReportFromFocusedTestReportArray
          }
          testReportArray={props.testReportArray}
          fullscreen={true}
        />
      </Modal.Body>
      <Modal.Footer className="p-0 bg-gray-800">
        <button
          id="close-test-data-modal-button"
          className="flex items-center mx-auto my-6 px-2 py-1 outline outline-offset-2 outline-rose-700 rounded-md bg-rose-800 hover:bg-rose-700 disabled:bg-slate-400 disabled:outline-slate-500 text-white font-bold text-xl"
          onClick={() => props.setOpenFullscreenDataOverviewModal(false)}
        >
          <FaWindowClose className="text-white mr-4" />
          Close
        </button>
      </Modal.Footer>
    </Modal>
  );
}

export default DataOverviewModal;
