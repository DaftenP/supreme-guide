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
    <div class="row justify-content-center my-4">
      <div class="col-lg-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <h1 class="font-title">{{ qna.qnaTitle }}</h1>
        </h2>
      </div>
      <div>
        <div class="qna-header d-flex justify-content-between">
          <div id="font-small">
            <label for="writer">작성자 :</label>
            {{ qna.qnaWriter }}
          </div>
          <div>
            {{ qna.qnaCreateDate }}
            <label class="ms-1" for="view">조회 </label>
            {{ qna.qnaView }}
          </div>
        </div>
        <div class="qna-body">
          <span v-html="qna.qnaContent" class="qna-content"></span>
        </div>
        <div class="qna-footer d-flex justify-content-end">
          <a
            class="btn btn-outline-primary ms-2"
            v-if="qna.qnaWriter === userStore.userId"
            href="#"
            @click.prevent="goModify"
            >수정</a
          >
          <a
            class="btn btn-danger ms-2"
            v-if="qna.qnaWriter === userStore.userId"
            @click="deleteQna"
            ><font-awesome-icon :icon="['fas', 'trash']" style="color: #ffffff"
          /></a>
          <button class="btn btn-outline-dark ms-2" @click="goList">
            목록
          </button>
        </div>

        <h3 class="mt-4">댓글</h3>
        <form @submit.prevent="registerComment" class="mb-3">
          <div class="mb-3">
            <label for="newComment" class="form-label">댓글 작성</label>
            <textarea
              class="form-control"
              id="newComment"
              rows="3"
              v-model="newComment.commentContent"></textarea>
          </div>
          <button type="submit" class="btn btn-primary">댓글 등록</button>
        </form>
        <hr />
        <ul class="list-group">
          <li
            class="list-group-item mb-2"
            v-for="comment in comments"
            :key="comment.qnaCommentId">
            <div class="d-flex justify-content-between align-items-center">
              <div>
                <strong>{{ comment.userId }}:</strong> {{ comment.content }}
              </div>
              <div>
                <button
                  v-if="comment.userId === userStore.userId"
                  class="btn btn-sm btn-danger rounded-pill px-3 py-1"
                  @click="deleteComment(comment.id)">
                  <font-awesome-icon
                    :icon="['fas', 'trash']"
                    style="color: #ffffff" />
                </button>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 1000px;
}

.font-title {
  font-family: "CustomFont";
  font-size: 40px;
}

#font-small {
  font-family: "CustomFont3";
  font-size: 20px;
}

/* 댓글 목록 스타일 */
.list-group {
  max-height: 400px; /* 댓글이 너무 많아지는 것을 방지하기 위해 스크롤 가능한 영역 제한 */
  overflow-y: auto; /* 스크롤 가능한 영역 만들기 */
}

.list-group-item:not(:last-child) {
  border-bottom: 1px solid #dee2e6; /* 마지막 댓글 제외하고 아래 테두리 추가 */
}

.list-group-item {
  border: 1px solid #dee2e6;
  border-radius: 5px;
}

.mb-2 {
  margin-bottom: 0.5rem !important;
}

/* 추가적인 커스텀 스타일 */
.qna-header {
  background-color: #f8f9fa;
  padding: 1rem;
  border-radius: 8px 8px 0 0;
}

.qna-footer {
  background-color: #f8f9fa;
  padding: 1rem;
  border-radius: 0 0 8px 8px;
  display: flex;
  justify-content: flex-end;
}

.qna-body {
  padding: 1rem;
  border: 1px solid #eee;
  background-color: white;
  border-top: none;
  border-bottom: none;
}

/* 댓글 삭제 버튼 스타일 */
.btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}

.btn-danger:hover {
  background-color: #c82333;
  border-color: #bd2130;
}

.btn-danger:focus {
  background-color: #b21f2d;
  border-color: #9c1c23;
  box-shadow: 0 0 0 0.25rem rgba(225, 83, 97, 0.5);
}
</style>
