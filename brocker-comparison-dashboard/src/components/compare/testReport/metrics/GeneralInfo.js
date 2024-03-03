import React from "react";
import { FaDotCircle, FaSquare } from "react-icons/fa";

function GeneralInfo({ testReport }) {
  return (
    <div>
      <div className="grid grid-cols-3 gap-4">
        <div className="flex m-auto items-center ml-4 text-2xl my-6">
          <FaDotCircle className="text-blue-800 ml-auto mr-2" />
          <span className="text-blue-800 font-bold mr-2 w-fit">
            Sync:&nbsp;
          </span>
          <span className="mr-auto">{testReport.isSync ? "YES" : "NO"}</span>
        </div>
        <div className="flex m-auto items-center ml-4 text-2xl my-6">
          <FaDotCircle className="text-blue-800 ml-auto mr-2" />
          <span className="text-blue-800 font-bold mr-2 w-fit">
            All Broker Types:&nbsp;
          </span>
          <div className="grid grid-flow-row text-start">
            {testReport.brokerTypeList.sort().map((brokerType) => (
              <span key={brokerType} className="mr-auto">
                {brokerType}
              </span>
            ))}
          </div>
        </div>
        <div className="flex m-auto items-center ml-4 text-2xl my-6">
          <FaDotCircle className="text-blue-800 ml-auto mr-2" />
          <span className="text-blue-800 font-bold mr-2 w-fit">
            Users in test:&nbsp;
          </span>
          <span className="mr-auto">{testReport?.userList?.length}</span>
        </div>
      </div>
      <div className="grid grid-cols-2 gap-4">
        <div className="flex m-auto items-center ml-4 text-2xl my-6">
          <FaDotCircle className="text-blue-800 ml-auto mr-2" />
          <span className="text-blue-800 font-bold mr-2 w-fit">
            Number Of attempts:&nbsp;
          </span>
          <span className="mr-auto">{testReport.numberOfAttempts}</span>
        </div>
        <div className="flex m-auto items-center ml-4 text-2xl my-6">
          <FaDotCircle className="text-blue-800 ml-auto mr-2" />
          <span className="text-blue-800 font-bold mr-2 w-fit">
            Delay between attempts:&nbsp;
          </span>
          <span className="mr-auto">
            {testReport.formattedDelayBetweenAttempts}
          </span>
        </div>
      </div>
      {testReport?.brokerInfoDataList && (
        <div className="grid grid-cols-2 gap-4 border-t-2 pt-6 border-slate-600">
          {testReport?.brokerInfoDataList.map((brokerInfoData) => {
            return (
              <div key={brokerInfoData.brokerType}>
                <div className="flex items-center">
                  <FaSquare className="text-blue-800 mr-2" />
                  <span className="font-bold text-blue-800 text-2xl">
                    Number of users for {brokerInfoData.brokerType}:&nbsp;
                  </span>
                  <span>{brokerInfoData?.userList?.length}</span>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

export default GeneralInfo;
