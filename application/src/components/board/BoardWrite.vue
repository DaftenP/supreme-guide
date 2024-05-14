<script setup>
import BoardFormItem from "@/components/item/BoardFormItem.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { postArticle } from "@/api/board";
const board = ref({});

const router = useRouter();
const writeArticle = () => {
  postArticle(
    board.value,
    // success
    (resp) => {
      alert("질문 등록이 완료되었습니다.");
      console.log(resp.data);
      goList();
    },
    // fail
    (err) => {
      alert("질문 등록에 실패하였습니다.");
      console.log(err);
    }
  );
};

const goList = () => {
  router.push({
    name: "BoardList",
  });
};

onMounted(() => {
  // TODO: 로그인 구현 시 로그인 정보로 부터 작성자 아이디 불러오기
  board.value.board_writer = "ssafy";
});
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글쓰기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <BoardFormItem
          type="create"
          :board="board"
          @evt-process="writeArticle" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
