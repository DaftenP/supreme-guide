import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useMapStore = defineStore("map", () => {
  const lat = ref(33.450701);
  const lng = ref(126.570667);
  const attractionInfo = ref({});

  return {
    lat,
    lng,
    attractionInfo,
  };
});
