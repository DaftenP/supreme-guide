<script setup>
import { ref, defineProps } from "vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

const props = defineProps({
  attraction: Object,
});

const goTo = () => {
  mapStore.lat = props.attraction.latitude;
  mapStore.lng = props.attraction.longitude;
};
</script>

<template>
  <div>
    <img class="thumbnail" :src="attraction.firstImage" />
    <a @click="goTo">{{ attraction.title }}</a>
    <div>{{ attraction.addr1 + attraction.addr2 }}</div>
    <button
      class="btn btn-outline-danger"
      @click="$emit('detailView', attraction.contentId)">
      상세정보
    </button>
  </div>
</template>

<style scoped>
.thumbnail {
  width: 70px;
}
</style>
