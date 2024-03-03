import React from "react";
import { FaDotCircle, FaSquare } from "react-icons/fa";

function TimeMetric({ timeMetric, brokerInfoDataList }) {
  const getConstatnsForBrokerTypes = () => {
    return (
      <div>
        {brokerInfoDataList && (
          <div className="grid grid-row-2 gap-4 mt-4">
            {brokerInfoDataList.map((brokerInfoData) => {
              return (
                <div key={brokerInfoData.brokerType} className="block">
                  <div className="flex items-center text-xl font-bold text-blue-800 text-left">
                    <FaSquare className="mr-2 text-xl" />
                    <span>{"[" + brokerInfoData.brokerType + "]:"}</span>
                  </div>
                  <div className="grid grid-cols-2 mt-1">
                    <div className="flex items-center">
                      <FaDotCircle className="text-sm mr-2" />
                      <span className="font-bold text-xl">
                        Produced Time:&nbsp;
                      </span>
                      <span>
                        {
                          brokerInfoData?.reportTimeMetric
                            ?.formattedProducedTime
                        }
                      </span>
                    </div>
                    <div className="flex items-center">
                      <FaDotCircle className="text-sm mr-2" />
                      <span className="font-bold text-xl">
                        Consumed Time:&nbsp;
                      </span>
                      <span>
                        {
                          brokerInfoData?.reportTimeMetric
                            ?.formattedConsumedTime
                        }
                      </span>
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

  const getDeltaTimeForBrokerTypes = () => {
    return (
      <div>
        {brokerInfoDataList && (
          <div className="grid grid-row-2 gap-4 mt-4">
            {brokerInfoDataList.map((brokerInfoData) => {
              return (
                <div key={brokerInfoData.brokerType} className="block">
                  <div className="flex items-center text-xl font-bold text-blue-800 text-left">
                    <FaSquare className="mr-2 text-xl" />
                    <span>{"[" + brokerInfoData.brokerType + "]:"}</span>
                  </div>
                  <div className="grid grid-cols-3 gap-4">
                    <div className="flex items-center">
                      <FaDotCircle className="text-sm mr-2" />
                      <span className="font-bold">
                        {"Min delta time:"}&nbsp;
                      </span>
                      <span>
                        {
                          brokerInfoData?.reportTimeMetric
                            ?.formattedMinDeltaTime
                        }
                      </span>
                    </div>
                    <div className="flex items-center">
                      <FaDotCircle className="text-sm mr-2" />
                      <span className="font-bold">
                        {"Average delta time:"}&nbsp;
                      </span>
                      <span>
                        {
                          brokerInfoData?.reportTimeMetric
                            ?.formattedAverageDeltaTime
                        }
                      </span>
                    </div>
                    <div className="flex items-center">
                      <FaDotCircle className="text-sm mr-2" />
                      <span className="font-bold">
                        {"Max delta time:"}&nbsp;
                      </span>
                      <span>
                        {
                          brokerInfoData?.reportTimeMetric
                            ?.formattedMaxDeltaTime
                        }
                      </span>
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
    <div>
      <div id="memory-metric-wrapper" className="block text-center">
        <div className="text-center text-3xl font-bold border-y-2 border-slate-600 rounded-md pl-6 py-2 w-full my-6">
          <h2 className="text-blue-800 text-2xl text-left font-bold my-1">
            Constants
          </h2>
        </div>
        <div>
          <div className="grid grid-cols-2">
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"All Produced Time:"}&nbsp;</span>
              <span>{timeMetric?.formattedProducedTime}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">{"All Consumed Time:"}&nbsp;</span>
              <span>{timeMetric?.formattedConsumedTime}</span>
            </div>
          </div>
          {getConstatnsForBrokerTypes()}
        </div>
        <div id="delta-time-metrics" className="block">
          <div className="text-center text-3xl font-bold border-y-2 border-slate-600 rounded-md pl-6 py-2 w-full my-6">
            <h2 className="my-1 text-blue-800 text-2xl font-bold">
              Delta Time Metrics
            </h2>
          </div>
          <div>
            <div className="grid grid-cols-3 gap-4">
              <div className="flex items-center">
                <FaDotCircle className="text-sm mr-2" />
                <span className="font-bold">{"Min delta time:"}&nbsp;</span>
                <span>{timeMetric?.formattedMinDeltaTime}</span>
              </div>
              <div className="flex items-center">
                <FaDotCircle className="text-sm mr-2" />
                <span className="font-bold">{"Average delta time:"}&nbsp;</span>
                <span>{timeMetric?.formattedAverageDeltaTime}</span>
              </div>
              <div className="flex items-center">
                <FaDotCircle className="text-sm mr-2" />
                <span className="font-bold">{"Max delta time:"}&nbsp;</span>
                <span>{timeMetric?.formattedMaxDeltaTime}</span>
              </div>
            </div>
            {getDeltaTimeForBrokerTypes()}
          </div>
        </div>
      </div>
    </div>
  );
}

export default TimeMetric;
