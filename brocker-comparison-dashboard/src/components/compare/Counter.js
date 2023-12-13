import React from "react";

function Counter(props) {
  return (
    <div className="flex p-4 rounded-md m-auto bg-slate-700 text-white w-fit my-4 text-2xl shadow bg-opacity-50">
      <h1>{props.name}:&nbsp;</h1>
      <div>
        <h1>{props.value}</h1>
      </div>
    </div>
  );
}

export default Counter;
