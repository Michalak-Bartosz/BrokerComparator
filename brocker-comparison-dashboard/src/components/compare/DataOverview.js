import { Tabs } from "flowbite-react";
import React from "react";
import DbReportsOverview from "./DbReportsOverview";
import { BiSolidReport } from "react-icons/bi";
import { FaList } from "react-icons/fa6";
import TestReportOverview from "./TestReportOverview";
import { BsArrowsFullscreen } from "react-icons/bs";

function DataOverview(props) {
  const tabsCustomTheme = {
    tablist: {
      styles: {
        default: "w-full grid grid-cols-2",
      },
      tabitem: {
        base: "flex text-2xl items-center justify-center p-4 bg-gray-600",
        styles: {
          default: {
            active: {
              on: "bg-slate-800 bg-opacity-80 border-slate-800 text-white active rounded-none",
              off: "bg-gray-600 border-slate-800 text-slate-800 rounded-none",
            },
          },
        },
        icon: "mr-2 h-15 w-15",
      },
    },
    tabitemcontainer: {
      styles: {
        default: "px-14 py-4",
      },
    },
  };

  return (
    <div
      id="db-overview"
      className={`pb-8 bg-slate-950 bg-opacity-40 rounded-lg shadow-lg w-full ${
        props.fullscreen ? "max-h-full" : "max-h-[500px]"
      } overflow-auto min-h-full`}
    >
      {props.setOpenFullscreenDataOverviewModal && (
        <div className="flex w-full m-auto bg-gray-900 rounded-t-md border-b-4 border-blue-500">
          <span className="m-auto ml-4 font-bold text-blue-500">
            Report Overview
          </span>
          <button
            className="flex aspect-square m-auto mr-0 items-center font-bold text-white bg-blue-800 border-4 border-blue-700 rounded-t-md p-2 hover:bg-blue-600"
            onClick={() => props.setOpenFullscreenDataOverviewModal(true)}
          >
            <BsArrowsFullscreen />
          </button>
        </div>
      )}
      <Tabs aria-label="Full width tabs" theme={tabsCustomTheme}>
        <Tabs.Item active title="Test Report Summary" icon={BiSolidReport}>
          <TestReportOverview
            focusedTestReportArray={props.focusedTestReportArray}
          />
        </Tabs.Item>
        <Tabs.Item title="All Reports" icon={FaList}>
          <DbReportsOverview
            testReportArray={props.testReportArray}
            focusedTestReportUUIDArray={props.focusedTestReportArray?.map(
              (testReport) => testReport.testUUID
            )}
            addReportToFocusedTestReportArray={
              props.addReportToFocusedTestReportArray
            }
            removeReportFromFocusedTestReportArray={
              props.removeReportFromFocusedTestReportArray
            }
          />
        </Tabs.Item>
      </Tabs>
    </div>
  );
}

export default DataOverview;
