<script setup>
import { ref, onBeforeMount, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import Cookies from "vue-cookies";
import axios from "axios";

const route = useRoute();
const router = useRouter();

const notice = ref({});

const noticeId = route.params.noticeId;
console.log("detail     "+noticeId);

const goModify = () => {
    router.push({
        name: "NoticeModify",
        params: { noticeId: notice.value.noticeId },
    })
}

// 삭제
const deleteNotice = async () => {
    try {
        const token = Cookies.get("accessToken");
        const res = await axios({
            method: "delete",
            url: `${import.meta.env.VITE_API_BASE_URL}/notice/${noticeId}`,
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
        alert("삭제에 성공하였습니다.");
        goList();
    } catch (error) {
        console.log(error);
        alert("삭제 권한이 없습니다.");
    }
}

// 상세 조회
onBeforeMount( async () => {
    try {
        const res = await axios({
            method: "get",
            url: `${import.meta.env.VITE_API_BASE_URL}/notice/view/${noticeId}`,
        })
        console.log(res.data);
        notice.value = res.data;
    } catch (error) {
        console.log(error);
        alert("공지사항 조회에 문제가 발생하였습니다.");
    }
})

const goList = () => {
    router.push({
        name: "NoticeList",
    })
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">공지사항 상세 페이지</mark>
        </h2>
      </div>
      <div class="col-lg-12">
        <!-- <h1 class="">{{ notice.noticeTitle }}</h1> -->
        <div class="d-flex justify-content-between">
          <div>
            <label for="writer">작성자 :</label>
            {{ notice.noticeWriter }}
          </div>

          <div>
            {{ notice.noticeCreateDate }}

            <label class="ms-1" for="view">조회 </label>
            {{ notice.noticeView }}
          </div>
        </div>
        <hr />
        <span v-html="notice.noticeContent"></span>
        <hr />
        <div class="d-flex justify-content-end">
          <a
            class="btn btn-outline-primary ms-2 pe-4 ps-4"
            @click.prevent="goModify"
            >수정</a
          >
          <a
            class="btn btn-outline-danger ms-2 pe-4 ps-4"
            @click="deleteNotice()"
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

<style scoped>

</style>