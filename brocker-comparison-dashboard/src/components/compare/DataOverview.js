import { Tabs } from "flowbite-react";
import React, { useRef } from "react";
import DbReportsOverview from "./DbReportsOverview";
import { BiSolidReport } from "react-icons/bi";
import { FaList } from "react-icons/fa6";
import TestReportOverview from "./TestReportOverview";

function DataOverview(props) {
  const tabsRef = useRef(null);
  const tabsCustomTheme = {
    tablist: {
      styles: {
        default: "w-full grid grid-cols-2",
      },
      tabitem: {
        base: "flex text-2xl items-center justify-center p-4 rounded-t-md",
        styles: {
          default: {
            active: {
              on: "bg-slate-800 bg-opacity-80 border-4 border-slate-800 text-white p-4 active",
              off: "bg-gray-600 border-4 border-slate-800 text-slate-800",
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
      className="pb-8 bg-slate-950 bg-opacity-40 rounded-lg shadow-lg w-full max-h-[500px] overflow-auto min-h-full"
    >
      <Tabs aria-label="Full width tabs" ref={tabsRef} theme={tabsCustomTheme}>
        <Tabs.Item active title="Test Report Summary" icon={BiSolidReport}>
          <TestReportOverview testReport={props.testReport} />
        </Tabs.Item>
        <Tabs.Item title="All Reports" icon={FaList}>
          <DbReportsOverview
            testReportArray={props.testReportArray}
            tabsRef={tabsRef}
            testReportUUID={props.testReport?.testUUID}
            setTestReport={props.setTestReport}
          />
        </Tabs.Item>
      </Tabs>
    </div>
  );
}

export default DataOverview;
