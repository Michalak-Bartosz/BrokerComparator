import { useDispatch } from "react-redux";
import useHttpApi from "./useHttpApi";
import {
  addUserAction,
  removeUserAction,
} from "../../redux/actions/userActions";
import { clearAllForcusedReportsAction } from "../../redux/actions/testReportActions";

const useApi = () => {
  const httpApi = useHttpApi();
  const dispatch = useDispatch();

  async function registerUser(registerForm) {
    try {
      const response = await httpApi.post("/auth/register", registerForm);
      if (response?.accessToken) {
        dispatch(addUserAction(registerForm.username));
      }
      return response;
    } catch (error) {
      throw error;
    }
  }

  async function logInUser(logInForm) {
    try {
      const response = await httpApi.post("/auth/login", logInForm);

      if (response?.accessToken) {
        dispatch(addUserAction(logInForm.username));
      }
      return response;
    } catch (error) {
      throw error;
    }
  }

  async function logOutUser() {
    try {
      await httpApi.post("/auth/logout", null, false);
      dispatch(removeUserAction());
      dispatch(clearAllForcusedReportsAction());
      localStorage.clear();
    } catch (error) {
      throw error;
    }
  }

  async function performTest(testSettings) {
    try {
      const response = await httpApi.post("/test/perform", testSettings);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async function finishTest(testSettings) {
    try {
      const response = await httpApi.post("/test/finish", testSettings);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async function getTestReport(testUUID) {
    try {
      const response = await httpApi.get(`/test/${testUUID}/report`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  async function getTestReports() {
    try {
      const response = await httpApi.get(`/test/report`);
      return response;
    } catch (error) {
      throw error;
    }
  }

  return {
    registerUser,
    logInUser,
    logOutUser,
    performTest,
    finishTest,
    getTestReport,
    getTestReports,
  };
};

export default useApi;
