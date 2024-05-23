import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const mapListStore = defineStore("maplist", () => {
  const markerList = ref([]);
  return {
    markerList,
  };
});
