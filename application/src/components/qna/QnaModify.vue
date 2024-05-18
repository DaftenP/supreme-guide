<script setup>
import QnaFormItem from "@/components/item/QnaFormItem.vue";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getArticle } from "@/api/board";
import { putArticle } from "@/api/board";
import noAuthClient from "@/api/noAuthClient";
import authClient from "@/api/authClient";
import axios from "axios";
import Cookies from "vue-cookies";

const router = useRouter();
const route = useRoute();

const qna = ref({});

const qnaId = route.params.qnaId;

// qna데이터 가져오기
onMounted( async () => {
    try {
        const res = await noAuthClient({
            method: "get",
            url: `${import.meta.env.VITE_API_BASE_URL}/qna/view/${qnaId}`
        }) 
        qna.value = res.data;
    } catch (error) {
        console.log(error);
        alert("문제가 발생하였습니다.")
    }
});

const modifyArticle = async () => {
    try {
        const token = Cookies.get("accessToken");
        const res = await axios({
            method: "put",
            url: `${import.meta.env.VITE_API_BASE_URL}/qna/${qnaId}`,
            data: qna.value,
            
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
        console.log(res.data);
        alert("질문 수정이 완료 되었습니다.");
        goDetail(qnaId);
    } catch (error) {
        console.log(error);
        alert("작성자만 글을 삭제할 수 있습니다.")
    }
};

const goDetail = (id) => {
  router.push({
    name: "QnaDetail",
    params: {
      qnaId: id,
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
        <QnaFormItem
          type="modify"
          :qna="qna"
          @evt-process="modifyArticle" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
