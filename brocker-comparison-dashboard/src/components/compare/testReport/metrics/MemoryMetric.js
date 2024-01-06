import React from "react";
import { FaDotCircle, FaSquare } from "react-icons/fa";

function MemoryMetric({ memoryMetric, brokerMemoryMetric }) {
  const getMemoryMetricsForBrokerTypes = () => {
    return (
      <div>
        {brokerMemoryMetric && (
          <div className="grid grid-row-2 gap-4 mt-4">
            {brokerMemoryMetric.map((brokermemoryMetric) => {
              return (
                <div key={brokermemoryMetric.brokerType} className="block">
                  <div className="flex items-center text-xl font-bold text-blue-800 text-left">
                    <FaSquare className="mr-2 text-xl" />
                    <span>{"[" + brokermemoryMetric.brokerType + "]:"}</span>
                  </div>
                  <div className="grid grid-cols-2 gap-6">
                    <div id="used-heap-memory-metrics">
                      <h2 className="my-2 text-blue-800 text-2xl font-bold">
                        Used Heap Memory Metrics
                      </h2>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Max Used Heap Memory:"}&nbsp;
                        </span>
                        <span>
                          {brokermemoryMetric?.maxUsedHeapMemoryGB + "GB"}
                        </span>
                      </div>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Min Used Heap Memory:"}&nbsp;
                        </span>
                        <span>
                          {brokermemoryMetric?.minUsedHeapMemoryGB + "GB"}
                        </span>
                      </div>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Average Used Heap Memory:"}&nbsp;
                        </span>
                        <span>
                          {brokermemoryMetric?.averageUsedHeapMemoryGB + "GB"}
                        </span>
                      </div>
                    </div>
                    <div id="committed-memory-metrics">
                      <h2 className="my-2 text-blue-800 text-2xl font-bold">
                        Committed Memory Metrics
                      </h2>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Max Committed Memory:"}&nbsp;
                        </span>
                        <span>
                          {brokermemoryMetric?.maxCommittedMemoryGB + "GB"}
                        </span>
                      </div>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Min Committed Memory:"}&nbsp;
                        </span>
                        <span>
                          {brokermemoryMetric?.minCommittedMemoryGB + "GB"}
                        </span>
                      </div>
                      <div className="flex items-center">
                        <FaDotCircle className="text-sm mr-2" />
                        <span className="font-bold">
                          {"Average Committed Memory:"}&nbsp;
                        </span>
                        <span>
                          {brokermemoryMetric?.averageCommittedMemoryGB + "GB"}
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
    <div id="memory-metric-wrapper" className="block text-center">
      <div className="py-4">
        <h1 className="flex ml-auto mr-0 text-2xl border-y-2 border-slate-600 rounded-md pl-6 py-2 font-bold text-blue-800">
          Memory Metrics
        </h1>
      </div>
      <h2 className="mb-2 text-blue-800 text-2xl text-left font-bold">
        Constants
      </h2>
      <div className="grid grid-cols-2 gap-6">
        <div className="flex items-center">
          <FaDotCircle className="text-sm mr-2" />
          <span className="font-bold">{"Initial Memory:"}&nbsp;</span>
          <span>{memoryMetric?.initialMemoryGB + "GB"}</span>
        </div>
        <div className="flex items-center">
          <FaDotCircle className="text-sm mr-2" />
          <span className="font-bold">{"Max Heap Memory:"}&nbsp;</span>
          <span>{memoryMetric?.maxHeapMemoryGB + "GB"}</span>
        </div>
      </div>
      <div>
        <div className="grid grid-cols-2 gap-6">
          <div id="used-heap-memory-metrics">
            <h2 className="my-2 text-blue-800 text-2xl font-bold">
              Used Heap Memory Metrics
            </h2>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"Max Used Heap Memory:"}&nbsp;</span>
              <span>{memoryMetric?.maxUsedHeapMemoryGB + "GB"}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"Min Used Heap Memory:"}&nbsp;</span>
              <span>{memoryMetric?.minUsedHeapMemoryGB + "GB"}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">
                {"Average Used Heap Memory:"}&nbsp;
              </span>
              <span>{memoryMetric?.averageUsedHeapMemoryGB + "GB"}</span>
            </div>
          </div>
          <div id="committed-memory-metrics">
            <h2 className="my-2 text-blue-800 text-2xl font-bold">
              Committed Memory Metrics
            </h2>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"Max Committed Memory:"}&nbsp;</span>
              <span>{memoryMetric?.maxCommittedMemoryGB + "GB"}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"Min Committed Memory:"}&nbsp;</span>
              <span>{memoryMetric?.minCommittedMemoryGB + "GB"}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">
                {"Average Committed Memory:"}&nbsp;
              </span>
              <span>{memoryMetric?.averageCommittedMemoryGB + "GB"}</span>
            </div>
          </div>
        </div>
        {getMemoryMetricsForBrokerTypes()}
      </div>
    </div>
  );
}

export default MemoryMetric;
