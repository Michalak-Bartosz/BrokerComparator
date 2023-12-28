import React from "react";
import { TbListNumbers } from "react-icons/tb";
import { MdAccessTimeFilled } from "react-icons/md";

function SwitchChartMode(props) {
  return (
    <div className="flex w-fit bg-white bg-opacity-60 mx-auto mt-0 rounded-b-lg border-b-2 border-x-2 border-slate-600">
      <h1 className="text-black font-bold px-4 py-2">Switch chart mode</h1>
      <label
        id={(Math.random() + 1).toString(36).substring(7)}
        className="flex cursor-pointer w-96 items-center rounded-b-md"
      >
        <input
          id={(Math.random() + 1).toString(36).substring(7)}
          type="checkbox"
          checked={props.isTimestampMode}
          onChange={() => props.setIsTimestampMode((prevMode) => !prevMode)}
          className="sr-only"
        />
        <div className="flex m-auto h-max w-full items-center justify-center">
          <span
            className={`flex w-full items-center justify-center ${
              !props.isTimestampMode
                ? "bg-green-950 bg-opacity-70"
                : "bg-slate-400 bg-opacity-60 text-slate-500"
            }`}
          >
            <div className="flex p-2 items-center">
              Message number
              <TbListNumbers className="ml-2" />
            </div>
          </span>
          <span
            className={`flex w-full items-center justify-center rounded-br-md ${
              props.isTimestampMode
                ? "bg-green-950 bg-opacity-70"
                : "bg-slate-400 bg-opacity-60 text-slate-500"
            }`}
          >
            <div className="flex p-2 items-center">
              Timestamp
              <MdAccessTimeFilled className="ml-2" />
            </div>
          </span>
        </div>
      </label>
    </div>
  );
}
export default SwitchChartMode;
