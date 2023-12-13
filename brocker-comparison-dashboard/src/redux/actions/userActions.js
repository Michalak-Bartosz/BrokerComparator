import {
  ADD_USER,
  REMOVE_USER,
  UPDATE_USER,
} from "../constants/userActionsType";

export const addUserAction = (user) => {
  return {
    type: ADD_USER,
    payload: user,
  };
};

export const updateUserAction = (user) => {
  return {
    type: UPDATE_USER,
    payload: user,
  };
};

export const removeUserAction = () => {
  return {
    type: REMOVE_USER,
  };
};
