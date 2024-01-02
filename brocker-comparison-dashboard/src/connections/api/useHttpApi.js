import BASE_URL from "../constants/BASE_URL";
import ApiError from "./errors/ApiError";
import BadRequestError from "./errors/BadRequestError";
import ConflictError from "./errors/ConflictError";
import ForbiddenError from "./errors/ForbiddenError";
import NotFoundError from "./errors/NotFoundError";
import UnauthorizedError from "./errors/UnauthorizedError";

const useHttpApi = () => {
  function getHeaders() {
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    headers.append("Accept", "application/json");
    headers.append("Origin", "http://localhost:3000");
    return headers;
  }

  function getRequest(method, body) {
    return {
      method: method,
      RequestMode: "cors",
      headers: getHeaders(),
      credentials: "include",
      body: body,
    };
  }

  async function get(url) {
    const request = getRequest("GET");

    const response = await fetch(BASE_URL.API_URL + url, request);

    if (response.ok) {
      const jsonOut = await response.json();
      return jsonOut;
    }

    handleErrors(url, request, response);
  }

  async function post(url, body, returnsOutput = true) {
    try {
      const request = getRequest("POST", JSON.stringify(body));

      const response = await fetch(BASE_URL.API_URL + url, request);

      if (response.ok && returnsOutput) {
        const jsonOut = await response.json();
        return jsonOut;
      } else if (response.ok) {
        return;
      }

      handleErrors(url, request, response);
    } catch (error) {
      throw error;
    }
  }

  async function put(url, body, returnsOutput = true) {
    try {
      const request = getRequest("PUT", JSON.stringify(body));

      const response = await fetch(BASE_URL.API_URL + url, request);

      if (response.ok && returnsOutput) {
        const jsonOut = await response.json();
        return jsonOut;
      } else if (response.ok) {
        return;
      }

      handleErrors(url, request, response);
    } catch (error) {
      throw error;
    }
  }

  async function remove(url, body) {
    const request = getRequest("DELETE", JSON.stringify(body));

    const response = await fetch(BASE_URL.API_URL + url, request);

    if (response.ok) {
      const jsonOut = await response.json();
      return jsonOut;
    }

    handleErrors(url, request, response);
  }

  function handleErrors(url, request, response) {
    switch (response.status) {
      case 400:
        throw new BadRequestError();
      case 401:
        throw new UnauthorizedError();
      case 403:
        tryRefreashToken(url, request);
        break;
      case 404:
        throw new NotFoundError();
      case 409:
        throw new ConflictError();
      default:
        throw new ApiError("Unhandled error occurred.", -1);
    }
  }

  async function tryRefreashToken(url, request) {
    try {
      const response = await fetch(
        BASE_URL.API_URL + "/auth/refresh-token",
        getRequest("POST")
      );

      if (response.ok) {
        retryRequest(url, request);
        return;
      }

      handleErrors(response);
    } catch (error) {
      throw error;
    }
  }

  async function retryRequest(url, request) {
    const response = await fetch(BASE_URL.API_URL + url, request);
    if (response.ok) {
      return;
    }
  }

  return { get, post, put, remove };
};

export default useHttpApi;
