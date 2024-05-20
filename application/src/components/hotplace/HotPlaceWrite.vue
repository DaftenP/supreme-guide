<template>
  <div class="container">
    <div class="left-panel">
      <MapComponent :searchResults="places" @selectPlace="selectPlace"></MapComponent>
    </div>
    <div class="right-panel">
      <div class="search-box">
        <input
          v-model="searchQuery"
          @keydown.enter="searchPlace"
          placeholder="장소 검색" />
        <button @click="searchPlace">검색</button>
        
        <ul v-if="places.length">
          <li
            v-for="place in places"
            :key="place.id"
            @click="selectPlace(place)"
            :class="{ selected: selectedPlace && selectedPlace.id === place.id }">
            {{ place.place_name }}
          </li>
        </ul>
      </div>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="title">제목</label>
          <input type="text" id="title" v-model="title" required />
        </div>
        <div class="form-group">
          <label for="category">카테고리</label>
          <input type="text" id="category" v-model="category" required />
        </div>
        <div class="form-group">
          <label for="description">설명</label>
          <textarea id="description" v-model="description" required></textarea>
        </div>
        <div class="form-group">
          <label>이미지 첨부</label>
          <PhotoUpload
            :width="'100%'"
            :height="'100%'"
            @update:image="updateImage" />
        </div>
        <button type="submit">등록</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import { useRouter } from 'vue-router';
import MapComponent from "@/components/commons/MapComponent2.vue";
import PhotoUpload from "../item/PhotoUpload.vue";
import authClient from "@/api/authClient";
import axios from "axios";

const searchQuery = ref("");
const places = ref([]);
const selectedPlace = ref(null);
const title = ref("");
const category = ref("");
const description = ref("");
const image = ref(null);
const KAKAO_API_KEY = import.meta.env.VITE_API_KAKAO_MAP_REST_KEY;
const map = ref(null);
const markers = ref([]);
const router = useRouter();
let infowindow = null;

const updateImage = (base64String) => {
  image.value = base64String;
};

const goList = () => {
  router.push({
    name: "HotplaceList",
  });
}

const createMarker = (place) => {
  console.log("hihi");
  const marker = {
    lat: place.latitude,
    lng: place.longitude,
    infoWindow: `<div style="padding:5px;">${place.place_name}</div>`,
  };
  markers.value.push(marker);

  // kakao.maps.event.addListener(marker, 'click', () => {
  //   selectPlace(place);
  //   if (infowindow) {
  //     infowindow.close();
  //   }
  //   infowindow = new kakao.maps.InfoWindow({
  //     content: `<div style="padding:5px;">${place.place_name}</div>`,
  //   });
  //   infowindow.open(map.value, marker);
  //   map.value.panTo(new kakao.maps.LatLng(place.latitude, place.longitude));
  // });

  // markers.value.push(marker);
};

const onClickMapMarker = (marker) => {
  console.log(marker);
  console.log(`Marker Clicked - Latitude: ${marker.lat}, Longitude: ${marker.lng}`);
  if (map.value) {
    const position = new kakao.maps.LatLng(marker.lat, marker.lng);
    map.value.panTo(position);
    const infowindow = new kakao.maps.InfoWindow({
      content: marker.infoWindow,
    });
    infowindow.open(map.value, marker.marker);
  }
};

const clearMarkers = () => {
  markers.value.forEach(marker => marker.setMap(null));
  markers.value = [];
};

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const placesSearchCB = (data, status) => {
  if (status === kakao.maps.services.Status.OK) {
    const bounds = new kakao.maps.LatLngBounds();
    clearMarkers();

    for (let place of data) {
      places.value.push({
        id: place.id,
        place_name: place.place_name,
        latitude: place.y,
        longitude: place.x,
      });
      createMarker(place);
      bounds.extend(new kakao.maps.LatLng(place.y, place.x));
    }

    map.value.setBounds(bounds);
  }
};

const searchPlace = async () => {
  console.log(searchQuery.value);
  const url = `https://dapi.kakao.com/v2/local/search/keyword.json?query=${searchQuery.value}`;
  const config = {
    headers: {
      'Authorization': `KakaoAK ${KAKAO_API_KEY}`,
    },
    type: 'get'
  };
  try {
    const response = await axios.get(url, config);
    places.value = response.data.documents.map((place) => ({
      id: place.id,
      place_name: place.place_name,
      latitude: place.y,
      longitude: place.x,
    }));
    nextTick(() => {
      onLoadKakaoMap(map.value);
      places.value.forEach(createMarker);
    });
    console.log(response.data);
  } catch (error) {
    console.log(error);
    alert("장소 검색에 실패했습니다.");
  }
};

const selectPlace = (place) => {
  selectedPlace.value = place;
  console.log(place);
  console.log(`Selected Place - Latitude: ${place.latitude}, Longitude: ${place.longitude}`);
  if (map.value) {
    map.value.panTo(new kakao.maps.LatLng(place.latitude, place.longitude));
  }
};

const submitForm = async () => {
  const formData = {
    title: title.value,
    category: category.value,
    description: description.value,
    image: image.value,
    place: selectedPlace.value,
  };

  try {
    const res = await authClient({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/regist`,
      data: formData,
    });
    alert("핫플레이스가 성공적으로 등록되었습니다.");
  } catch (error) {
    console.log(error);
    alert("핫플레이스 등록에 실패했습니다.");
  }
};

onMounted(async () => {
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  await nextTick();
  map.value = new kakao.maps.Map(document.getElementById('map'), options);
});
</script>

<style scoped>
.container {
  display: flex;
}
.left-panel {
  width: 50%;
  padding: 20px;
}
.right-panel {
  width: 50%;
  padding: 20px;
}
.map {
  width: 100%;
  height: 500px;
}
.search-box {
  margin-bottom: 20px;
}
.selected {
  background-color: yellow;
}
</style>
