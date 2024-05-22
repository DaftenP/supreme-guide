<script setup>
import { ref, computed, defineProps } from "vue";
import AttractionSearch from "@/components/attraction/AttractionSearch.vue";
import MapComponent from "@/components/commons/MapComponent.vue";
import AttractionSideBar from "@/components/attraction/AttractionSideBar.vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();
const checkboxes = ref([
  { id: 12, label: "관광지", checked: true },
  { id: 14, label: "문화시설", checked: true },
  { id: 15, label: "축제", checked: true },
  { id: 25, label: "여행코스", checked: true },
  { id: 28, label: "레포츠", checked: true },
  { id: 32, label: "숙박", checked: true },
  { id: 38, label: "쇼핑", checked: true },
  { id: 39, label: "음식점", checked: true },
]);
const checkedIds = computed(() =>
  checkboxes.value.filter((c) => c.checked).map((c) => c.id)
);
const updateCheckbox = (id, checked) => {
  const checkbox = checkboxes.value.find((c) => c.id === id);
  if (checkbox) {
    checkbox.checked = checked;
  } else {
    checkbox.checked = !checked;
  }
};
const computedAttractionList = computed(() => {
  return mapStore.attractions.filter((c) =>
    checkedIds.value.includes(c.category)
  );
});
</script>

<template>
  <div id="attraction-view" class="flex-grow-1 d-flex flex-column">
    <AttractionSearch></AttractionSearch>
    <div
      id="div-map"
      class="flex-grow-1 d-flex align-items-center justify-content-center">
      <MapComponent :attractions="computedAttractionList"></MapComponent>
    </div>
    <div>
      <AttractionSideBar
        :checkboxes="checkboxes"
        :attractions="computedAttractionList"
        @update-checkbox="updateCheckbox"></AttractionSideBar>
    </div>
  </div>
</template>

<style scoped></style>
