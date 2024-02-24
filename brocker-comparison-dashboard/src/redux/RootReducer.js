import { combineReducers } from "redux";
import UserReducer from "./reducers/UserReducer";
import TestReportReducer from "./reducers/TestReportReducer";

const rootReducer = combineReducers({
  testReport: TestReportReducer,
  user: UserReducer,
});

export default rootReducer;
