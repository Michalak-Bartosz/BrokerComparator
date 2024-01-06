import React from "react";
import { FaDotCircle, FaSquare } from "react-icons/fa";

function DataSizeMetric({ dataSizeMetric, brokerInfoDataList }) {
  const getProducedDataSizePerSecondsForBrokerTypes = () => {
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
                  <div className="grid grid-cols-2 gap-x-4">
                    <div className="flex items-center">
                      <FaDotCircle className="text-sm mr-2" />
                      <span className="font-bold">
                        {"Produced Data Size per Seconds:"}&nbsp;
                      </span>
                      <span>
                        {brokerInfoData?.reportDataSizeMetric
                          ?.formattedProducedDataSizePerSecond + "/s"}
                      </span>
                    </div>
                    <div className="flex items-center">
                      <FaDotCircle className="text-sm mr-2" />
                      <span className="font-bold">
                        {"Consumed Data Size per Seconds:"}&nbsp;
                      </span>
                      <span>
                        {brokerInfoData?.reportDataSizeMetric
                          ?.formattedConsumedDataSizePerSecond + "/s"}
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
    <div id="memory-metric-wrapper" className="block text-center">
      <div id="payload-size-metrics" className="block">
        <div className="text-center text-3xl font-bold border-y-2 border-slate-600 rounded-md pl-6 py-2 w-full my-6">
          <h2 className="my-1 text-blue-800 text-2xl font-bold">
            Payload Size Metrics
          </h2>
        </div>
        <div className="grid grid-cols-3 gap-4">
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Max Payload Size:"}&nbsp;</span>
            <span>{dataSizeMetric?.formattedMaxPayloadSize}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Min Payload Size:"}&nbsp;</span>
            <span>{dataSizeMetric?.formattedMinPayloadSize}</span>
          </div>
          <div className="flex items-center">
            <FaDotCircle className="text-sm mr-2" />
            <span className="font-bold">{"Average Payload Size:"}&nbsp;</span>
            <span>{dataSizeMetric?.formattedAveragePayloadSize}</span>
          </div>
        </div>
      </div>
      <div id="total-data-size-metrics" className="block">
        <div className="text-center text-3xl font-bold border-y-2 border-slate-600 rounded-md pl-6 py-2 w-full my-6">
          <h2 className="my-1 text-blue-800 text-2xl font-bold">
            Total Data Size Metrics
          </h2>
        </div>
        <div>
          <div className="grid grid-cols-2 gap-x-4">
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">
                {"Total Produced Data Size:"}&nbsp;
              </span>
              <span>{dataSizeMetric?.formattedTotalProducedDataSize}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">
                {"Total Consumed Data Size:"}&nbsp;
              </span>
              <span>{dataSizeMetric?.formattedTotalConsumedDataSize}</span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">
                {"Produced Data Size per Seconds:"}&nbsp;
              </span>
              <span>
                {dataSizeMetric?.formattedProducedDataSizePerSecond + "/s"}
              </span>
            </div>
            <div className="flex items-center">
              <FaDotCircle className="text-sm mr-2" />
              <span className="font-bold">
                {"Consumed Data Size per Seconds:"}&nbsp;
              </span>
              <span>
                {dataSizeMetric?.formattedConsumedDataSizePerSecond + "/s"}
              </span>
            </div>
          </div>
          {getProducedDataSizePerSecondsForBrokerTypes()}
        </div>
      </div>
    </div>
  );
}

export default DataSizeMetric;
