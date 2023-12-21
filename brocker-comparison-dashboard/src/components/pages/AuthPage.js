import React from "react";
import LogInForm from "../auth/LoginForm";
import RegisterForm from "../auth/RegisterForm";
import SwitchLoginRegister, { authMode } from "../auth/SwitchLoginRegister";
import { useNavigate } from "react-router-dom";
import useApi from "../../connections/api/useApi";
import ErrorMessage, { authErrorMessage } from "../auth/ErrorMessage";

function AuthPage() {
  const navigate = useNavigate();
  const api = useApi();

  async function logInUser(username, password) {
    try {
      let loginForm = {
        username: username,
        password: password,
      };
      await api.logInUser(loginForm).then(() => {
        navigate("/");
      });
    } catch (e) {
      if (e.status === 403) {
        authErrorMessage.value = "Bad user credentials!";
      }
    }
  }

  async function registerUser(username, password) {
    try {
      let registerForm = {
        username: username,
        password: password,
        role: "USER",
      };
      await api.registerUser(registerForm).then((response) => {
        navigate("/");
      });
    } catch (e) {
      if (e.status === 403) {
        authErrorMessage.value = "Username already exists!";
      }
    }
  }

  return (
    <div className="block mx-auto my-12 w-fit rounded-md">
      <SwitchLoginRegister />
      {authMode.value ? (
        <LogInForm logInUser={logInUser} />
      ) : (
        <RegisterForm registerUser={registerUser} />
      )}
      <ErrorMessage />
    </div>
  );
}

export default AuthPage;
