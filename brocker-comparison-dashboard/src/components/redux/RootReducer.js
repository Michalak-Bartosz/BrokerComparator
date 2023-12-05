import { combineReducers } from "redux";
import TokenReducer from "./reducers/TokenReducer";

const rootReducer = combineReducers({ token: TokenReducer });

export default rootReducer;
