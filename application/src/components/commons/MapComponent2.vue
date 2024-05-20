<script setup>
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import { ref, defineProps, watch } from "vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

const map = ref();
const props = defineProps({
  attractions: Object,
  searchResults: Array,
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
    map.value.panTo(new kakao.maps.LatLng(33.45058, 126.574942));
  }
};

watch(() => props.searchResults, (newResults) => {
  if (newResults && newResults.length > 0) {
    const firstResult = newResults[0];
    map.value.panTo(new kakao.maps.LatLng(firstResult.latitude, firstResult.longitude));
  }
});
</script>

<template>
  <div id="div-map">
    <KakaoMap
      :lat="mapStore.lat"
      :lng="mapStore.lng"
      @onLoadKakaoMap="onLoadKakaoMap"
      width="100%"
      height="100%">
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
          imageSrc:
            'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png',
          imageWidth: 64,
          imageHeight: 64,
          imageOption: {},
        }"
        @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(attraction)"
        @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(attraction)" />
      <KakaoMapMarker
        v-for="(result, index) in searchResults"
        :key="index"
        :lat="result.latitude"
        :lng="result.longitude"
        :title="result.place_name"
        @click="selectPlace(result)" />
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
