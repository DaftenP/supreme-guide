<script setup>
import QnaFormItem from "@/components/item/QnaFormItem.vue";
import axios from "axios";
import { ref, onMounted, reactive, onUpdated } from "vue";
import { useRouter } from "vue-router";

import { useUserStore } from "@/stores/userStore";
import authClient from "@/api/authClient";
const userStore = useUserStore();

// data
const qna = reactive({
  qnaTitle: "",
  qnaWriter: "",
  qnaContent: "",
  list: [],
});

// LifeCycle
onUpdated(() => {
  console.log(`정보: ${JSON.stringify(qna)}`);
});

const router = useRouter();
const writeArticle = async () => {
  try {
    const res = await authClient({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/qna/regist`,
      data: qna,
    });
    alert("질문이 등록되었습니다.");
    console.log(res.data);
    goList();
    qna.value = res.data;
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
  qna.qnaWriter = userStore.userId;
});
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <!-- <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글쓰기</mark>
        </h2> -->
      </div>
      <div class="col-lg-10 text-start">
        <QnaFormItem type="create" :qna="qna" @evt-process="writeArticle" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
