<script setup>
import { ref, onBeforeMount, computed } from "vue";
import { useRouter } from "vue-router";
import { getAllArticle } from "@/api/board";

const router = useRouter();
const boards = ref([]);
const key = ref("");
const word = ref("");

const movePage = () => {
  router.push({ name: "BoardWrite" });
};

onBeforeMount(() => {
  getAllArticle(
    {},
    (resp) => {
      console.log(resp);
      if (resp.status == 200) {
        boards.value = resp.data;
        boards.value.filter((board) => {
          board.computed_date = computed(() => {
            return board.board_created_time.replace(
              /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):\d{2}$/,
              "$1.$2.$3 $4:$5"
            );
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
  router.push({
    name: "BoardDetail",
    params: {
      board_id: id,
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
                  <option value="board_title">제목</option>
                  <option value="board_content">내용</option>
                  <option value="board_writer">작성자</option>
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
              v-for="(board, index) in boards"
              :index="index"
              :board="board"
              :key="board.boardId">
              <td>{{ board.board_id }}</td>
              <td>
                <a href="#" @click="goDetail(board.board_id)">{{
                  board.board_title
                }}</a>
              </td>
              <td>{{ board.board_view }}</td>
              <td>{{ board.board_writer }}</td>
              <td>{{ board.computed_date }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
