<script setup>
import { ref, onBeforeMount, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import noAuthClient from "@/api/noAuthClient";
import { useUserStore } from "@/stores/userStore";
import authClient from "@/api/authClient";

const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

const qna = ref({});
const comments = ref([]);

// data
const newComment = reactive({
  commentContent: "",
});

const qnaId = route.params.qnaId;
const goModify = () => {
  router.push({
    name: "QnaModify",
    params: { qnaId: qna.value.qnaId },
  });
};

const deleteQna = async () => {
  try {
    const res = await authClient({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/${qnaId}`,
    });
    alert("삭제에 성공하였습니다.");
    goList();
  } catch (error) {
    console.log(error);
    alert("작성자만 글을 삭제할 수 있습니다.");
  }
};

const goList = () => {
  router.push({
    name: "QnaList",
  });
};

const registerComment = async () => {
  try {
    const res = await authClient({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/comment/regist`,
      data: {
        articleId: qnaId,
        content: newComment.commentContent,
      },
    });
    alert("댓글 등록에 성공하였습니다.");
    newComment.commentContent = "";
    await fetchComments(); // 댓글 목록 갱신
  } catch (error) {
    console.log(error);
    alert("댓글 등록에 실패하였습니다.");
  }
};

const fetchComments = async () => {
  try {
    const res = await noAuthClient({
      method: "get",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/view/${qnaId}`,
    });
    console.log(res.data);
    qna.value = res.data;
    comments.value = res.data.list;
  } catch (error) {
    console.log(error);
    alert("문제가 발생했습니다.");
  }
};

// 댓글 삭제
const deleteComment = async (commentId) => {
  try {
    const res = await authClient({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/comment/${commentId}`,
    });
    alert("댓글을 삭제하였습니다.");
    await fetchComments(); // 댓글 목록 갱신
  } catch (error) {
    console.log(error);
    alert("문제가 발생했습니다.");
  }
};

// 게시글 상세 조회 및 댓글 목록 조회
onBeforeMount(async () => {
  await fetchComments();
});
</script>

<template>
  <div class="container">
    <div class="content">
      <div class="title">
        <h1>{{ qna.qnaTitle }}</h1>
      </div>
      <div class="info">
        <span>작성자: {{ qna.qnaWriter }}</span>
        <span>{{ qna.qnaCreateDate }}</span>
        <span>조회: {{ qna.qnaView }}</span>
      </div>
      <!-- <font-awesome-icon 
      :icon="['fas', 'ellipsis-vertical']" style="color: #d3d5d9;"
      @click="toggleActions" /> -->
      <div class="body">
        <p v-html="qna.qnaContent"></p>
      </div>
      <div class="actions">
        <button
          v-if="qna.qnaWriter === userStore.userId"
          @click.prevent="goModify">
          수정
        </button>
        <button v-if="qna.qnaWriter === userStore.userId" @click="deleteQna">
          삭제
        </button>
        <button @click="goList">목록</button>
      </div>

      <div class="divider"></div>
      <div class="comments">
        <h3>댓글</h3>
        <form @submit.prevent="registerComment">
          <textarea
            v-model="newComment.commentContent"
            placeholder="댓글 작성"></textarea>
          <button type="submit">댓글 등록</button>
        </form>
        <ul>
          <li v-for="comment in comments" :key="comment.qnaCommentId">
            <div class="comment">
              <span
                :class="comment.harmful ? 'blur-sm cursor-pointer' : ''"
                @click="comment.harmful = false"
                class="select-none transition duration-500 ease-in-out">
                <strong>{{ comment.userId }}:</strong>
                {{ comment.content }}
              </span>
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
</template>

<style scoped>
.container {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: "Arial", sans-serif;
}

.content {
  background-color: #fff;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
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
