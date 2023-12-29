import React, { useCallback, useEffect, useState } from "react";
import { IoMdInformationCircle } from "react-icons/io";
import { Tooltip } from "react-tooltip";
import { authErrorMessage } from "./ErrorMessage";

function RegisterForm(props) {
  const [username, setUserName] = useState(null);
  const [password, setPassword] = useState(null);
  const [isPasswordValid, setIsPasswordValid] = useState(true);

  const passwordValidationRules = `<h1>Password rules:</h1>
    <ul>
      <li>=> At least one capital char.</li>
      <li>=> The char length between 7 to 40.</li>
      <li>=> At least one symbol.</li>
      <li>=> At least one number.</li>
    </ul>`;

  const passwordValidate = (password) => {
    if (password) {
      var re = {
        capital: /(?=.*[A-Z])/,
        length: /(?=.{7,40}$)/,
        specialChar: /[ -/:-@[-`{-~]/,
        digit: /(?=.*[0-9])/,
      };
      return (
        re.capital.test(password) &&
        re.length.test(password) &&
        re.specialChar.test(password) &&
        re.digit.test(password)
      );
    }
    return false;
  };

  const submit = useCallback(() => {
    if (passwordValidate(password)) {
      props.registerUser(username, password);
      setIsPasswordValid(true);
      authErrorMessage.value = null;
      return;
    }
    setIsPasswordValid(false);
    authErrorMessage.value = "Invalid password!";
  }, [password, props, username]);

  useEffect(() => {
    const listener = (event) => {
      if (event.code === "Enter" || event.code === "NumpadEnter") {
        event.preventDefault();
        submit();
      }
    };
    document.addEventListener("keydown", listener);
    return () => {
      document.removeEventListener("keydown", listener);
    };
  }, [submit]);

  return (
    <div className="transition-all block pb-8 pt-6 px-8 w-96 text-left mx-auto bg-slate-800 bg-opacity-80 rounded-b-md shadow-md">
      <h1 className="text-6xl font-bold text-blue-500 mb-8 text-center drop-shadow-[0_1.2px_1.2px_rgba(37,99,235,1)]">
        Register!
      </h1>
      <div id="login-user-form" className="grid w-min m-auto gap-6 text-3xl">
        <label
          id={(Math.random() + 1).toString(36).substring(7)}
          htmlFor="register-username-input"
        >
          Username:
        </label>
        <input
          id="register-username-input"
          className="px-4 py-2 rounded-md w-64 text-black"
          type="text"
          placeholder="Provide username..."
          onChange={(e) => setUserName(e.target.value)}
        />
        <div className="flex m-0 p-0">
          <label
            id={(Math.random() + 1).toString(36).substring(7)}
            htmlFor="register-password-input"
          >
            Password:
          </label>
          <Tooltip id="filter-tooltip" className="text-2xl" />
          {isPasswordValid === false ? (
            <IoMdInformationCircle
              className="m-auto ml-3 text-4xl text-amber-600"
              data-tooltip-id="filter-tooltip"
              data-tooltip-html={passwordValidationRules}
              data-tooltip-place="top"
            />
          ) : (
            <></>
          )}
        </div>
        <input
          id="register-password-input"
          className={`px-4 py-2 rounded-md w-64 text-black ${
            isPasswordValid ? "" : "border-4 border-amber-600"
          }`}
          type="password"
          placeholder="Provide password..."
          onChange={(e) => setPassword(e.target.value)}
        />
        <button
          className="m-auto my-2 bg-blue-500 px-4 py-2 rounded-md text-2xl outline outline-offset-2 outline-blue-200 font-bold hover:bg-blue-300 hover:outline-blue-100"
          onClick={submit}
        >
          Register
        </button>
      </div>
    </div>
  );
}

export default RegisterForm;
