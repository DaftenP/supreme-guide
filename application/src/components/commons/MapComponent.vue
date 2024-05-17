<script setup>
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import { ref, computed, defineProps } from "vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

const map = ref();
const props = defineProps({
  attractions: Object,
});

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const mouseOverKakaoMapMarker = (param) => {
  param.visible = true;
};
const mouseOutKakaoMapMarker = (param) => {
  param.visible = false;
};

const panTo = () => {
  if (map.value) {
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.value.panTo(new kakao.maps.LatLng(33.45058, 126.574942));
  }
};
</script>

<template>
  <KakaoMap
    :lat="mapStore.lat"
    :lng="mapStore.lng"
    @onLoadKakaoMap="onLoadKakaoMap"
    width="auto">
    <KakaoMapMarker
      v-for="(attraction, index) in attractions"
      :key="index"
      :lat="attraction.latitude"
      :lng="attraction.longitude"
      :title="attraction.title"
      :infoWindow="{
        content: `<h3>${attraction.title}</h3>`,
        visible: attraction.visible == true,
      }"
      :image="{
        imageSrc:
          'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png',
        imageWidth: 64,
        imageHeight: 64,
        imageOption: {},
      }"
      @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(attraction)"
      @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(attraction)" />
  </KakaoMap>
  <!-- <div>
    <button @click="changeLatLng" class="demo-button">
      좌표값 변경으로 중심좌표 부드러운 이동
    </button>
    <button @click="setCenter" class="demo-button">
      map 객체로 중심좌표 이동
    </button>
    <button @click="panTo" class="demo-button">map 객체로 부드러운 이동</button>
  </div> -->
</template>
