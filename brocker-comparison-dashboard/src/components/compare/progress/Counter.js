import React from "react";

function Counter(props) {
  return (
    <div className="flex p-4 rounded-md bg-slate-700 text-white w-fit text-2xl shadow bg-opacity-50 m-auto ml-0">
      <h1>{props.name}:&nbsp;</h1>
      <div>
        <h1>{props.value}</h1>
      </div>
    </div>
  );
}

export default Counter;
