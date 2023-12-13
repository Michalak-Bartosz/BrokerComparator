import { Navigate, Outlet, Route, Routes } from "react-router-dom";
import MainDashboardPage from "./pages/MainDashboardPage";
import Navbar from "./Navbar";
import Footer from "./Footer";
import AuthPage from "./pages/AuthPage";
import { useSelector } from "react-redux";
import { CookiesProvider } from "react-cookie";

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
      className="m-auto min-h-full min-w-full bg-gradient-to-t from-slate-300 to-white font-openSans"
    >
      <Navbar />
      <div id="page-content-wrapper" className="h-full mt-32 p-8">
        <CookiesProvider>
          <Routes>
            <Route element={<PrivateRoute />}>
              <Route path="/" element={<MainDashboardPage />} />
            </Route>

            <Route element={<AnonymousRoute />}>
              <Route path="/auth" element={<AuthPage />} />
            </Route>
          </Routes>
        </CookiesProvider>
      </div>
      <Footer />
    </div>
  );
}

export default App;
