import React from "react";
import { FaDotCircle } from "react-icons/fa";

function MemoryMetric({ memoryMetric }) {
  return (
    <div className="text-xl m-auto">
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">Initial Memory:</span>
        <span>{memoryMetric.initialMemoryGB + "GB"}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">Used Heap Memory:</span>
        <span>{memoryMetric.usedHeapMemoryGB + "GB"}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">Max Heap Memory:</span>
        <span>{memoryMetric.maxHeapMemoryGB + "GB"}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">Committed Memory:</span>
        <span>{memoryMetric.committedMemoryGB + "GB"}</span>
      </div>
    </div>
  );
}

export default MemoryMetric;
