import React from "react";
import { Progress } from "flowbite-react";

function ProgressBar(props) {
  return (
    <div className="grid gap-2 font-bold text-2xl px-8 py-6">
      <div className="flex m-auto">
        <h1>TEST PROGRESS:&nbsp;</h1>
        <h1 className="text-green-800">{props.testStatus}%</h1>
      </div>
      <Progress progress={props.testStatus} size="lg" color="green" />
    </div>
  );
}

export default ProgressBar;
