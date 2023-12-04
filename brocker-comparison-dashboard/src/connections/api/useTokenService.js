import { effect, signal } from "@preact/signals-react";

const USER = "user";

export const user = signal(JSON.parse(localStorage.getItem(USER)));

effect(() => {
  localStorage.setItem(USER, JSON.stringify(user.value));
});

const useTokenService = () => {
  async function getLocalRefreshToken() {
    return user.value?.refreshToken;
  }

  async function getLocalAccessToken() {
    return user.value?.accessToken;
  }

  async function updateNewAccessToken(token) {
    user.accessToken = token;
  }

  async function setUser(newUser) {
    user.value = newUser;
  }

  async function removeUser() {
    user.value = undefined;
  }

  return {
    getLocalRefreshToken,
    getLocalAccessToken,
    updateNewAccessToken,
    setUser,
    removeUser,
  };
};

export default useTokenService;
