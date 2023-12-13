import React, { useEffect } from "react";
import { signal } from "@preact/signals-react";

function LogInForm(props) {
  const username = signal(null);
  const password = signal(null);
  useEffect(() => {
    const listener = (event) => {
      if (event.code === "Enter" || event.code === "NumpadEnter") {
        console.log("Enter key was pressed. Run your function.");
        event.preventDefault();
        props.logInUser(username.value, password.value);
      }
    };
    document.addEventListener("keydown", listener);
    return () => {
      document.removeEventListener("keydown", listener);
    };
  }, [password.value, props, username.value]);

  return (
    <div className="transition-all block pb-8 pt-6 px-16 w-96 text-left mx-auto bg-slate-500 bg-opacity-30 rounded-b-md shadow-md">
      <h1 className="text-6xl font-bold text-blue-500 mb-8 text-center">
        LogIn!
      </h1>
      <div id="login-user-form" className="grid w-min m-auto gap-6 text-3xl">
        <label id="username">Username:</label>
        <input
          id="username-input"
          className="px-4 py-2 rounded-md w-64"
          type="text"
          placeholder="Provide username..."
          onChange={(e) => (username.value = e.target.value)}
        />
        <label id="username">Password:</label>
        <input
          id="password-input"
          className="px-4 py-2 rounded-md w-64"
          type="password"
          placeholder="Provide password..."
          onChange={(e) => (password.value = e.target.value)}
        />
        <button
          className="m-auto my-2 bg-slate-100 px-4 py-2 rounded-md text-2xl outline outline-offset-2 outline-blue-500 font-bold hover:bg-blue-300"
          onClick={() => props.logInUser(username.value, password.value)}
        >
          LogIn
        </button>
      </div>
    </div>
  );
}

export default LogInForm;
