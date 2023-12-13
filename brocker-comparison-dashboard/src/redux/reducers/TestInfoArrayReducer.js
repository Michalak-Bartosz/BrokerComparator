import {
  ADD_CONSUMER_TEST_INFO_ARRAY,
  CLEAR_CONSUMER_TEST_INFO_ARRAY,
  ADD_PRODUCER_TEST_INFO_ARRAY,
  CLEAR_PRODUCER_TEST_INFO_ARRAY,
} from "../constants/testInfoArrayActionType";

const initialTestInfoArray = {
  consumerTestInfoArray: [],
  producerTestInfoArray: [],
};

const TestInfoArrayReducer = (state = initialTestInfoArray, action) => {
  switch (action.type) {
    case ADD_CONSUMER_TEST_INFO_ARRAY:
      return {
        ...state,
        consumerTestInfoArray: [...state.consumerTestInfoArray, action.payload],
      };
    case CLEAR_CONSUMER_TEST_INFO_ARRAY:
      return {
        ...state,
        consumerTestInfoArray: [],
      };
    case ADD_PRODUCER_TEST_INFO_ARRAY:
      return {
        ...state,
        producerTestInfoArray: [...state.producerTestInfoArray, action.payload],
      };
    case CLEAR_PRODUCER_TEST_INFO_ARRAY:
      return {
        ...state,
        producerTestInfoArray: [],
      };
    default:
      return state;
  }
};

export default TestInfoArrayReducer;
