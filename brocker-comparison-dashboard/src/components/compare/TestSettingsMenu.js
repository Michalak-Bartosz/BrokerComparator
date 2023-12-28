import { Label, Select, TextInput } from "flowbite-react";
import React, { useEffect } from "react";
import {
  ALL_BROKERS,
  KAFKA_BROKER,
  RABBITMQ_BROKER,
} from "./constants/brokerType";
import { IoMdInformationCircle } from "react-icons/io";
import { FaDotCircle } from "react-icons/fa";
import { Tooltip } from "react-tooltip";

function TestSettingsMenu(props) {
  const selectBrokerOptions = [KAFKA_BROKER, RABBITMQ_BROKER, ALL_BROKERS];
  const numberOfMessagesToSendTooltip =
    "You can send from 1 do 2000 messages in one test.";
  const numberOfAttemptsTooltip =
    "You can set from 1 do 50 attempts in one test.";
  const delayInMillisecondsTooltip =
    "You can set from 0 do 60 000 milliseconds of delay between attempts in test.";
  const startTestTooltip =
    "You can not start test without messages to send or zero attempts in test.";

  const numberInputOnWheelPreventChange = (e) => {
    e.target.blur();
    e.stopPropagation();
    setTimeout(() => {
      e.target.focus();
    }, 0);
  };

  useEffect(() => {
    const listener = (event) => {
      if (event.code === "Enter" || event.code === "NumpadEnter") {
        event.preventDefault();
        props.performTest();
      }
    };
    document.addEventListener("keydown", listener);
    return () => {
      document.removeEventListener("keydown", listener);
    };
  }, [props]);

  return (
    <div
      id="test-settings-menu"
      className="px-10 py-8 bg-slate-950 bg-opacity-40 rounded-lg shadow-lg min-h-[500px] w-full"
    >
      <div className="mb-6 border-y-4 border-slate-600 rounded-md py-4">
        <h1 className="text-4xl text-center text-blue-500 font-bold drop-shadow-[0_1.2px_1.2px_rgba(37,99,235,1)]">
          Test Settings
        </h1>
      </div>
      <div className="flex flex-col mx-6">
        <div className="flex items-center pb-2">
          <FaDotCircle className="text-blue-500 mr-2" />
          <div>
            <Label
              htmlFor="number-messages-to-send-input"
              value="Number of messages to send in test:&nbsp;"
              className="text-2xl text-white mr-4"
            />
          </div>
          <div className="flex m-auto mr-0">
            <TextInput
              id="number-messages-to-send-input"
              className="rounded-md text-2xl w-20"
              type="number"
              step="1"
              min="1"
              max="2000"
              onWheel={numberInputOnWheelPreventChange}
              defaultValue={props.numberOfMessagesToSend}
              onChange={(e) => props.setNumberOfMessagesToSend(e.target.value)}
            />
            <Tooltip
              id="number-of-messages-to-send-tooltip"
              className="text-2xl"
            />
            <IoMdInformationCircle
              className="m-auto ml-3 text-4xl text-blue-500"
              data-tooltip-id="number-of-messages-to-send-tooltip"
              data-tooltip-html={numberOfMessagesToSendTooltip}
              data-tooltip-place="right"
            />
          </div>
        </div>
        <div className="flex items-center py-2">
          <FaDotCircle className="text-blue-500 mr-2" />
          <div>
            <Label
              htmlFor="number-of-attempts-input"
              value="Number of attempts in test:&nbsp;"
              className="text-2xl text-white mr-4"
            />
          </div>
          <div className="flex m-auto mr-0">
            <TextInput
              id="number-of-attempts-input"
              className="rounded-md text-2xl w-20"
              type="number"
              step="1"
              min="1"
              max="50"
              onWheel={numberInputOnWheelPreventChange}
              defaultValue={props.numberOfAttempts}
              onChange={(e) => props.setNumberOfAttempts(e.target.value)}
            />
            <Tooltip id="number-of-attempts-tooltip" className="text-2xl" />
            <IoMdInformationCircle
              className="m-auto ml-3 text-4xl text-blue-500"
              data-tooltip-id="number-of-attempts-tooltip"
              data-tooltip-html={numberOfAttemptsTooltip}
              data-tooltip-place="right"
            />
          </div>
        </div>
        <div className="flex items-center py-2">
          <FaDotCircle className="text-blue-500 mr-2" />
          <div>
            <Label
              htmlFor="delay-in-milliseconds-input"
              value="Delay between attempts [ms]:&nbsp;"
              className="text-2xl text-white mr-4"
            />
          </div>
          <div className="flex m-auto mr-0">
            <TextInput
              id="delay-in-milliseconds-input"
              className="rounded-md text-2xl w-20"
              type="number"
              step="1"
              min="0"
              max="60 000"
              onWheel={numberInputOnWheelPreventChange}
              defaultValue={props.delayInMilliseconds}
              onChange={(e) => props.setDelayInMilliseconds(e.target.value)}
            />
            <Tooltip id="delay-in-milliseconds-tooltip" className="text-2xl" />
            <IoMdInformationCircle
              className="m-auto ml-3 text-4xl text-blue-500"
              data-tooltip-id="delay-in-milliseconds-tooltip"
              data-tooltip-html={delayInMillisecondsTooltip}
              data-tooltip-place="right"
            />
          </div>
        </div>
        <div className="flex items-center pt-2">
          <FaDotCircle className="text-blue-500 mr-2" />
          <div>
            <Label
              htmlFor="broker-select"
              value="Select in which broker perform test:&nbsp;"
              className="text-2xl text-white mr-4"
            />
          </div>
          <div className="flex m-auto mr-0">
            <Select
              id="broker-select"
              className="w-32"
              onChange={(e) =>
                props.setBrokerTypes(JSON.parse(e.target.value).value)
              }
              required
            >
              {selectBrokerOptions.map((option) => {
                return (
                  <option key={option.value} value={JSON.stringify(option)}>
                    {option.label}
                  </option>
                );
              })}
            </Select>
          </div>
        </div>
      </div>
      <div className="h-0 rounded-lg border-2 border-slate-600 my-8" />
      {(!props.numberOfMessagesToSend || !props.numberOfAttempts) && (
        <Tooltip id="start-test-tooltip" className="text-2xl" />
      )}
      <button
        id="start-test-button"
        data-tooltip-id="start-test-tooltip"
        data-tooltip-html={startTestTooltip}
        data-tooltip-place="bottom"
        className="flex m-auto px-4 py-2 outline outline-offset-2 outline-green-700 rounded-md bg-green-800 hover:bg-green-600 disabled:bg-slate-400 disabled:outline-slate-500 text-white font-bold text-2xl"
        disabled={
          !props.numberOfMessagesToSend ||
          !props.numberOfAttempts ||
          props.testInProgressProducer ||
          props.testInProgressConsumer
        }
        onClick={() => props.performTest()}
      >
        Start test
      </button>
    </div>
  );
}

export default TestSettingsMenu;
