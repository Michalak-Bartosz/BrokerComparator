import React from "react";
import { Progress } from "flowbite-react";

function ProgressBar(props) {
  return (
    <div className="grid gap-2 font-bold text-2xl p-8">
      <div className="flex m-auto">
        <h1>TEST PROGRESS:&nbsp;</h1>
        <h1 className="text-green-500">{props.testStatus}%</h1>
      </div>
      <Progress progress={props.testStatus} size="lg" />
    </div>
  );
}

export default ProgressBar;
