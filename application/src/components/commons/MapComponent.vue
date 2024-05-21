<script setup>
import {
  KakaoMap,
  KakaoMapMarker,
  KakaoMapMarkerPolyline,
} from "vue3-kakao-maps";
import { ref, computed, onBeforeMount } from "vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

const map = ref();
const props = defineProps({
  attractions: Object,
  tripList: Object,
});

const markerList = computed(() => {
  return props.tripList
    ? props.tripList.map((el, idx) => ({
        lat: el.latitude,
        lng: el.longitude,
        order:
          idx == 0
            ? "출발"
            : idx == props.tripList.length - 1
            ? "도착"
            : idx + 1,
      }))
    : ref([]);
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
// /
const panTo = () => {
  if (map.value) {
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.value.panTo(new kakao.maps.LatLng(33.45058, 126.574942));
  }
};
</script>

<template>
  <div id="div-map">
    <KakaoMap
      :lat="mapStore.lat"
      :lng="mapStore.lng"
      @onLoadKakaoMap="onLoadKakaoMap"
      width="100%"
      height="100%"
      level="11">
      <KakaoMapMarker
        v-for="(attraction, index) in attractions"
        :key="index"
        :lat="attraction.latitude"
        :lng="attraction.longitude"
        :title="attraction.title"
        :infoWindow="{
          content: `
            <span class=&quot;border border-2 rounded &quot;>${attraction.title}</span>
          `,
          visible: attraction.visible == true,
        }"
        :image="{
          imageSrc: `/src/assets/img/marker-icons/${attraction.category}.png`,
          imageWidth: 48,
          imageHeight: 48,
          imageOption: {},
        }"
        @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(attraction)"
        @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(attraction)" />

      <KakaoMapMarkerPolyline
        v-if="tripList"
        :markerList="markerList"
        :showMarkerOrder="true"
        strokeColor="#C74C5E"
        :strokeOpacity="1"
        strokeStyle="shortdot" />
    </KakaoMap>
  </div>
</template>

<style scoped>
#div-map {
  width: 100%;
  height: 100%;
}
.marker-info {
  background-color: darkblue;
}
</style>
