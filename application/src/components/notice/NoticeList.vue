<script setup>
import { ref, onBeforeMount, computed } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import Cookies from "vue-cookies";

const router = useRouter();
const key = ref("");
const word = ref("");
const notices = ref([]);
const notice = ref({});
// const noticeId = ref(0);

const movePage = () => {
    router.push({ name: "NoticeWrite"});
}

onBeforeMount( async () => {
    try {
        const res = await axios({
            method: "get",
            url: `${import.meta.env.VITE_API_BASE_URL}/notice/all`
        })
        notices.value = res.data;
        // noticeId.value = res.data.noticeId;
        console.log(res.data);
    } catch (error) {
        console.log(error);
        alert("리스트를 불러오는 데 문제가 발생했습니다.");
    }
})

const searchNotice = async () => {
    try {
        const res = await axios({
            method: "get",
            url: `${import.meta.env.VITE_API_BASE_URL}/notice/view/${noticeId}`,
        })
        notice.value = res.data;
    } catch(error) {
        console.log(error);
        alert("문제가 발생했습니다.");
    }
}

const goDetail = (id) => {
    console.log(id);
    router.push({
        name: "NoticeDetail",
        params: {
            noticeId: id,
        }
    })
}

// searchArticle 구현

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">Notice</mark>
        </h2>
      </div>
      <div class="col-lg-12">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button
              type="button"
              class="btn btn-outline-primary btn-sm"
              @click="movePage">
              공지사항 등록
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <div class="input-group input-group-sm">
                <select class="form-select" v-model="key">
                  <option value="noticeTitle">제목</option>
                  <option value="noticeContent">내용</option>
                  <option value="noticeWriter">작성자</option>
                </select>
                <input
                  type="text"
                  class="form-control"
                  placeholder="검색어"
                  v-model="word" />
                <button
                  class="btn btn-dark"
                  type="button"
                  @click="searchArticle">
                  검색
                </button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">조회수</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="text-center"
              v-for="(notice, index) in notices"
              :index="index"
              :notice="notice"
              :key="notice.noticeId">
              <td>{{ notice.noticeId }}</td>
              <td>
                <a @click="goDetail(notice.noticeId)">{{ notice.noticeTitle }}</a>
              </td>
              <td>{{ notice.noticeView }}</td>
              <td>{{ notice.noticeWriter }}</td>
              <td>{{ notice.noticeCreateDate }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>




<style scoped>

</style>