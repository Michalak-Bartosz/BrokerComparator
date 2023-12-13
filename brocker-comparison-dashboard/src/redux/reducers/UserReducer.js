import {
  ADD_USER,
  REMOVE_USER,
  UPDATE_USER,
} from "../constants/userActionsType";

const initialUser = {
  username: null,
};

const UserReducer = (state = initialUser, action) => {
  switch (action.type) {
    case ADD_USER:
      return {
        ...state,
        username: action.payload,
      };
    case UPDATE_USER:
      return {
        ...state,
        username: action.payload,
      };
    case REMOVE_USER:
      return {
        ...state,
        username: null,
      };
    default:
      return state;
  }
};

export default UserReducer;
