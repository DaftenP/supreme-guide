import { defineStore } from "pinia";
import { ref, computed } from "vue";
import Cookies from "vue-cookies";

export const useUserStore = defineStore(
  "user",
  () => {
    const userId = ref("");

    const isLoggedIn = computed(() => {
      return userId.value !== "";
    });

    const setUserId = (id) => {
      userId.value = id;
    };

    return {
      userId,
      isLoggedIn,
      setUserId,
    };
  },

  { persist: true }
);
