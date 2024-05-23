<script setup>
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import axios from "axios";
import MapComponent from "@/components/commons/MapComponent.vue";
import PhotoUpload from "../item/PhotoUpload.vue";
import authClient from "@/api/authClient";
import { useMapStore } from "@/stores/map";
import { useUserStore } from "@/stores/userStore";
const mapStore = useMapStore();
const userStore = useUserStore();

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
  <div class="blog-post">
    <div class="col-lg-8">
      <h2 class="blog-post-title font-style">HotPlace 등록</h2>
    </div>
    <p class="blog-post-meta">
      {{ new Date().toLocaleDateString() }} by
      <a href="#">{{ userStore.userId }}</a>
    </p>

    <div class="row justify-content-center">
      <div class="col-md-6 map-container">
        <MapComponent
          :searchPlaces="searchQuery"
          ref="mapComponentRef"></MapComponent>
      </div>
      <div class="col-md-5 form-container">
        <div class="search-box mb-4">
          <input
            v-model="searchQuery"
            placeholder="장소 검색"
            class="form-control search-input" />
          <button
            @click="searchPlaces"
            class="btn btn-primary mt-2 search-button">
            <font-awesome-icon
              :icon="['fas', 'magnifying-glass']"
              style="color: #ffffff" />
          </button>
        </div>
        <form @submit.prevent="submitForm" class="form-needs">
          <div class="title_line mb-3">
            <label for="title">제목</label>
            <input
              id="title"
              v-model="title"
              class="form-control title-input"
              placeholder="여기에 제목을 적어주세요"
              required />
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
            <label for="content">설명</label>
            <textarea
              id="description"
              v-model="description"
              class="form-control"
              rows="5"
              placeholder="내용을 여기에 입력하세요..."
              required></textarea>
          </div>
          <div class="form-group mb-3">
            <label for="selected-place">선택한 장소</label>
            <div>{{ mapStore.attractionInfo.title }}</div>
            <div>{{ mapStore.attractionInfo.address }}</div>
          </div>
          <div class="form-group mb-3">
            <label>이미지 첨부</label>
            <PhotoUpload
              :width="'100%'"
              :height="'100%'"
              @update:image="updateImage" />
          </div>
          <button type="submit" class="btn btn-success">등록</button>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
#div-map {
  height: 500px !important;
  width: 500px !important;
}
.map-container {
  border: 2px solid white; /* 액자 효과 */
  padding: 10px;

  overflow: hidden;
  /* margin-right: 20px; */
}

.form-container {
  background-color: #f9f9f9; /* 공지사항 스타일 */
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
}

.search-box {
  display: flex;
  align-items: center;
}

.search-input {
  border-radius: 4px;
  border: 1px solid #ced4da;
  padding: 10px;
  width: calc(100% - 45px);
  margin-right: 10px;
}

.search-button {
  background-color: #007bff;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  color: #ffffff;
}

.form-needs .form-group {
  margin-bottom: 15px;
}

.form-needs .form-group label {
  font-weight: bold;
}

.form-needs .form-control,
.form-needs .form-select {
  border-radius: 5px;
  padding: 10px;
  border: 1px solid #ccc;
}

.form-needs textarea.form-control {
  resize: vertical;
}

.form-needs .btn-success {
  background-color: #28a745;
  border-color: #28a745;
}

.font-style {
  font-family: "CustomFont";
  font-size: 50px;
}

.blog-post {
  margin: 2rem auto;
  padding: 2rem;
  background-color: white;
  border-radius: 0.5rem;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
  max-width: 1200px;
}

.blog-post-title {
  margin-bottom: 1rem;
  font-size: 2rem;
  font-weight: 600;
}

.blog-post-meta {
  margin-bottom: 2rem;
  color: #6c757d;
}

.title-line {
  margin-bottom: 1.5rem;
  border-bottom: 2px solid #ccc;
}

.title-input {
  border-radius: 4px;
  border: 1px solid #ced4da;
  padding: 10px;
}

.title-placeholder {
  position: absolute;
  top: -0.8em;
  left: 0;
  background-color: #f8f9fa;
  padding: 0 0.5em;
  color: #6c757d;
  font-style: italic;
}

.form-select {
  border-radius: 4px;
  border: 1px solid #ced4da;
  padding: 10px;
}

.form-control {
  border-radius: 4px;
  border: 1px solid #ced4da;
  padding: 10px;
}

.btn-success {
  background-color: #28a745;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  color: #ffffff;
}

.btn-primary {
  background-color: #007bff;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  color: #ffffff;
}

.form-group label {
  font-weight: bold;
}

.form-group div {
  margin-bottom: 5px;
}
</style>
