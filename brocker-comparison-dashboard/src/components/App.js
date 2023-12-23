import { Navigate, Outlet, Route, Routes } from "react-router-dom";
import MainDashboardPage from "./pages/MainDashboardPage";
import Navbar from "./Navbar";
import Footer from "./Footer";
import AuthPage from "./pages/AuthPage";
import { useSelector } from "react-redux";

function App() {
  const accessToken = useSelector((state) => state.token.accessToken);
  function PrivateRoute() {
    return accessToken === null ? <Navigate to="/auth" replace /> : <Outlet />;
  }

  function AnonymousRoute() {
    return accessToken === null ? <Outlet /> : <Navigate to="/" replace />;
  }

  return (
    <div
      id="body-wrapper"
      className="absolute m-auto min-h-full w-full 
      bg-slate-700  
      font-openSans
      text-white"
    >
      <Navbar />
      <div
        id="page-content-wrapper"
        className="min-h-full min-w-full mt-32 mb-16 p-8"
      >
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
