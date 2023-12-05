import {
  ADD_TOKEN,
  REMOVE_TOKEN,
  UPDATE_ACCESS_TOKEN,
} from "../constants/tokenActionTypes";

export const addTokenAction = (token) => {
  return {
    type: ADD_TOKEN,
    payload: token,
  };
};

export const updateAccessTokenAction = (accessToken) => {
  return {
    type: UPDATE_ACCESS_TOKEN,
    payload: accessToken,
  };
};

export const removeTokenAction = () => {
  return {
    type: REMOVE_TOKEN,
  };
};
