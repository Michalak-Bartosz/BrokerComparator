import {
  ADD_TOKEN,
  REMOVE_TOKEN,
  UPDATE_ACCESS_TOKEN,
} from "../constants/tokenActionTypes";

const initialToken = {
  accessToken: undefined,
  refreshToken: undefined,
};

const TokenReducer = (state = initialToken, action) => {
  switch (action.type) {
    case ADD_TOKEN:
      return {
        ...state,
        accessToken: action.payload.accessToken,
        refreshToken: action.payload.refreshToken,
      };
    case UPDATE_ACCESS_TOKEN:
      return {
        ...state,
        accessToken: action.payload,
      };
    case REMOVE_TOKEN:
      return {
        ...state,
        accessToken: undefined,
        refreshToken: undefined,
      };
    default:
      return state;
  }
};

export default TokenReducer;
