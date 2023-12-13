import {
  ADD_CONSUMER_TEST_INFO_ARRAY,
  CLEAR_CONSUMER_TEST_INFO_ARRAY,
  ADD_PRODUCER_TEST_INFO_ARRAY,
  CLEAR_PRODUCER_TEST_INFO_ARRAY,
} from "../constants/testInfoArrayActionType";

export const addConsumerTestInfoArrayAction = (testInfo) => {
  return {
    type: ADD_CONSUMER_TEST_INFO_ARRAY,
    payload: testInfo,
  };
};

export const clearConsumerTestInfoArrayAction = () => {
  return {
    type: CLEAR_CONSUMER_TEST_INFO_ARRAY,
  };
};

export const addProducerTestInfoArrayAction = (testInfo) => {
  return {
    type: ADD_PRODUCER_TEST_INFO_ARRAY,
    payload: testInfo,
  };
};

export const clearProducerTestInfoArrayAction = () => {
  return {
    type: CLEAR_PRODUCER_TEST_INFO_ARRAY,
  };
};
