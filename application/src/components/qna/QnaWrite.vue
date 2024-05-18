<script setup>
import QnaFormItem from "@/components/item/QnaFormItem.vue";
import axios from "axios";
import { ref, onMounted, reactive, onUpdated } from "vue";
import { useRouter } from "vue-router";

import { useUserStore } from "@/stores/userStore";
import authClient from "@/api/authClient";
import Cookies from "vue-cookies";
const userStore = useUserStore();

// data
const qna = reactive({
    qnaTitle: "",
    qnaWriter:"",
    qnaContent: "",
    list: [],
});

// LifeCycle
onUpdated(() => {
    console.log(`정보: ${JSON.stringify(qna)}` );
});

const router = useRouter();
const writeArticle = async () => {
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
    const token = Cookies.get("accessToken");
    const res = await axios({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/regist`,
      data: {
        qna: qna
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    alert("질문이 등록되었습니다.");
    console.log(res.data);
    goList();
  } catch (error) {
    alert("질문 등록에 실패하였습니다.");
    console.log(error);
  }
};

const goList = () => {
  router.push({
    name: "QnaList",
  });
};

onMounted(() => {
  qna.qna_writer = userStore.userId;
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
        <QnaFormItem
          type="create"
          :qna="qna"
          @evt-process="writeArticle" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
