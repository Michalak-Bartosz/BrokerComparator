import React from "react";
import { Progress } from "flowbite-react";

function UsageBar(props) {
  return (
    <div className="grid grid-cols-2 w-full items-center font-bold text-2xl px-8 py-2">
      <div className="flex">
        <h1>{props.usageLabel}:&nbsp;</h1>
        <h1 className="text-blue-500">{props.usageStatus}&nbsp;[GB]</h1>
      </div>
      <Progress
        className="w-full"
        progress={props.usageStatus}
        size="xl"
        color="blue"
      />
    </div>
  );
}

export default UsageBar;
