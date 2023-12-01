import "./App.css";
import { Navigate, Outlet, Route, Routes } from "react-router-dom";
import MainDashboardPage from "./pages/MainDashboardPage";
import LoginPage from "./pages/LoginPage";
import { useLocalStorage } from "./storage/LocalStorage";
import useApi from "../connections/api/useApi";

function App() {
  const api = useApi();
  const [, setUser] = useLocalStorage("user", null);

  async function authenticateUser(user) {
    try {
      await api.authenticateUser(user).then((result) => {
        return true;
      });
    } catch (error) {
      setUser(null);
      return false;
    }
  }

  function PrivateRoute() {
    const user = JSON.parse(localStorage.getItem("user"));
    return user ? <Outlet /> : <Navigate to="/login" replace />;
  }

  function AnonymousRoute() {
    const user = JSON.parse(localStorage.getItem("user"));
    return user ? <Navigate to="/" replace /> : <Outlet />;
  }

  return (
    <Routes>
      <Route element={<PrivateRoute />}>
        <Route path="/" element={<MainDashboardPage />} />
      </Route>
      <Route element={<AnonymousRoute />}>
        <Route path="/login" element={<LoginPage setUser={setUser} />} />
      </Route>
    </Routes>
  );
}

export default App;
