import { Label, Select, TextInput } from "flowbite-react";
import React, { useEffect } from "react";

function TestSettingsMenu(props) {
  const selectBrokerOptions = [
    { value: "KAFKA", label: "Kafka" },
    { value: "RABBITMQ", label: "Rabbit MQ" },
    { value: "ALL", label: "All brokers" },
  ];

  const numberInputOnWheelPreventChange = (e) => {
    // Prevent the input value change
    e.target.blur();

    // Prevent the page/container scrolling
    e.stopPropagation();

    // Refocus immediately, on the next tick (after the current function is done)
    setTimeout(() => {
      e.target.focus();
    }, 0);
  };

  useEffect(() => {
    const listener = (event) => {
      if (event.code === "Enter" || event.code === "NumpadEnter") {
        console.log("Enter key was pressed. Run your function.");
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
      className="p-6 bg-slate-400 bg-opacity-20 rounded-lg"
    >
      <h1 className="text-5xl text-center mb-6 text-blue-500 font-bold border-y-4 border-slate-600 rounded-md py-4">
        Test Settings
      </h1>
      <div className="grid grid-cols-2 gap-6 mb-6">
        <div className="flex items-center m-auto">
          <div>
            <Label
              htmlFor="number-messages-to-send-input"
              value="Number of messages to send in test:&nbsp;"
              className="text-2xl"
            />
          </div>
          <TextInput
            id="number-messages-to-send-input"
            className="rounded-md text-2xl"
            type="number"
            step="1"
            min="0"
            max="10000"
            onWheel={numberInputOnWheelPreventChange}
            defaultValue={props.numberOfMessagesToSend}
            onChange={(e) => props.setNumberOfMessagesToSend(e.target.value)}
          />
        </div>
        <div className="flex items-center m-auto">
          <div>
            <Label
              htmlFor="broker-select"
              value="Select in which broker perform test:&nbsp;"
              className="text-2xl"
            />
          </div>
          <Select
            id="broker-select"
            onChange={(e) => props.setBrokerType(e.target.value)}
            required
          >
            {selectBrokerOptions.map((option) => {
              return (
                <option key={option.value} value={option.value}>
                  {option.label}
                </option>
              );
            })}
          </Select>
        </div>
      </div>

      <button
        className="flex m-auto px-6 py-3 mb-4 outline outline-offset-2 outline-green-700 rounded-md bg-green-800 hover:bg-green-600 text-white font-bold text-2xl"
        onClick={() => props.performTest()}
      >
        Start test
      </button>
    </div>
  );
}

export default TestSettingsMenu;
