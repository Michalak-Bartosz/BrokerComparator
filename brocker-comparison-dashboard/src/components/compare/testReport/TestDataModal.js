import { Modal, Tabs } from "flowbite-react";
import { FaWindowClose } from "react-icons/fa";
import { FaUsersLine } from "react-icons/fa6";
import { MdDataObject } from "react-icons/md";
import React from "react";
import UserTable from "./table/user/UserTable";
import DebugInfoTable from "./table/debugInfo/DebugInfoTable";

function TestDataModal(props) {
  const tabsCustomTheme = {
    tablist: {
      styles: {
        default: "w-full divide-x divide-gray-200 shadow grid grid-flow-col",
      },
      tabitem: {
        base: "flex items-center justify-center p-4",
        styles: {
          default: {
            active: {
              on: "text-xl bg-white text-black active rounded-none p-4",
              off: "text-xl bg-slate-200 text-slate-500 rounded-none p-4",
            },
          },
        },
      },
    },
  };

  return (
    <div id="test-data-modal-wrapper">
      <Modal
        show={props.openModal}
        size="10xl"
        className="p-24"
        onClose={() => props.setOpenModal(false)}
      >
        <Modal.Header>Test Data</Modal.Header>
        <Modal.Body>
          <div className="space-y-6">
            <div className="overflow-x-auto rounded-t-md">
              <Tabs aria-label="Full width tabs" theme={tabsCustomTheme}>
                <Tabs.Item active title="Users" icon={FaUsersLine}>
                  <UserTable userList={props?.userList} />
                </Tabs.Item>
                <Tabs.Item title="Debug Info" icon={MdDataObject}>
                  <DebugInfoTable debugInfoList={props?.debugInfoList} />
                </Tabs.Item>
              </Tabs>
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer className="p-0">
          <button
            id="close-test-data-modal-button"
            className="flex items-center mx-auto my-6 px-2 py-1 outline outline-offset-2 outline-rose-700 rounded-md bg-rose-800 hover:bg-rose-700 disabled:bg-slate-400 disabled:outline-slate-500 text-white font-bold text-xl"
            onClick={() => props.setOpenModal(false)}
          >
            <FaWindowClose className="text-white mr-4" />
            Close
          </button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default TestDataModal;
