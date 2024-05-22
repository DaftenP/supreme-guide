<script setup>
import { ref, reactive, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import axios from "axios";
import MapComponent from "@/components/commons/MapComponent2.vue";
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
  <div class="container">
    <div class="left-panel">
      <MapComponent ref="mapComponentRef"></MapComponent>
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
            }"
          >
            {{ place.place_name }}
          </li>
        </ul>
      </div>
      <form @submit.prevent="modifyHotplace">
        <div class="form-group">
          <label for="title">제목</label>
          <input type="text" id="title" v-model="hotplace.hotplaceName" required />
        </div>
        <div class="col-lg-2 col-md-2 col-12 p-2">
          <label for="type">관광지</label>
          <select
            class="form-select ps-3"
            name="category"
            id="search-content-id"
            v-model="hotplace.category"
          >
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
          <textarea
            id="description"
            v-model="hotplace.comment"
            required
          ></textarea>
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
            @update:image="updateImage"
          />
        </div>
        <button type="submit">수정</button>
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
