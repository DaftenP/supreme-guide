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
  <div class="container my-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <MapComponent :searchPlaces="searchQuery" ref="mapComponentRef"></MapComponent>
      </div>
      <div class="col-md-6">
        <div class="search-box mb-4">
          <input v-model="searchQuery" placeholder="장소 검색" class="form-control" />
          <button @click="searchPlaces" class="btn btn-primary mt-2">검색</button>
        </div>
        <form @submit.prevent="submitForm" class="form-needs">
          <div class="form-group mb-3">
            <label for="title">제목</label>
            <input type="text" id="title" v-model="title" class="form-control" required />
          </div>
          <div class="form-group mb-3">
            <label for="type">관광지 유형</label>
            <select class="form-select" v-model="category" required>
              <option disabled value="">선택해주세요</option>
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
          <div class="form-group mb-3">
            <label for="description">설명</label>
            <textarea id="description" v-model="description" class="form-control" required></textarea>
          </div>
          <div class="form-group mb-3">
            <label>이미지 첨부</label>
            <PhotoUpload :width="'100%'" :height="'100%'" @update:image="updateImage" />
          </div>
          <button type="submit" class="btn btn-success">등록</button>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 960px;
  margin: auto;
}
.form-needs .form-group label {
  font-weight: bold;
}
.form-needs .form-group input,
.form-needs .form-group select,
.form-needs .form-group textarea {
  margin-top: 0.5rem;
}
</style>