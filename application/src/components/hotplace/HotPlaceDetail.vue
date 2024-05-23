<script setup>
import { ref, onBeforeMount, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import noAuthClient from "@/api/noAuthClient";
import { useUserStore } from "@/stores/userStore";
import { useMapStore } from "@/stores/map";
import authClient from "@/api/authClient";
import MapComponent from "@/components/commons/MapComponent.vue";
import ImageFrame from "@/components/item/ImageFrame.vue"; // 추가

const IMAGE_URL = import.meta.env.VITE_IMAGE_BASE_URL;

const userStore = useUserStore();
const mapStore = useMapStore();
const route = useRoute();
const router = useRouter();

const hotplace = ref({});
const comments = ref([]);

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

// 핫플레이스 삭제
const deleteHotplace = async () => {
  try {
    await authClient({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/${hotplaceId}`,
    });
    alert("삭제가 완료되었습니다.");
    goList();
  } catch (error) {
    console.log(error);
    alert("작성자만 글 삭제가 가능합니다.");
  }
};

// 핫플레이스 정보 가져오기
const fetchHotPlaces = async () => {
  try {
    console.log(hotplaceId);
    const res = await noAuthClient({
      method: "get",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/view/${hotplaceId}`,
    });
    mapStore.lat = res.data.latitude;
    mapStore.lng = res.data.longitude;

    console.log(mapStore.lat);
    console.log(mapStore.lng);
    mapStore.attractionInfo = res.data;
    hotplace.value = res.data;
    hotplace.value.image = `/${IMAGE_URL}/${hotplace.value.image}`;
    comments.value = res.data.list;
  } catch (error) {
    console.log(error);
    alert("정보를 가져오는데 문제가 발생했습니다.");
  }
};

// 댓글 삭제
const deleteComment = async (commentId) => {
  try {
    const res = await authClient({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/comment/${commentId}`,
    });
    alert("댓글을 삭제하였습니다.");
    await fetchHotPlaces(); // 댓글 목록 갱신
  } catch (error) {
    console.log(error);
    alert("문제가 발생했습니다.");
  }
};

const goList = () => {
  router.push({
    name: "HotplaceList",
  });
};

const registComment = async () => {
  try {
    await authClient({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/hotplace/comment/regist`,
      data: {
        articleId: hotplaceId,
        content: newComment.commentContent,
      },
    });
    alert("댓글 등록에 성공했습니다.");
    newComment.commentContent = "";
    await fetchHotPlaces();
  } catch (error) {
    console.log(error);
    alert("댓글을 추가하는데 문제가 발생했습니다.");
  }
};

onBeforeMount(async () => {
  await fetchHotPlaces();
});
</script>

<template>
  <div class="blog-post">
    <div class="col-lg-8">
      <h2 class="blog-post-title font-style">{{ hotplace.hotplaceName }}</h2>
      <div class="info">
        <span>작성자: {{ hotplace.writer }}</span>
        <span>주소: {{ hotplace.addr1 }}</span>
      </div>
    </div>
    <div class="container">
      <div class="left-panel">
        <MapComponent></MapComponent>
      </div>
      <div class="right-panel form-container">
        <div class="image-frame-container">
          <ImageFrame :imageSrc="hotplace.image" />
        </div>

        <div class="actions">
          <!-- <button @click.prevent="goModify">수정</button> -->
          <button @click="deleteHotplace">삭제</button>
          <button @click="goList">목록으로</button>
        </div>
        <div class="comments">
          <h2>댓글</h2>
          <form @submit.prevent="registComment">
            <div class="comment-form">
              <textarea
                v-model="newComment.commentContent"
                placeholder="댓글을 입력하세요"></textarea>
              <button type="submit">댓글 추가</button>
            </div>
          </form>
          <ul>
            <li v-for="comment in comments" :key="comment.hotplaceCommentId">
              <div class="comment">
                <span
                  ><strong>{{ comment.userId }}:</strong>
                  {{ comment.content }}</span
                >
                <button
                  v-if="comment.userId === userStore.userId"
                  @click="deleteComment(comment.id)">
                  삭제
                </button>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#div-map {
  height: 500px !important;
  width: 500px !important;
}
.container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 20px;
}

.form-container {
  background-color: #f9f9f9; /* 공지사항 스타일 */
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
}

.left-panel,
.right-panel {
  width: 48%;
}

h1 {
  font-size: 24px;
  margin-bottom: 10px;
}

.actions button {
  margin-right: 10px;
  padding: 10px;
  font-size: 14px;
}

.comment {
  display: flex;
  align-items: center;
}

.comment span {
  margin-right: 10px; /* content와 삭제 버튼 사이의 간격 */
}

.comments-section {
  margin-top: 20px;
}

.comments-section h2 {
  font-size: 20px;
  margin-bottom: 10px;
}

.comments-section ul {
  list-style: none;
  padding: 0;
}

.comments-section li {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.comment-form {
  margin-top: 10px;
}

.comment-form textarea {
  width: 100%;
  height: 80px;
  margin-bottom: 5px;
  padding: 10px;
  border: 1px solid black;
}

.comment-form button {
  padding: 10px;
  font-size: 14px;
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

.font-style {
  font-family: "CustomFont";
  font-size: 40px;
}

.info {
  font-size: 14px;
  color: #555;
  margin-bottom: 20px;
}

.info span {
  margin-right: 15px;
}

.image-frame-container {
  display: flex;
  justify-content: center;
}
</style>
