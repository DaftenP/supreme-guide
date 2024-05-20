import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useTripStore = defineStore("trip", () => {
  const trip = ref({});

  return {
    trip,
  };
});
