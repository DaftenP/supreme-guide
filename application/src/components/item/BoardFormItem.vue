<script setup>
import { ref } from "vue";
import { getArticle } from "@/api/board";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const props = defineProps({
  type: String,
  board: Object,
});

const emit = defineEmits(["evtProcess"]);

const refTitle = ref(null);
const refContent = ref(null);
const refWriter = ref(null);

const validate = () => {
  let isValid = true;
  let errMsg = "";

  !props.board.board_title
    ? ((isValid = false),
      (errMsg = "제목을 입력해주세요."),
      refTitle.value.focus())
    : !props.board.board_content
    ? ((isValid = false),
      (errMsg = "내용을 입력해주세요."),
      refContent.value.focus())
    : !props.board.board_writer
    ? ((isValid = false),
      (errMsg = "작성자를 입력해주세요."),
      refWriter.value.focus())
    : (isValid = true);
  if (!isValid) {
    alert(errMsg);
  } else {
    emit("evtProcess");
  }
};

const goList = () => {
  router.push({
    name: "BoardList",
  });
};
</script>

<template>
  <div class="mb-3">
    <label class="form-label" for="title">제목</label>
    <input
      class="form-control"
      type="text"
      id="title"
      name="title"
      v-model="board.board_title"
      ref="refTitle" />

    <label class="form-label pt-3" for="writer">글쓴이</label>
    <input
      class="form-control"
      type="text"
      id="writer"
      name="writer"
      v-model="board.board_writer"
      ref="refWriter"
      disabled />

    <label class="form-label pt-3" for="content">내용</label>
    <textarea
      class="form-control"
      id="content"
      name="content"
      rows="5"
      v-model="board.board_content"
      ref="refContent">
    </textarea>

    <div class="d-flex justify-content-end mt-4">
      <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="validate">
        {{ type === "create" ? "등록" : "수정" }}
      </button>
      <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="goList">
        목록
      </button>
    </div>
  </div>
</template>

<style scoped></style>
