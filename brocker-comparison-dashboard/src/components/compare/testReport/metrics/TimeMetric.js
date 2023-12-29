import React from "react";
import { FaDotCircle } from "react-icons/fa";
import { getDurationInMilliseconds } from "../../../util/DateTimeUtil";

function TimeMetric({ timeMetric }) {
  return (
    <div id="memory-metric-wrapper" className="block text-center">
      <h2 className="mb-2 text-blue-500 text-2xl text-left font-bold pt-4">
        Constants
      </h2>
      <div className="grid grid-cols-2 gap-6">
        <div className="flex items-center">
          <FaDotCircle className="text-sm mr-2" />
          <span className="font-bold">{"All Produced Time:"}&nbsp;</span>
          <span>
            {getDurationInMilliseconds(timeMetric.producedTime) + "ms"}
          </span>
        </div>
        <div className="flex items-center">
          <FaDotCircle className="text-sm mr-2" />
          <span className="font-bold">{"All Consumed Time:"}&nbsp;</span>
          <span>
            {getDurationInMilliseconds(timeMetric.consumedTime) + "ms"}
          </span>
        </div>
      </div>
      <div id="delta-time-metrics" className="block">
        <h2 className="my-2 text-blue-500 text-2xl font-bold">
          Delta Time Metrics
        </h2>
        <div className="block m-auto w-max">
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Min delta time:"}&nbsp;</span>
            <span>
              {getDurationInMilliseconds(timeMetric.minDeltaTime) + "ms"}
            </span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Max delta time:"}&nbsp;</span>
            <span>
              {getDurationInMilliseconds(timeMetric.maxDeltaTime) + "ms"}
            </span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Average delta time:"}&nbsp;</span>
            <span>
              {getDurationInMilliseconds(timeMetric.averageDeltaTime) + "ms"}
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default TimeMetric;
