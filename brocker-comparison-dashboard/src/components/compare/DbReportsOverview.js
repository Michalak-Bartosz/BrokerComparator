import React from "react";
import ReportTable from "./testReport/table/report/ReportTable";

function DbReportsOverview(props) {
  return (
    <div id="test-report-wrapper" className="block h-min text-xl">
      <div>
        {props.testReportArray.length !== 0 ? (
          <ReportTable
            testReportArray={props.testReportArray}
            tabsRef={props.tabsRef}
            testReportUUID={props.testReportUUID}
            setTestReport={props.setTestReport}
          />
        ) : (
          <h1 className="font-bold text-center m-auto mt-10">
            No reports. Start new one!
          </h1>
        )}
      </div>
    </div>
  );
}

export default DbReportsOverview;
