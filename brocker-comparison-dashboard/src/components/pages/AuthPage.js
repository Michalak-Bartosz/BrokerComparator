import React from "react";
import LoginForm from "../auth/LoginForm";
import RegisterForm from "../auth/RegisterForm";
import SwitchLoginRegister from "../auth/SwitchLoginRegister";
import { signal } from "@preact/signals-react";

export const status = signal(true);
function AuthPage() {
  return (
    <div className="block m-24">
      <SwitchLoginRegister status={status} />
      {status.value ? <LoginForm /> : <RegisterForm />}
    </div>
  );
}

export default AuthPage;
