import React from "react";
import { IoLogInOutline } from "react-icons/io5";
import { PiTrademarkRegisteredFill } from "react-icons/pi";

function SwitchLoginRegister(props) {
  const handleCheckboxChange = () => {
    props.status.value = !props.status.value;
  };

  return (
    <label
      id="switch-button"
      className="flex m-auto cursor-pointer w-96 items-center rounded-t-md m-auto"
    >
      <input
        id="input-switch"
        type="checkbox"
        checked={props.status.value}
        onChange={handleCheckboxChange}
        className="sr-only"
      />
      <div className="flex m-auto text-2xl h-max w-full items-center justify-center rounded-t-md">
        <span
          className={`flex w-full items-center justify-center rounded-t-md ${
            !props.status.value
              ? "bg-white text-slate-300"
              : "bg-slate-500 bg-opacity-30"
          }`}
        >
          <div className="flex p-2 items-center">
            LogIn
            <IoLogInOutline className="ml-2" />
          </div>
        </span>
        <span
          className={`flex w-full items-center justify-center rounded-t-md ${
            props.status.value
              ? "bg-white text-slate-300"
              : "bg-slate-500 bg-opacity-30"
          }`}
        >
          <div className="flex p-2 items-center">
            Register
            <PiTrademarkRegisteredFill className="ml-2" />
          </div>
        </span>
      </div>
    </label>
  );
}
export default SwitchLoginRegister;
