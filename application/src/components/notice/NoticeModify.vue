<script setup>
import NoticeFormItem from "@/components/item/NoticeFormItem.vue";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";
import Cookies from "vue-cookies";

const router = useRouter();
const route = useRoute();

const notice = ref({});
const noticeId = route.params.noticeId;

onMounted(async () => {
    try {
        const res = await axios({
            method: "get",
            url: `${import.meta.env.VITE_API_BASE_URL}/notice/view/${noticeId}`
        })
        notice.value = res.data;
    } catch (error) {
        console.log(error);
        alert("문제가 발생했습니다.");
    }
})

const modifyNotice = async () => {
    try {
        const token = Cookies.get("accessToken");
        const res = await axios({
            method: "put",
            url: `${import.meta.env.VITE_API_BASE_URL}/notice/${noticeId}`,
            data: notice.value,
            headers: {
                Authorization: `Bearer ${token}`,
            }
            
        })
        console.log(res.data);
        alert("공지사항 수정이 완료되었습니다.");
        goDetail(noticeId);
    } catch (error) {
        console.log(error);
        alert("작성자만 글을 수정할 수 있습니다.");
    }
}

const goDetail = (id) => {
    router.push({
        name: "NoticeDetail",
        params: {
            noticeId: id,
        }
    })
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">공지사항 수정</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <NoticeFormItem
          type="modify"
          :notice="notice"
          @evt-process="modifyNotice" />
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>