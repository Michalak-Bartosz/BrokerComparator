import { effect, signal } from "@preact/signals-react";

function getStorageValue(key, defaultValue) {
  const saved = localStorage.getItem(key);
  const initial = JSON.parse(saved.value);
  return initial || defaultValue;
}

export const useLocalStorage = (key, defaultValue) => {
  const item = signal(() => {
    return getStorageValue(key, defaultValue);
  });

  effect(() => {
    localStorage.setItem(key, JSON.stringify(item.value));
  });

  return item;
};
