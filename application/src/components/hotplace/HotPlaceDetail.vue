<script setup>
import { ref, onBeforeMount, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import noAuthClient from "@/api/noAuthClient";
import { useUserStore } from "@/stores/userStore";
import { useMapStore } from "@/stores/map";
import authClient from "@/api/authClient";
import MapComponent from "@/components/commons/MapComponent2.vue";
const IMAGE_URL = import.meta.env.VITE_IMAGE_BASE_URL;

const userStore = useUserStore();
const mapStore = useMapStore();
const route = useRoute();
const router = useRouter();

const hotplace = ref({}); // hotplace객체
const comments = ref([]); // 댓글 관리 객체

// data
const newComment = reactive({
  commentContent: "",
});

const hotplaceId = route.params.hotplaceId;
const goModify = () => {
  router.push({
    name: "HotplaceModify",
    params: { hotplaceId: hotplace.value.hotplaceId },
  });
};

const deleteHotplace = async () => {
  try {
    const res = await authClient({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/${hotplaceId}`,
    });
    alert("삭제가 완료되었습니다.");
  } catch (error) {
    console.log(error);
    alert("작성자만 글 삭제가 가능합니다.");
  }
  goList();
};

// 핫플레이스 정보 가져오기
const fetchHotPlaces = async () => {
  try {
    const res = await noAuthClient({
      method: "get",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/view/${hotplaceId}`,
    });
    // pinia 상태 변경
    mapStore.lat = res.data.latitude;
    mapStore.lng = res.data.longitude;
    mapStore.attractionInfo = res.data;
    console.log(res.data);
    hotplace.value = res.data;
    hotplace.value.image = `/${IMAGE_URL}/${hotplace.value.image}`;
  } catch (error) {
    console.log(error);
    alert("정보를 가져오는데 문제가 발생했습니다.");
  }
};

const goList = () => {
  router.push({
    name: "HotplaceList",
  });
};

onBeforeMount(async () => {
  await fetchHotPlaces();
});
</script>

<template>
  <div class="container">
    <div class="right-panel">
      <div class="image">
        <img :src="hotplace.image" alt="핫플레이스 이미지" />
      </div>
    </div>
    <div class="left-panel">
      <MapComponent></MapComponent>
      <div class="title">
        <h1>{{ hotplace.hotplaceName }}</h1>
      </div>
      <div class="author">
        <p>작성자: {{ hotplace.writer }}</p>
      </div>

      <div class="address">
        <p>{{ hotplace.addr1 }}</p>
      </div>

      <div class="actions">
        <button @click="goModify">수정</button>
        <button @click="deleteHotplace">삭제</button>
        <button @click="goList">목록으로</button>
      </div>
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
