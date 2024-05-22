<script setup>
import { ref, onBeforeMount, computed } from "vue";
import { useRouter } from "vue-router";
import noAuthClient from "@/api/noAuthClient";
import { useUserStore } from "@/stores/userStore";
import AOS from "aos";
import "aos/dist/aos.css";

AOS.init({
  duration: 1000,
});

const userStore = useUserStore();
const router = useRouter();
const qnas = ref([]);
const key = ref("");
const word = ref("");
const qna = ref({});
const currentPage = ref(1);
const totalPages = ref(0);
const searchCondition = ref({
  countPerPage: 9,
  key: "",
  word: "",
  currentPage: 1,
  offset: 0,
  limit: true,
});

const movePage = () => {
  router.push({ name: "QnaWrite" });
};

const changePage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    searchCondition.value.currentPage = page;
    currentPage.value = page; // 현재 페이지 업데이트
    fetchNotices();
  }
};

// searchArticle구현
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
};

const fetchNotices = async () => {
  try {
    let url = `${import.meta.env.VITE_API_BASE_URL}/qna/all`;
    if (searchCondition.value.key && searchCondition.value.word) {
      if (searchCondition.value.key === "qnaWriter") {
        searchCondition.value.key = "qna_writer";
      } else if (searchCondition.value.key === "qnaContent") {
        searchCondition.value.key = "qna_content";
      } else if (searchCondition.value.key === "qnaTitle") {
        searchCondition.value.key = "qna_title";
      }
      url += `?key=${searchCondition.value.key}&word=${searchCondition.value.word}`;
    }
    const res = await noAuthClient({
      method: "get",
      url: url,
    });
    console.log(url);

    const startIndex =
      (searchCondition.value.currentPage - 1) *
      searchCondition.value.countPerPage;
    const endIndex = startIndex + searchCondition.value.countPerPage;
    qnas.value = res.data
      .slice(startIndex, endIndex)
      .map((item, index) => ({ ...item, index: index + startIndex + 1 }));
    totalPages.value = Math.ceil(
      res.data.length / searchCondition.value.countPerPage
    );
    console.log(res.data);
  } catch (error) {
    console.log(error);
    alert("리스트를 불러오는 데 문제가 발생했습니다.");
  }
};

onBeforeMount(fetchNotices);

const goDetail = (id) => {
  console.log(id);
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
    <div class="row justify-content-center my-4">
      <div class="col-lg-8">
        <h2 class="text-center font-style">Q&A</h2>
      </div>
      <div class="input-group my-4">
        <select class="form-select" v-model="key">
          <option value="qna_title">제목</option>
          <option value="qna_content">내용</option>
          <option value="qna_writer">작성자</option>
        </select>
        <input
          type="text"
          class="form-control"
          placeholder="검색어를 입력해 주세요"
          v-model="word" />
        <button class="btn btn-dark" type="button" @click="searchArticle">
          <font-awesome-icon
            :icon="['fas', 'magnifying-glass']"
            style="color: #fcfcfc" />
        </button>
      </div>

      <div class="row">
        <div
          class="col-md-4 mb-4"
          v-for="(qna, index) in qnas"
          :key="qna.qnaId"
          :index="index">
          <div
            class="card h-100"
            @click="goDetail(qna.qnaId)"
            :data-aos="'fade-up'">
            <div class="card-body">
              <h5 class="card-title">
                {{ qna.qnaTitle }}
              </h5>

              <!-- {{ qna.qnaContent }} -->
              <br />
              <br />
              <div class="card-text d-flex">
                <p>
                  {{ qna.qnaCreateDate }}
                </p>
              </div>
              <p>
                {{ qna.qnaWriter }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-2 text-start">
      <button
        type="button"
        class="btn btn-sm"
        id="font-small"
        v-if="userStore.userId != ''"
        @click="movePage">
        질문 등록
        <font-awesome-icon
          :icon="['fas', 'arrow-right']"
          style="color: black" />
      </button>
    </div>

    <nav aria-label="Page navigation example" class="mt-4">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <button
            class="page-link"
            @click="changePage(currentPage - 1)"
            aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </button>
        </li>
        <li
          class="page-item"
          v-for="pageNumber in totalPages"
          :key="pageNumber"
          :class="{ active: currentPage === pageNumber }">
          <button class="page-link" @click="changePage(pageNumber)">
            {{ pageNumber }}
          </button>
        </li>
        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
          <button
            class="page-link"
            @click="changePage(currentPage + 1)"
            aria-label="Next">
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

.card {
  position: relative; /* 아이콘을 상대적으로 위치시키기 위해 설정 */
  transition: transform 0.2s;
  cursor: pointer;
  max-width: 300px;
}

.card-container {
  /* max-width: 800px; 원하는 최대 너비 설정 */
  /* margin: 0 auto; 가운데 정렬 */
  max-width: 1200px;
  flex-wrap: wrap;
  display: flex;
  gap: 1rem;
}

.font-style {
  font-family: "CustomFont";
  font-size: 50px;
}

#font-small {
  font-family: "CustomFont3";
  font-size: 20px;
}

.container {
  max-width: 1000px;
}

.card {
  transition: transform 0.2s;
  cursor: pointer;
}

.card:hover {
  transform: translateY(-5px);
}

.card-icon {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 2.5rem; /* 아이콘 크기 조정 */
  color: #63e6be;
}

.card-title {
  color: #333;
  font-weight: bold;
  font-family: "CustomFont";
  font-size: 20px;
}

.card-title:hover {
  text-decoration: underline;
}
</style>
