import React, { useEffect, useState } from "react";
import TestReportSummary from "./testReport/TestReportSummary";

function TestReportOverview({ focusedTestReportArray, setOpenFullscreenDataOverviewModal }) {
  const [currentTestReportIndex, setCurrentTestReportIndex] = useState(0);
  const isReportFocused = (index) => {
    return index === currentTestReportIndex;
  };

  const increaseCurrentTestReportIndex = () => {
    if (currentTestReportIndex + 1 > focusedTestReportArray.length - 1) {
      setCurrentTestReportIndex(0);
      return;
    }
    setCurrentTestReportIndex((prevIndex) => prevIndex + 1);
  };

  const decreseCurrentTestReportIndex = () => {
    if (currentTestReportIndex - 1 < 0) {
      setCurrentTestReportIndex(focusedTestReportArray.length - 1);
      return;
    }
    setCurrentTestReportIndex((prevIndex) => prevIndex - 1);
  };

  useEffect(() => {
    if (currentTestReportIndex > focusedTestReportArray.length - 1) {
      setCurrentTestReportIndex(0);
    }
  }, [currentTestReportIndex, focusedTestReportArray]);

  return (
    <div>
      {focusedTestReportArray.length === 0 && (
        <h1 className="flex w-max font-bold text-center m-auto mt-12 text-blue-500 text-2xl">
          Choose report from "All Reports" tab or start new test to show test
          report summray!
        </h1>
      )}
      {focusedTestReportArray.map((testReport, index) => {
        return (
          <TestReportSummary
            key={testReport.testUUID + 1}
            testReport={testReport}
            isReportFocused={isReportFocused(index)}
            increaseCurrentTestReportIndex={increaseCurrentTestReportIndex}
            decreseCurrentTestReportIndex={decreseCurrentTestReportIndex}
          />
        );
      })}
    </div>
  );
}

export default TestReportOverview;
