<script setup>
import { ref, onBeforeMount, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import noAuthClient from "@/api/noAuthClient";
import Cookies from "vue-cookies";
import axios from "axios";
import { useUserStore } from "@/stores/userStore";

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
    const token = Cookies.get("accessToken");
    const res = await axios({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/${qnaId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
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
    const token = Cookies.get("accessToken");
    const res = await axios({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/comment/regist`,
      data: {
        articleId: qnaId,
        content: newComment.commentContent,
      },
      headers: {
        Authorization: `Bearer ${token}`,
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
      const token = Cookies.get("accessToken");
      const res = await axios({
        method: "delete",
        url :`${import.meta.env.VITE_API_BASE_URL}/qna/comment/${commentId}`,
        headers: {
          "Authorization": `Bearer ${token}`,
        }
      })
      alert("댓글을 삭제하였습니다.")
      await fetchComments(); // 댓글 목록 갱신
  } catch (error) {
    console.log(error);
    alert("문제가 발생했습니다.");
  }
}

// 게시글 상세 조회 및 댓글 목록 조회
onBeforeMount(async () => {
  await fetchComments();
});
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">질문 상세 페이지</mark>
        </h2>
      </div>
      <div class="col-lg-12">
        <h1>{{ qna.qnaTitle }}</h1>
        <div class="d-flex justify-content-between">
          <div>
            <label for="writer">작성자 :</label>
            {{ qna.qnaWriter }}
          </div>
          <div>
            {{ qna.qnaCreateDate }}
            <label class="ms-1" for="view">조회 </label>
            {{ qna.qnaView }}
          </div>
        </div>
        <hr />
        <span v-html="qna.qnaContent"></span>
        <hr />
        <div class="d-flex justify-content-end">
          <a
            class="btn btn-outline-primary ms-2 pe-4 ps-4"
            href="#"
            @click.prevent="goModify"
            >수정</a
          >
          <a
            class="btn btn-outline-danger ms-2 pe-4 ps-4"
            @click="deleteQna"
            >삭제</a
          >
          <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="goList">
            목록
          </button>
        </div>
        <h3>댓글</h3>
        <form @submit.prevent="registerComment">
          <div class="mb-3">
            <label for="newComment" class="form-label">댓글 작성</label>
            <textarea
              class="form-control"
              id="newComment"
              rows="3"
              v-model="newComment.commentContent"
            ></textarea>
          </div>
          <button type="submit" class="btn btn-primary">댓글 등록</button>
        </form>
        <hr/>
        <ul class="list-group mb-3">
          <li
            class="list-group-item"
            v-for="comment in comments"
            :key="comment.qnaCommentId"
          >
            <strong>{{ comment.userId }}:</strong> {{ comment.content }}
        
        <button
          v-if="comment.userId === userStore.userId"
          class="btn btn-sm btn-outline-danger ms-2"
          @click="deleteComment(comment.id)"
        >
          삭제
        </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
