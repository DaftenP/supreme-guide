<template>
  <div class="container">
    <div class="left-panel">
      <MapComponent></MapComponent>
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
            @click="selectPlace(place)">
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
import { ref } from "vue";
import MapComponent from "@/components/commons/MapComponent.vue";
import PhotoUpload from "../item/PhotoUpload.vue";

const searchQuery = ref("");
const places = ref([]);
const selectedPlace = ref(null);
const title = ref("");
const category = ref("");
const description = ref("");
const image = ref(null);

const updateImage = (base64String) => {
  image.value = base64String;
};

const searchPlace = () => {
  // 카카오맵 API를 사용하여 장소를 검색하는 로직을 추가합니다.
  // 검색 결과는 places 배열에 저장됩니다.
  // 예: places.value = 검색 결과;
};

const selectPlace = (place) => {
  selectedPlace.value = place;
  // 선택된 장소를 처리하는 로직을 추가합니다.
};

const submitForm = () => {
  const formData = {
    title: title.value,
    category: category.value,
    description: description.value,
    image: image.value,
    place: selectedPlace.value,
  };
  // formData를 서버에 전송하는 로직을 추가합니다.
  console.log(formData);
};
</script>

<style scoped>
#div-map {
  width: 100% !important;
  height: 100% !important;
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
.form-group {
  margin-bottom: 20px;
}
</style>
