<script setup>
import { ref, onBeforeMount, computed } from "vue";
import { useRouter } from "vue-router";
import { getAllArticle } from "@/api/board";

const router = useRouter();
const qnas = ref([]);
const key = ref("");
const word = ref("");

const movePage = () => {
  router.push({ name: "QnaWrite" });
};

onBeforeMount(() => {
  getAllArticle(
    {},
    (resp) => {
      console.log(resp);
      if (resp.status == 200) {
        qnas.value = resp.data;
        qnas.value.filter((qna) => {
          qna.computed_date = computed(() => {
            const dateString = qna.qnaCreatetime;
            const date = new Date(dateString);
            return date.toLocaleString();
          });
        });
      } else {
        alert("문제가 발생했습니다.");
      }
    },
    (err) => {
      alert("문제가 발생했습니다.", err);
    }
  );
});

const searchArticle = () => {
  getAllArticle(
    { key: key.value, word: word.value },
    (resp) => {
      console.log(resp);
      if (resp.status == 200) {
        boards.value = resp.data;
      } else {
        alert("문제가 발생했습니다.");
      }
    },
    (err) => {
      alert("문제가 발생했습니다.", err);
    }
  );
};

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
    <div class="row justify-content-center">
      <div class="col-lg-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">Q&A 목록</mark>
        </h2>
      </div>
      <div class="col-lg-12">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button
              type="button"
              class="btn btn-outline-primary btn-sm"
              @click="movePage">
              질문 등록
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <div class="input-group input-group-sm">
                <select class="form-select" v-model="key">
                  <option value="qna_title">제목</option>
                  <option value="qna_content">내용</option>
                  <option value="qna_writer">작성자</option>
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
              v-for="(qna, index) in qnas"
              :index="index"
              :qna="qna"
              :key="qna.qnaId">
              <td>{{ qna.qnaId }}</td>
              <td>
                <a href="#" @click="goDetail(qna.qnaId)">{{ qna.qnaTitle }}</a>
              </td>
              <td>{{ qna.qnaView }}</td>
              <td>{{ qna.qnaWriter }}</td>
              <td>{{ qna.qnaCreateDate }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
