import React from "react";
import { FaDotCircle, FaSquare } from "react-icons/fa";

function CPUMetric({ cpuMetric, brokerCpuMetric }) {
  const getCpuMetricsForBrokerTypes = () => {
    return (
      <div>
        {brokerCpuMetric && (
          <div className="grid grid-row-2 gap-4 mt-4">
            {brokerCpuMetric.map((brokerCpuMetric) => {
              return (
                <div key={brokerCpuMetric.brokerType} className="block">
                  <div className="flex items-center text-xl font-bold text-blue-800 text-left">
                    <FaSquare className="mr-2 text-xl" />
                    <span>{"[" + brokerCpuMetric.brokerType + "]:"}</span>
                  </div>
                  <div className="grid grid-cols-2 gap-x-4">
                    <div>
                      <h2 className="mb-2 text-blue-800 text-2xl font-bold">
                        System CPU Metrics
                      </h2>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Min System Cpu Usage:"}&nbsp;
                        </span>
                        <span>
                          {brokerCpuMetric?.minSystemCpuUsagePercentage + "%"}
                        </span>
                      </div>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Average System Cpu Usage:"}&nbsp;
                        </span>
                        <span>
                          {brokerCpuMetric?.averageSystemCpuUsagePercentage +
                            "%"}
                        </span>
                      </div>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Max System Cpu Usage:"}&nbsp;
                        </span>
                        <span>
                          {brokerCpuMetric?.maxSystemCpuUsagePercentage + "%"}
                        </span>
                      </div>
                    </div>
                    <div id="app-cpu-metrics">
                      <h2 className="mb-2 text-blue-800 text-2xl font-bold">
                        App CPU Metrics
                      </h2>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Min App Cpu Usage:"}&nbsp;
                        </span>
                        <span>
                          {brokerCpuMetric?.minAppCpuUsagePercentage + "%"}
                        </span>
                      </div>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Average App Cpu Usage:"}&nbsp;
                        </span>
                        <span>
                          {brokerCpuMetric?.averageAppCpuUsagePercentage + "%"}
                        </span>
                      </div>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Max App Cpu Usage:"}&nbsp;
                        </span>
                        <span>
                          {brokerCpuMetric?.maxAppCpuUsagePercentage + "%"}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </div>
    );
  };
  return (
    <div id="cpu-metric-wrapper" className="block text-center">
      <div className="py-4">
        <h1 className="flex ml-auto mr-0 text-2xl border-y-2 border-slate-600 rounded-md pl-6 py-2 font-bold text-blue-800">
          CPU Metrics
        </h1>
      </div>

      <div>
        <div className="grid grid-cols-2 gap-6">
          <div id="system-cpu-metrics">
            <h2 className="mb-2 text-blue-800 text-2xl font-bold">
              System CPU Metrics
            </h2>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"Min System Cpu Usage:"}&nbsp;</span>
              <span>{cpuMetric?.minSystemCpuUsagePercentage + "%"}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">
                {"Average System Cpu Usage:"}&nbsp;
              </span>
              <span>{cpuMetric?.averageSystemCpuUsagePercentage + "%"}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"Max System Cpu Usage:"}&nbsp;</span>
              <span>{cpuMetric?.maxSystemCpuUsagePercentage + "%"}</span>
            </div>
          </div>
          <div id="app-cpu-metrics">
            <h2 className="mb-2 text-blue-800 text-2xl font-bold">
              App CPU Metrics
            </h2>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"Min App Cpu Usage:"}&nbsp;</span>
              <span>{cpuMetric?.minAppCpuUsagePercentage + "%"}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">
                {"Average App Cpu Usage:"}&nbsp;
              </span>
              <span>{cpuMetric?.averageAppCpuUsagePercentage + "%"}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"Max App Cpu Usage:"}&nbsp;</span>
              <span>{cpuMetric?.maxAppCpuUsagePercentage + "%"}</span>
            </div>
          </div>
        </div>
        {getCpuMetricsForBrokerTypes()}
      </div>
    </div>
  );
}

export default CPUMetric;
