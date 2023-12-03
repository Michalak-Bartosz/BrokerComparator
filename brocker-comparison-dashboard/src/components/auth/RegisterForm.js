import React from "react";
import useApi, { user } from "../../connections/api/useApi";
import { signal } from "@preact/signals-react";
import { useNavigate } from "react-router-dom";

function RegisterForm() {
  const navigate = useNavigate();
  const api = useApi();
  const username = signal(null);
  const password = signal(null);
  const error = signal(null);

  async function registerUser() {
    try {
      let registerUserDto = {
        username: username,
        password: password,
        role: "USER",
      };
      await api.registerUser(registerUserDto).then((result) => {
        error.value = null;
        user.value = result;
        navigate("/");
      });
    } catch (error) {
      error.value = error.status;
    }
  }

  return (
    <div className="transition-all block pb-8 pt-6 px-16 w-96 text-center mx-auto mb-12 bg-slate-500 bg-opacity-30 rounded-b-md shadow-md">
      <h1 className="text-6xl font-bold text-blue-500 mb-8">Register!</h1>
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
          onClick={registerUser}
        >
          Register
        </button>
      </div>
    </div>
  );
}

export default RegisterForm;
