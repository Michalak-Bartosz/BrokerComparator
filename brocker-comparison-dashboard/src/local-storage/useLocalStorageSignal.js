import { effect, signal } from "@preact/signals-react";

function getStorageValue(key, defaultValue) {
  const saved = localStorage.getItem(key);
  if (saved) {
    return JSON.parse(saved);
  }
  return defaultValue;
}

export const createLocalStorageSignal = (key, defaultValue) => {
  const singalValue = signal(getStorageValue(key, defaultValue));
  console.log("Register: " + key + " with value: " + singalValue.value);
  console.log(singalValue.value);
  effect(() => {
    localStorage.setItem(key, JSON.stringify(singalValue.value));
  });

  return singalValue;
};
