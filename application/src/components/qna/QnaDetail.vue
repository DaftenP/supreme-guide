<script setup>
import { ref, onBeforeMount, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getArticle, deleteArticle } from "@/api/board";

const route = useRoute();
const router = useRouter();

const qna = ref({});

const goModify = () => {
  router.push({
    name: "QnaModify",
    params: { qnaId: qna.value.qnaId },
  });
};

const deleteBoard = () => {
  if (confirm("정말로 삭제하시겠습니까?")) {
    const success = (response) => {
      alert("삭제되었습니다.");
      goList();
    };

    const fail = (error) => {
      alert("문제가 발생했습니다", error);
    };

    deleteArticle(boardId, success, fail);
  }
};

const goList = () => {
  router.push({
    name: "QnaList",
  });
};

const qnaId = route.params.qnaId;

// 게시글 상세 조회
onBeforeMount( async () => {
  // try {
    
  // } catch (error) {
  //   consle.log(error);
  //   alert("문제가 발생했습니다.")
  // }
  getArticle(
    qnaId,
    (response) => {
      const b = response.data;
      qna.value = b;
      qna.value.computed_content = computed(() => {
        return qna.value.board_content.replaceAll(`\n`, `<br/>`);
      });
      qna.value.computed_date = computed(() => {
        return qna.value.qna_created_time.replace(
          /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):\d{2}$/,
          "$1.$2.$3 $4:$5"
        );
      });
    },
    (err) => {
      alert("문제가 발생했습니다.");
      console.log(err);
    }
  );
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
        <h1 class="">{{ qna.qnaTitle }}</h1>
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
            @click="deleteBoard()"
            >삭제</a
          >
          <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="goList">
            목록
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
