import React from "react";
import { FaDotCircle } from "react-icons/fa";

function CPUMetric({ cpuMetric }) {
  return (
    <div id="cpu-metric-wrapper" className="block text-center">
      <div className="py-4">
        <h1 className="flex ml-auto mr-0 text-2xl border-y-2 border-slate-600  rounded-md pl-6 py-2">
          CPU Metrics
        </h1>
      </div>

      <div className="grid grid-cols-2 gap-6">
        <div id="system-cpu-metrics">
          <h2 className="mb-2 text-blue-500 text-2xl font-bold">
            System CPU Metrics
          </h2>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Max System Cpu Usage:"}&nbsp;</span>
            <span>{cpuMetric.maxSystemCpuUsagePercentage + "%"}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Min System Cpu Usage:"}&nbsp;</span>
            <span>{cpuMetric.minSystemCpuUsagePercentage + "%"}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">
              {"Average System Cpu Usage:"}&nbsp;
            </span>
            <span>{cpuMetric.averageSystemCpuUsagePercentage + "%"}</span>
          </div>
        </div>
        <div id="app-cpu-metrics">
          <h2 className="mb-2 text-blue-500 text-2xl font-bold">
            App CPU Metrics
          </h2>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Max App Cpu Usage:"}&nbsp;</span>
            <span>{cpuMetric.maxAppCpuUsagePercentage + "%"}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Min App Cpu Usage:"}&nbsp;</span>
            <span>{cpuMetric.minAppCpuUsagePercentage + "%"}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">
              {"Average App Cpu Usage:"}&nbsp;
            </span>
            <span>{cpuMetric.averageAppCpuUsagePercentage + "%"}</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CPUMetric;
