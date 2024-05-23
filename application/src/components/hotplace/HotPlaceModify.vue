<script setup>
import { ref, reactive, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import axios from "axios";
import MapComponent from "@/components/commons/MapComponent.vue";
import PhotoUpload from "../item/PhotoUpload.vue";
import authClient from "@/api/authClient";
import { useMapStore } from "@/stores/map";
import noAuthClient from "@/api/noAuthClient";

const mapStore = useMapStore();

const route = useRoute();
const router = useRouter();

const hotplaceId = route.params.hotplaceId;

const searchQuery = ref("");
const places = ref([]);
const selectedPlace = ref(null);
const hotplace = ref({});
const mapComponentRef = ref(null);

let map = null;
const markerList = ref([]);

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
  hotplace.image = base64Data;
  console.log(hotplace.image);
};

const modifyHotplace = async () => {
  try {
    const res = await authClient({
      method: "put",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace`,
      data: hotplace,
    });
    console.log(res.data);
    alert("수정이 완료되었습니다.");
    goDetail(hotplaceId);
  } catch (error) {
    console.log(error);
    alert("작성자만 수정할 수 있습니다.");
  }
};

const goDetail = (id) => {
  router.push({
    name: "HotplaceDetail",
    params: {
      hotplaceId: id,
    },
  });
};
const goList = () => {
  router.push({ name: "HotplaceList" });
};

onMounted(async () => {
  try {
    const res = await noAuthClient({
      method: "get",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/view/${hotplaceId}`,
    });
    hotplace.value = res.data;
  } catch (error) {
    console.log(error);
    alert("핫플레이스 조회에 문제가 발생했습니다.");
  }
});
</script>

<template>
  <div class="container my-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <MapComponent
          :searchPlaces="searchQuery"
          ref="mapComponentRef"></MapComponent>
      </div>
      <div class="col-md-6">
        <div class="search-box mb-4">
          <input
            v-model="searchQuery"
            placeholder="장소 검색"
            class="form-control" />
          <button @click="searchPlaces" class="btn btn-primary mt-2">
            검색
          </button>
        </div>
        <form @submit.prevent="modifyHotplace" class="form-needs">
          <div class="form-group mb-3">
            <label for="title">장소명</label>
            <input
              type="text"
              id="title"
              v-model="hotplace.hotplaceName"
              class="form-control"
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
            <label for="description">설명</label>
            <textarea
              id="description"
              v-model="hotplace.comment"
              class="form-control"
              required></textarea>
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
  height: 600px !important;
}
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
