import React from "react";
import { useNavigate } from "react-router-dom";
import { signal } from "@preact/signals-react";
import useApi from "../../connections/api/useApi";

function LogInForm() {
  const navigate = useNavigate();
  const api = useApi();
  const username = signal(null);
  const password = signal(null);
  const error = signal(null);

  async function logInUser() {
    try {
      let loginForm = {
        username: username.value,
        password: password.value,
      };
      await api.logInUser(loginForm).then(() => navigate("/"));
    } catch (e) {
      error.value = e.status;
    }
  }

  return (
    <div className="transition-all block pb-8 pt-6 px-16 w-96 text-center mx-auto mb-12 bg-slate-500 bg-opacity-30 rounded-b-md shadow-md">
      <h1 className="text-6xl font-bold text-blue-500 mb-8">LogIn!</h1>
      <div id="login-user-form" className="grid w-min m-auto gap-6 text-3xl">
        <label id="username">Username</label>
        <input
          id="username-input"
          className="px-4 py-2 rounded-md"
          type="text"
          placeholder="Provide username..."
          onChange={(e) => (username.value = e.target.value)}
        />
        <label id="username">Password</label>
        <input
          id="password-input"
          className="px-4 py-2 rounded-md"
          type="password"
          placeholder="Provide password..."
          onChange={(e) => (password.value = e.target.value)}
        />
        <button
          className="m-auto my-2 bg-slate-100 px-4 py-2 rounded-md text-2xl outline outline-offset-2 outline-blue-500 font-bold hover:bg-blue-300"
          onClick={logInUser}
        >
          LogIn
        </button>
      </div>
    </div>
  );
}

export default LogInForm;
