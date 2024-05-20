<script setup>
import AttractionSearch from "@/components/attraction/AttractionSearch.vue";
import MapComponent from "@/components/commons/MapComponent.vue";
import AttractionModal from "@/components/modal/AttractionModal.vue";
import AttractionSideBar from "@/components/attraction/AttractionSideBar.vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();
</script>

<template>
  <div id="attraction-view" class="flex-grow-1 d-flex flex-column">
    <AttractionSearch></AttractionSearch>
    <div
      id="div-map"
      class="flex-grow-1 d-flex align-items-center justify-content-center">
      <MapComponent :attractions="mapStore.attractions"></MapComponent>
    </div>
    <div>
      <AttractionSideBar
        :attractions="mapStore.attractions"></AttractionSideBar>
    </div>
    <AttractionModal />
  </div>
</template>

<style scoped></style>
