import React, { useState } from "react";
import useApi from "../../connections/api/useApi";
import { useNavigate } from "react-router-dom";
import { WsClient, users } from "../../connections/ws/WsClient";

function LoginPage(props) {
  const navigate = useNavigate();
  const api = useApi();
  const [username, setUsername] = useState(null);
  const [password, setPassword] = useState(null);
  const [error, setError] = useState(null);

  async function loginUser() {
    try {
      let user = {
        username: username,
        password: password,
      };
      await api.authenticate(user).then((result) => {
        setError(null);
        console.log(result)
        props.setUser(result);
        navigate("/");
      });
    } catch (error) {
      setError(error.status);
    }
  }

  return (
    <div className="block p-8 text-center mx-auto my-12 bg-slate-500 bg-opacity-30 rounded-md shadow-md w-min">
      <h1 className="text-6xl font-bold text-blue-500 mb-8">Login Page!</h1>
      <div id="login-user-form" className="grid w-min m-auto gap-6 text-3xl">
        <label id="username">Username</label>
        <input
          id="username-input"
          type="text"
          placeholder="Provide username..."
          onChange={(e) => setUsername(e.target.value)}
        />
        <label id="username">Username</label>
        <input
          id="password-input"
          type="password"
          placeholder="Provide password..."
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className="" onClick={loginUser}>
          Login
        </button>
      </div>
      <WsClient />
      {users.value.map((user) => {
        return <div key={user.uuid}>{user.name}</div>;
      })}
      {/* {users.value.map((user) => {
        return <div>user.uuid</div>;
      })} */}
    </div>
  );
}

export default LoginPage;
