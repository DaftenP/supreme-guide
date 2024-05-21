<script setup>
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import { ref, computed, defineProps } from "vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

const map = ref();
const markerList = ref([]);
const props = defineProps({
  attractions: Object,
  searchPlaces: String,
});

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const searchPlaces = () => {
  // 장소 검색 객체를 생성합니다.
  const ps = new kakao.maps.services.Places();
  // 키워드로 장소를 검색합니다.
  ps.keywordSearch(props.searchPlaces, placesSearchCB);
};

// 키워드 검색 완료 시 호출되는 콜백 함수
const placesSearchCB = (data, status) => {
  if (status === kakao.maps.services.Status.OK) {
    const newMarkerList = [];
    const bounds = new kakao.maps.LatLngBounds();

    for (let marker of data) {
      const markerItem = {
        lat: marker.y,
        lng: marker.x,
        infoWindow: {
          content: marker.place_name,
          visible: false,
        },
      };
      newMarkerList.push(markerItem);
      bounds.extend(new kakao.maps.LatLng(Number(marker.y), Number(marker.x)));
    }

    console.log("```````````hihi```````````");
    markerList.value = newMarkerList;
    console.log(markerList.value);
    map.value.setBounds(bounds);
  } else {
    console.error("검색 실패:", status);
  }
};

const onClickMapMarker = (markerItem) => {
  console.log("item-----" + markerItem.value);
  if (
    markerItem.infoWindow?.visible !== null &&
    markerItem.infoWindow !== undefined
  ) {
    markerItem.infoWindow.visible = !markerItem.infoWindow.visible;
  } else {
    markerItem.infoWindow.visible = true;
  }
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

// searchPlaces 함수를 외부에 노출
defineExpose({
  searchPlaces,
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
        @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(attraction)"
        @onClickKakaoMapMarker="onClickMapMarker(attraction)" />
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
