<script setup>
import { ref, defineProps } from "vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

const props = defineProps({
  attraction: Object,
  type: String,
});

const goTo = () => {
  mapStore.lat = props.attraction.latitude;
  mapStore.lng = props.attraction.longitude;
};
</script>

<template>
  <div class="bg-lightgray d-flex align-items-center m-2">
    <img
      class="thumbnail m-2"
      :src="
        attraction.firstImage
          ? attraction.firstImage
          : '/src/assets/img/no-img.png'
      " />
    <div>
      <a class="attraction-title" @click="goTo">{{ attraction.title }}</a>
      <br />
      <a>{{ attraction.addr1 + attraction.addr2 }}</a>
      <button
        class="btn btn-outline-danger"
        @click="$emit('detailView', attraction.contentId)"
        data-bs-toggle="modal"
        data-bs-target="#attractionModal">
        상세정보
      </button>
      <button
        v-if="type"
        class="btn btn-outline-primary"
        @click="$emit('addItem', attraction)">
        추가
      </button>
    </div>
  </div>
</template>

<style scoped>
.thumbnail {
  width: 100px; /* 원하는 너비 설정 */
  height: 100px; /* 원하는 높이 설정 */
  object-fit: cover; /* 비율을 유지하면서 주어진 너비와 높이에 맞게 조정 */
  object-position: center; /* 이미지가 잘릴 때 중앙을 기준으로 잘리도록 설정 */
  display: block; /* 이미지를 블록 요소로 설정 */
  overflow: hidden; /* 넘치는 부분은 숨김 */
}
a {
  display: block;
  color: black;
  text-decoration: none;
  padding: 0px;
  margin: 0px;
}
.attraction-title {
  font-size: large;
}
.bg-lightgray {
  background-color: #f6fcfb;
}
</style>
