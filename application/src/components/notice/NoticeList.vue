<script setup>
import { ref, onBeforeMount, computed, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import Cookies from "vue-cookies";

const router = useRouter();
const key = ref("");
const word = ref("");
const notices = ref([]);
const notice = ref({});
const currentPage = ref(1); 
const totalPages = ref(0); 
const searchCondition = ref({
  countPerPage: 5,
  key: "",
  word: "",
  currentPage: 1,
  offset: 0,
  limit: true
});

const movePage = () => {
    router.push({ name: "NoticeWrite"});
}

const changePage = (page) => {
    if (page >= 1 && page <= totalPages.value) {
        searchCondition.value.currentPage = page;
        currentPage.value = page; // 현재 페이지 업데이트
        fetchNotices();
    }
}

const searchArticle = () => {
    // 검색어 입력 상태에서만 검색 조건을 업데이트하도록 추가
    if (word.value !== "") {
        searchCondition.value.key = key.value;
        searchCondition.value.word = word.value;
    } else {
        // 검색어가 입력되지 않은 경우 검색 조건 초기화
        searchCondition.value.key = "";
        searchCondition.value.word = "";
    }
    // 검색 버튼 클릭 시 currentPage를 1로 초기화하여 첫 페이지부터 조회하도록 처리
    searchCondition.value.currentPage = 1;
    fetchNotices();
}


const fetchNotices = async () => {
    try {
      let url = `${import.meta.env.VITE_API_BASE_URL}/notice/all`;
      if (searchCondition.value.key && searchCondition.value.word) {
        if (searchCondition.value.key === "noticeWriter") {
          searchCondition.value.key = "notice_writer";
        } 
        else if (searchCondition.value.key === "noticeContent") {
          searchCondition.value.key = "notice_content";
        }
        else if (searchCondition.value.key === "noticeTitle") {
          searchCondition.value.key = "notice_title";
        }
            url += `?key=${searchCondition.value.key}&word=${searchCondition.value.word}`;
        }
        const res = await axios({
            method: "get",
            url: url,
        })
        console.log(url);

        const startIndex = (searchCondition.value.currentPage - 1) * searchCondition.value.countPerPage;
        const endIndex = startIndex + searchCondition.value.countPerPage;
        notices.value = res.data.slice(startIndex, endIndex).map((item, index) => ({ ...item, index: index + startIndex + 1 }));
        totalPages.value = Math.ceil(res.data.length / searchCondition.value.countPerPage);
        console.log(res.data);
    } catch (error) {
        console.log(error);
        alert("리스트를 불러오는 데 문제가 발생했습니다.");
    }
}


onBeforeMount(fetchNotices);

// const searchNotice = async () => {
//     try {
//         const res = await axios({
//             method: "get",
//             url: `${import.meta.env.VITE_API_BASE_URL}/notice/view/${notice.noticeId}`,
//         })
//         notice.value = res.data;
//     } catch(error) {
//         console.log(error);
//         alert("문제가 발생했습니다.");
//     }
// }

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
              v-if="userStore.userId!=''"
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
    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <!-- 이전 버튼 -->
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <button class="page-link" @click="changePage(currentPage - 1)" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </button>
        </li>
        <!-- 페이지 번호 -->
        <li class="page-item" v-for="pageNumber in totalPages" :key="pageNumber" :class="{ active: currentPage === pageNumber }">
          <button class="page-link" @click="changePage(pageNumber)">{{ pageNumber }}</button>
        </li>
        <!-- 다음 버튼 -->
        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
          <button class="page-link" @click="changePage(currentPage + 1)" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </button>
        </li>
      </ul>
    </nav>
  </div>
</template>




<style scoped>
/* 페이지 처리 스타일 */
.pagination {
    margin-top: 20px;
}
</style>