<script setup>
import {
  KakaoMap,
  KakaoMapMarker,
  KakaoMapMarkerPolyline,
} from "vue3-kakao-maps";
import { ref, computed, onBeforeMount } from "vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();
const error = ref(null);
const map = ref();
const props = defineProps({
  attractions: Object,
  searchPlaces: String,
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
  getLocation();
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
    const bounds = new kakao.maps.LatLngBounds();
    const geocoder = new kakao.maps.services.Geocoder();

    for (let marker of data) {
      const markerItem = {
        lat: marker.y,
        lng: marker.x,
        address: "",
        title: marker.place_name,
        visible: false,
      };

      // 위도, 경도를 주소로 변환
      geocoder.coord2Address(marker.x, marker.y, (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
          markerItem.address = result[0].address.address_name;
        } else {
          markerItem.address = "주소 검색 실패";
        }
      });

      newMarkerList.value.push(markerItem);
      bounds.extend(new kakao.maps.LatLng(Number(marker.y), Number(marker.x)));
    }

    markerList.value = newMarkerList.value;
    console.log(markerList.value);
    map.value.setBounds(bounds);
  } else {
    console.error("검색 실패:", status);
  }
};

const onClickMapMarker = (markerItem) => {
  // Map Store의 상태 업데이트
  mapStore.lat = markerItem.lat;
  mapStore.lng = markerItem.lng;
  mapStore.attractionInfo = markerItem;
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

const getLocation = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(setPosition, showError);
  } else {
    error.value = "이 브라우저에서는 Geolocation을 지원하지 않습니다.";
  }
};

const setPosition = (position) => {
  mapStore.lat = position.coords.latitude;
  mapStore.lng = position.coords.longitude;
  error.value = null;
};

const showError = (errorObj) => {
  switch (errorObj.code) {
    case errorObj.PERMISSION_DENIED:
      error.value = "사용자가 Geolocation 요청을 거부했습니다.";
      break;
    case errorObj.POSITION_UNAVAILABLE:
      error.value = "위치 정보를 사용할 수 없습니다.";
      break;
    case errorObj.TIMEOUT:
      error.value = "사용자 위치 요청이 시간 초과되었습니다.";
      break;
    case errorObj.UNKNOWN_ERROR:
      error.value = "알 수 없는 오류가 발생했습니다.";
      break;
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
      height="100%"
      level="6">
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
        @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(attraction)"
        @onClickKakaoMapMarker="onClickMapMarker(attraction)" />

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
