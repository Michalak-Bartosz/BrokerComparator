import { Navigate, Outlet, Route, Routes } from "react-router-dom";
import MainDashboardPage from "./pages/MainDashboardPage";
import Navbar from "./Navbar";
import Footer from "./Footer";
import AuthPage from "./pages/AuthPage";
import { user } from "../connections/api/useTokenService";

function App() {
  function PrivateRoute() {
    return user.value === undefined ? <Navigate to="/auth" replace /> : <Outlet />;
  }

  function AnonymousRoute() {
    return user.value === undefined ? <Outlet /> : <Navigate to="/" replace />;
  }

  return (
    <div
      id="body-wrapper"
      className="fixed m-auto min-h-full min-w-full bg-gradient-to-t from-slate-300 to-white font-openSans"
    >
      <Navbar />
      <div id="page-content-wrapper" className="mt-32 p-8">
        <Routes>
          <Route element={<PrivateRoute />}>
            <Route path="/" element={<MainDashboardPage />} />
          </Route>
          <Route element={<AnonymousRoute />}>
            <Route path="/auth" element={<AuthPage />} />
          </Route>
        </Routes>
      </div>
      <Footer />
    </div>
  );
}

export default App;
