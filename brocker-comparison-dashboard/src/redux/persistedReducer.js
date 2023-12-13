import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
import rootReducer from "./RootReducer";

const persistConfig = {
  key: "root",
  version: 1,
  storage,
};

const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
