import React from "react";
import ReportTable from "./testReport/table/report/ReportTable";

function DbReportsOverview(props) {
  return (
    <div id="test-report-wrapper" className="block h-min text-xl">
      <div>
        {props.testReportArray.length !== 0 ? (
          <ReportTable
            testReportArray={props.testReportArray}
            focusedTestReportUUIDArray={props.focusedTestReportUUIDArray}
            clearFocusedTestReportArray={props.clearFocusedTestReportArray}
            addReportToFocusedTestReportArray={
              props.addReportToFocusedTestReportArray
            }
            removeReportFromFocusedTestReportArray={
              props.removeReportFromFocusedTestReportArray
            }
          />
        ) : (
          <h1 className="font-bold text-center text-blue-500 m-auto mt-12 text-2xl">
            No reports. Start new test!
          </h1>
        )}
      </div>
    </div>
  );
}

export default DbReportsOverview;
