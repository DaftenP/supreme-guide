<script setup>
import BoardFormItem from "@/components/item/BoardFormItem.vue";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getArticle } from "@/api/board";
import { putArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const board = ref({});

onMounted(() => {
  const board_id = route.params.board_id;
  getArticle(
    board_id,
    (resp) => {
      board.value = resp.data;
    },
    (err) => {
      console.log(err);
    }
  );
  console.log(board.value);
});

const modifyArticle = () => {
  putArticle(
    board.value,
    // success
    (resp) => {
      alert("질문 수정이 완료 되었습니다.");
      goDetail(board.value.board_id);
    },
    // fail
    (err) => {
      alert("질문 수정에 실패 하였습니다.");
    }
  );
};

const goDetail = (id) => {
  router.push({
    name: "BoardDetail",
    params: {
      board_id: id,
    },
  });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글수정</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <BoardFormItem
          type="modify"
          :board="board"
          @evt-process="modifyArticle" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
