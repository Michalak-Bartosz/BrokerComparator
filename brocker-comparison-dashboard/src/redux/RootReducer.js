import { combineReducers } from "redux";
import TokenReducer from "./reducers/TokenReducer";
import UserReducer from "./reducers/UserReducer";
import TestReportReducer from "./reducers/TestReportReducer";

const rootReducer = combineReducers({
  testReport: TestReportReducer,
  token: TokenReducer,
  user: UserReducer,
});

export default rootReducer;
