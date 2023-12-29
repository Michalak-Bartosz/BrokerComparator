import { combineReducers } from "redux";
import TokenReducer from "./reducers/TokenReducer";
import UserReducer from "./reducers/UserReducer";

const rootReducer = combineReducers({
  token: TokenReducer,
  user: UserReducer,
});

export default rootReducer;
