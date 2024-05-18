<script setup>
import { ref, toRefs } from "vue";
import { getArticle } from "@/api/board";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const props = defineProps({
  type: String,
  qna: Object,
});

const emit = defineEmits(["evtProcess"]);

const { qna } = toRefs(props);

const emitProcess = () => {
  emit("evt-process", qna.value);
};

const refTitle = ref(null);
const refContent = ref(null);
const refWriter = ref(null);

const validate = () => {
  let isValid = true;
  let errMsg = "";

  !props.qna.qnaTitle
    ? ((isValid = false),
      (errMsg = "제목을 입력해주세요."),
      refTitle.value.focus())
    : !props.qna.qnaContent
    ? ((isValid = false),
      (errMsg = "내용을 입력해주세요."),
      refContent.value.focus())
    : !props.qna.qnaWriter
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
    name: "QnaList",
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
      v-model="qna.qnaTitle"
      ref="refTitle" />

    <label class="form-label pt-3" for="writer">글쓴이</label>
    <input
      class="form-control"
      type="text"
      id="writer"
      name="writer"
      v-model="qna.qnaWriter"
      ref="refWriter"
      disabled />

    <label class="form-label pt-3" for="content">내용</label>
    <textarea
      class="form-control"
      id="content"
      name="content"
      rows="5"
      v-model="qna.qnaContent"
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
