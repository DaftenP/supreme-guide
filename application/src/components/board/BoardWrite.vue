<script setup>
import BoardFormItem from "@/components/item/BoardFormItem.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { postArticle } from "@/api/board";
import { useUserStore } from "@/stores/userStore";
import authClient from "@/api/authClient";
const userStore = useUserStore();
const board = ref({});

const router = useRouter();
const writeArticle = async (qnaData) => {
  // postArticle(
  //   board.value,
  //   // success
  //   (resp) => {
  //     alert("질문 등록이 완료되었습니다.");
  //     console.log(resp.data);
  //     goList();
  //   },
  //   // fail
  //   (err) => {
  //     alert("질문 등록에 실패하였습니다.");
  //     console.log(err);
  //   }
  // );
  try {
    const res = await authClient({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/regist`,
      data: {
        qna: qnaData,
      },
    });
    alert("질문이 등록되었습니다.");
    console.log(res.qna.data);
    goList();
  } catch (error) {
    alert("질문 등록에 실패하였습니다.");
    console.log(error);
  }
};

const goList = () => {
  router.push({
    name: "BoardList",
  });
};

onMounted(() => {
  // TODO: 로그인 구현 시 로그인 정보로 부터 작성자 아이디 불러오기
  board.value.board_writer = userStore.userId;
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
