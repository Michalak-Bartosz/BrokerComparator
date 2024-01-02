import { FaDotCircle } from "react-icons/fa";
import React from "react";

function Address({ address }) {
  return (
    <div className="text-xl m-auto">
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">UUID:</span>
        <span>{address.uuid}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">User UUID:</span>
        <span>{address.userUuid}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">Street Name:</span>
        <span>{address.streetName}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">Number:</span>
        <span>{address.number}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">City:</span>
        <span>{address.city}</span>
      </div>
      <div className="flex items-center">
        <FaDotCircle className="text-blue-500 text-sm mr-1" />
        <span className="mr-1 font-bold">Country:</span>
        <span>{address.country}</span>
      </div>
    </div>
  );
}

export default Address;
