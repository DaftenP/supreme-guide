<script setup>
import NoticeFormItem from "@/components/item/NoticeFormItem.vue";
import axios from "axios";
import Cookies from "vue-cookies";
import { ref, onMounted, reactive, onUpdated } from "vue";
import { useRouter } from "vue-router";

import { useUserStore } from "@/stores/userStore";
import authClient from "@/api/authClient";

const userStore = useUserStore();
const router = useRouter();

// data
const notice = reactive({
    noticeTitle: "",
    noticeWriter: "",
    noticeContent: "",
})

const goList = () => {
    router.push({
        name: "NoticeList",
    })
}

// LifeCycle
onUpdated(() => {
    console.log(`정보: ${JSON.stringify(notice)}` );
});

onMounted(() => {
    notice.noticeWriter = userStore.userId;
})

// 글 작성
const writeNotice = async () => {
    try {
        const token = Cookies.get("accessToken");
        const res = await axios({
            method: "post",
            url: `${import.meta.env.VITE_API_BASE_URL}/notice/regist`,
            data: notice,
            headers: {
                Authorization: `Bearer ${token}`,
            }
        });
        alert("공지사항 등록이 완료되었습니다.");
        notice.value = res.data;
        goList();
    } catch (error) {
        console.log(error);
        alert("공지사항 등록에 실패하였습니다.");
    }
}

</script>

<template>
<div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">공지사항 등록</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <NoticeFormItem
          type="create"
          :notice="notice"
          @evt-process="writeNotice" />
      </div>
    </div>
  </div>
  
</template>


<style scoped>

</style>