import { useDispatch } from "react-redux";
import useHttpApi from "./useHttpApi";
import {
  addTokenAction,
  removeTokenAction,
} from "../../components/redux/actions/tokenActions";

const useApi = () => {
  const httpApi = useHttpApi();
  const dispatch = useDispatch();

  async function registerUser(registerForm) {
    try {
      const response = await httpApi.post("/auth/register", registerForm);
      if (response?.accessToken) {
        dispatch(addTokenAction(response));
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
      }
      return response;
    } catch (error) {
      throw error;
    }
  }

  async function logOutUser() {
    dispatch(removeTokenAction());
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
  };
};

export default useApi;
