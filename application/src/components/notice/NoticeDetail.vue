<script setup>
import { ref, onBeforeMount, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import noAuthClient from "@/api/noAuthClient";
import authClient from "@/api/authClient";

const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

const notice = ref({});

const noticeId = route.params.noticeId;
console.log("detail     " + noticeId);

const goModify = () => {
  router.push({
    name: "NoticeModify",
    params: { noticeId: notice.value.noticeId },
  });
};

// 삭제
const deleteNotice = async () => {
  try {
    const res = await authClient({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/notice/${noticeId}`,
    });
    alert("삭제에 성공하였습니다.");
    goList();
  } catch (error) {
    console.log(error);
    alert("삭제 권한이 없습니다.");
  }
};

// 상세 조회
onBeforeMount(async () => {
  try {
    const res = await noAuthClient({
      method: "get",
      url: `${import.meta.env.VITE_API_BASE_URL}/notice/view/${noticeId}`,
    });
    console.log(res.data);
    notice.value = res.data;
  } catch (error) {
    console.log(error);
    alert("공지사항 조회에 문제가 발생하였습니다.");
  }
});

const goList = () => {
  router.push({
    name: "NoticeList",
  });
};
</script>

<template>
  <div class="container">
    <div class="content" >
      <div class="title">
        <h1>{{ notice.noticeTitle }}</h1>
      </div>
      <div class="info">
        <span>작성자: {{ notice.noticeWriter }}</span>
        <span>{{ notice.noticeCreateDate }}</span>
        <span>조회: {{ notice.noticeView }}</span>
      </div>
      <!-- <font-awesome-icon 
      :icon="['fas', 'ellipsis-vertical']" style="color: #d3d5d9;"
      @click="toggleActions" /> -->
      <div class="body">
        <p v-html="notice.noticeContent"></p>
      </div>
      <div class="actions">
      <button v-if="notice.noticeWriter === userStore.userId" @click.prevent="goModify">수정</button>
      <button v-if="notice.noticeWriter === userStore.userId" @click="deleteNotice">삭제</button>
      <button @click="goList">목록</button>
    </div>

    <div class="divider"></div>
    </div>
  </div>
</template>
<style scoped>
.container {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Arial', sans-serif;
}

.content {
  background-color: #fff;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.title h1 {
  font-size: 24px;
  margin-bottom: 10px;
}

.info {
  font-size: 14px;
  color: #555;
  margin-bottom: 20px;
}

.info span {
  margin-right: 15px;
}

.body p {
  font-size: 16px;
  line-height: 1.6;
  white-space: pre-line;
}

.actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.actions button {
  margin-left: 10px;
  padding: 5px 10px;
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.actions button:hover {
  background-color: #f0f0f0;
}

.comments {
  margin-top: 30px;
}

.comments h3 {
  font-size: 20px;
  margin-bottom: 10px;
}

.comments form {
  margin-bottom: 20px;
}

.comments textarea {
  width: 100%;
  height: 60px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
}

.comments textarea:focus {
  outline: none;
  border-color: #aaa;
}

.comments button {
  padding: 5px 10px;
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.comments button:hover {
  background-color: #f0f0f0;
}

.comments ul {
  list-style: none;
  padding: 0;
}

.comments li {
  padding: 10px;
}

.divider {
  height: 2px;
  background: linear-gradient(to right, #ff7e5f, #feb47b);
  margin: 30px 0;
}

</style>