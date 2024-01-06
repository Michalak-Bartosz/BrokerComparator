import { Spinner, Toast } from "flowbite-react";
import React from "react";

function GeneratingDataStatusToast({ showToast, setShowToast }) {
  return (
    <div
      id="generating-data-status-Toast-wrapper"
      className={`flex transition-all duration-700 hover:ease-in-out fixed z-50 left-10 ${
        showToast ? "bottom-10" : "-bottom-40"
      }`}
    >
      <Toast className="min-w-max bg-emerald-900 border-2 shadow-2xl px-6">
        <div className="flex items-center bg-emerald-900 py-4 rounded-lg w-max m-auto ">
          <Spinner size="lg" />
          <span className="mx-4 text-2xl text-white font-medium">
            Generating Data...
          </span>
        </div>
      </Toast>
    </div>
  );
}

export default GeneratingDataStatusToast;
