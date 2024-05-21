import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useMapStore = defineStore("map", () => {
  const lat = ref(33.450701);
  const lng = ref(126.570667);
  const attractions = ref([]);
  const attractionInfo = ref({});
  const blogList = ref([]);

  return {
    lat,
    lng,
    attractions,
    attractionInfo,
    blogList,
  };
});
