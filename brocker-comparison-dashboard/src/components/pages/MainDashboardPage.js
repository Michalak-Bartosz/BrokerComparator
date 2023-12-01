import React from "react";
import useApi from "../../connections/api/useApi";

function MainDashboardPage(props) {
  const api = useApi();

  async function refreshToken() {
    try {
      const user = JSON.stringify(localStorage.getItem("user"));
      console.log(user);
      await api.refreshToken(user).then((result) => {
        console.log(result);
      });
    } catch (error) {
      console.log(error);
    }
  }
  return (
    <div>
      <h1 className="text-4xl text-rose-500">Main DashboardPage!</h1>
      <button onClick={refreshToken}>Refresh Token</button>
    </div>
  );
}

export default MainDashboardPage;
