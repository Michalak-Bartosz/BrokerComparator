import {
  ADD_TO_FOCUSED,
  CLEAR_ALL_FOCUSED,
  REMOVE_FROM_FOCUSED,
} from "../constants/testReportActionTypes";

const initialTestReportArray = {
  focusedReports: [],
};

const addReportToArray = (array, report) => {
  if (array.length === 0) {
    return [report];
  } else {
    return [...array, report];
  }
};

const TestReportReducer = (state = initialTestReportArray, action) => {
  switch (action.type) {
    case ADD_TO_FOCUSED:
      return {
        ...state,
        focusedReports: addReportToArray(state.focusedReports, action.payload),
      };
    case REMOVE_FROM_FOCUSED:
      return {
        ...state,
        focusedReports: [
          ...state.focusedReports.filter((report) => report !== action.payload),
        ],
      };
    case CLEAR_ALL_FOCUSED:
      return {
        ...state,
        focusedReports: [],
      };
    default:
      return state;
  }
};

export default TestReportReducer;
