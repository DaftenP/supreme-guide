<script setup>
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import axios from "axios";
import MapComponent from "@/components/commons/MapComponent2.vue";
import PhotoUpload from "../item/PhotoUpload.vue";
import authClient from "@/api/authClient";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

const searchQuery = ref("");
const places = ref([]);
const selectedPlace = ref(null);
const title = ref("");
const category = ref("");
const description = ref("");
const image = ref(null);
const mapComponentRef = ref(null);

let map = null;
const markerList = ref([]);

const router = useRouter();

const searchPlaces = () => {
  if (
    mapComponentRef.value &&
    typeof mapComponentRef.value.searchPlaces === "function"
  ) {
    mapComponentRef.value.searchPlaces(searchQuery.value);
  } else {
    console.error("The searchPlaces function does not exist in mapComponent.");
  }
};

const updateImage = (base64String) => {
  const base64Data = base64String.replace(/^data:image\/[a-z]+;base64,/, "");
  image.value = base64Data;
  console.log(image.value);
};

const submitForm = async () => {
  const hotplace = reactive({
    hotplaceName: title.value,
    latitude: mapStore.lat,
    longitude: mapStore.lng,
    addr1: mapStore.attractionInfo.address,
    comment: description.value,
    category: category.value,
    image: image.value,
  });

  try {
    const res = await authClient({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/regist`,
      data: hotplace,
    });
    alert("핫플레이스가 성공적으로 등록되었습니다.");
  } catch (error) {
    console.error(error);
    alert("핫플레이스 등록에 실패했습니다.");
  }
  goList();
};

const goList = () => {
  router.push({ name: "HotplaceList" });
};
</script>

<template>
  <div class="container">
    <div class="left-panel">
      <MapComponent
        :searchPlaces="searchQuery"
        ref="mapComponentRef"></MapComponent>
    </div>
    <div class="right-panel">
      <div class="search-box">
        <input v-model="searchQuery" placeholder="장소 검색" />
        <button @click="searchPlaces">검색</button>

        <ul v-if="places.length">
          <li
            v-for="place in places"
            :key="place.id"
            @click="selectPlace(place)"
            :class="{
              selected: selectedPlace && selectedPlace.id === place.id,
            }">
            {{ place.place_name }}
          </li>
        </ul>
      </div>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="title">제목</label>
          <input type="text" id="title" v-model="title" required />
        </div>
        <div class="col-lg-2 col-md-2 col-12 p-2">
          <label for="type">관광지</label>
          <select
            class="form-select ps-3"
            name="category"
            id="search-content-id"
            v-model="categorySelectedOption">
            <option value="0" selected>관광지 유형</option>
            <option value="12">관광지</option>
            <option value="14">문화시설</option>
            <option value="15">축제공연행사</option>
            <option value="25">여행코스</option>
            <option value="28">레포츠</option>
            <option value="32">숙박</option>
            <option value="38">쇼핑</option>
            <option value="39">음식점</option>
          </select>
        </div>
        <div class="form-group">
          <label for="description">설명</label>
          <textarea id="description" v-model="description" required></textarea>
        </div>
        <div class="form-group">
          <label for="ssss">선택한 장소</label>
          <div>{{ mapStore.attractionInfo.title }}</div>
          <div>{{ mapStore.attractionInfo.address }}</div>
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

<style scoped>
#div-map {
  height: 600px !important;
}
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
