import {
  ADD_TO_FOCUSED,
  CLEAR_ALL_FOCUSED,
  REMOVE_FROM_FOCUSED,
} from "../constants/testReportActionTypes";

export const addTestReportToFocuedAction = (testReport) => {
  return {
    type: ADD_TO_FOCUSED,
    payload: testReport,
  };
};

export const removeTestReportFromFocusedAction = (testReport) => {
  return {
    type: REMOVE_FROM_FOCUSED,
    payload: testReport,
  };
};

export const clearAllForcusedReportsAction = () => {
  return {
    type: CLEAR_ALL_FOCUSED,
  };
};
