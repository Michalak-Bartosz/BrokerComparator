import { useDispatch } from "react-redux";
import useHttpApi from "./useHttpApi";
import {
  addTokenAction,
  removeTokenAction,
} from "../../redux/actions/tokenActions";
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
        dispatch(addTokenAction(response));
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
        dispatch(addTokenAction(response));
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
      dispatch(removeTokenAction());
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

  // async function getMaze(mazeId) {
  //     try {
  //         const maze = await httpApi.get(`${mazeId}`);
  //         return maze;
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function getMazeCells(mazeId) {
  //     try {
  //         const mazeCells = await httpApi.get(`${mazeId}/cells`);
  //         return mazeCells;
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function getSolveMazeCells(mazeId, solveId) {
  //     try {
  //         const mazeCells = await httpApi.get(`${mazeId}/${solveId}`);
  //         return mazeCells;
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function getMazes() {
  //     try {
  //         const mazes = await httpApi.get("mazes");
  //         return mazes;
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function getSolutions(mazeId) {
  //     try {
  //         const solutions = await httpApi.get(`${mazeId}/solve`);
  //         return solutions
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function getSolution(solveId) {
  //     try {
  //         const solution = await httpApi.get(`solve/${solveId}`);
  //         return solution
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function createMaze(mazeBody) {
  //     try {
  //         const maze = await httpApi.post("create", mazeBody);
  //         return maze;
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function solveMaze(solveParams) {
  //     try {
  //         const solveMaze = await httpApi.post("solve", solveParams);
  //         return solveMaze;
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function deleteMaze(mazeId) {
  //     try {
  //         await httpApi.remove(`maze/${mazeId}`);
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

  // async function deleteSolve(solveId) {
  //     try {
  //         await httpApi.remove(`solve/${solveId}`);
  //     } catch (error) {
  //         handleErrors(error);
  //     }
  // }

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
