import { defineStore } from "pinia";
import Cookies from "vue-cookies";

export const useUserStore = defineStore("user", {
  state: () => ({
    userId: null,
  }),
  getters: {
    isLoggedIn: (state) => !!state.userId,
  },
  actions: {
    setUserId(userId) {
      this.userId = userId;
    },
    logout() {
      this.userId = null;
      Cookies.remove("accessToken");
      Cookies.remove("refreshToken");
    },
  },
});
