import React from "react";
import { FaDotCircle } from "react-icons/fa";

function CpuMetric({ cpuMetric }) {
  return (
    <div className="text-xl m-auto">
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">System Cpu Usage:</span>
        <span>{cpuMetric.systemCpuUsagePercentage + "%"}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">App Cpu Usage:</span>
        <span>{cpuMetric.appCpuUsagePercentage + "%"}</span>
      </div>
    </div>
  );
}

export default CpuMetric;
