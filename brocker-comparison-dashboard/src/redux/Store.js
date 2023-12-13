import { configureStore } from "@reduxjs/toolkit";
import persistedReducer from "./persistedReducer.js";

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
      immutableCheck: false,
    }),
});

export default store;
