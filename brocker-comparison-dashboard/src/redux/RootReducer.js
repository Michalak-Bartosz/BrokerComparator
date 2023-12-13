import { combineReducers } from "redux";
import TokenReducer from "./reducers/TokenReducer";
import UserReducer from "./reducers/UserReducer";
import TestInfoArrayReducer from "./reducers/TestInfoArrayReducer";

const rootReducer = combineReducers({
  token: TokenReducer,
  user: UserReducer,
  testInfoArray: TestInfoArrayReducer,
});

export default rootReducer;
