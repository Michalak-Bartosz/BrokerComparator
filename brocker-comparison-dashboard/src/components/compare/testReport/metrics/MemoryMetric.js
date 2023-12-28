import React from "react";
import { FaDotCircle } from "react-icons/fa";

function MemoryMetric({ memoryMetric }) {
  return (
    <div id="memory-metric-wrapper" className="block text-center">
      <div className="py-4">
        <h1 className="flex ml-auto mr-0 text-2xl border-y-2 border-slate-600 rounded-md pl-6 py-2">
          Memory Metrics
        </h1>
      </div>
      <h2 className="mb-2 text-blue-500 text-2xl text-left font-bold">
        Constants
      </h2>
      <div className="grid grid-cols-2 gap-6">
        <div className="flex items-center">
          <FaDotCircle className="text-sm mr-2" />
          <label className="font-bold">{"Initial Memory:"}&nbsp;</label>
          <span>{memoryMetric.initialMemoryGB + "GB"}</span>
        </div>
        <div className="flex items-center">
          <FaDotCircle className="text-sm mr-2" />
          <label className="font-bold">{"Max Heap Memory:"}&nbsp;</label>
          <span>{memoryMetric.maxHeapMemoryGB + "GB"}</span>
        </div>
      </div>
      <div className="grid grid-cols-2 gap-6">
        <div id="used-heap-memory-metrics">
          <h2 className="my-2 text-blue-500 text-2xl font-bold">
            Used Heap Memory Metrics
          </h2>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <label className="font-bold">{"Max Used Heap Memory:"}&nbsp;</label>
            <span>{memoryMetric.maxUsedHeapMemoryGB + "GB"}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <label className="font-bold">{"Min Used Heap Memory:"}&nbsp;</label>
            <span>{memoryMetric.minUsedHeapMemoryGB + "GB"}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <label className="font-bold">
              {"Average Used Heap Memory:"}&nbsp;
            </label>
            <span>{memoryMetric.averageUsedHeapMemoryGB + "GB"}</span>
          </div>
        </div>
        <div id="committed-memory-metrics">
          <h2 className="my-2 text-blue-500 text-2xl font-bold">
            Committed Memory Metrics
          </h2>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <label className="font-bold">{"Max Committed Memory:"}&nbsp;</label>
            <span>{memoryMetric.maxCommittedMemoryGB + "GB"}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <label className="font-bold">{"Min Committed Memory:"}&nbsp;</label>
            <span>{memoryMetric.minCommittedMemoryGB + "GB"}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <label className="font-bold">
              {"Average Committed Memory:"}&nbsp;
            </label>
            <span>{memoryMetric.averageCommittedMemoryGB + "GB"}</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MemoryMetric;
