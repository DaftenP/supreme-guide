<script setup>
import { ref, toRefs } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const props = defineProps({
  type: String,
  qna: Object,
});

const emit = defineEmits(["evtProcess"]);

const { qna } = toRefs(props);

const refTitle = ref(null);
const refContent = ref(null);

const validate = () => {
  let isValid = true;
  let errMsg = "";

  if (!props.qna.qnaTitle) {
    isValid = false;
    errMsg = "제목을 입력해주세요.";
    refTitle.value.focus();
  } else if (!props.qna.qnaContent) {
    isValid = false;
    errMsg = "내용을 입력해주세요.";
    refContent.value.focus();
  }

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
  <div class="blog-post">
    <h2 class="blog-post-title">
      Q&A {{ type === "create" ? "등록" : "수정" }}
    </h2>
    <p class="blog-post-meta">
      {{ new Date().toLocaleDateString() }} by
      <a href="#">{{ qna.qnaWriter }}</a>
    </p>

    <div class="title-line">
      <input
        class="title-input"
        v-model="qna.qnaTitle"
        ref="refTitle"
        placeholder="여기에 제목을 적어주세요" />
    </div>

    <div class="mb-3">
      <label class="form-label visually-hidden" for="content">내용</label>
      <textarea
        class="form-control"
        id="content"
        name="content"
        rows="10"
        v-model="qna.qnaContent"
        ref="refContent"
        placeholder="내용을 여기에 입력하세요..."></textarea>
    </div>

    <div class="d-flex justify-content-end mt-4">
      <button class="btn btn-primary" @click="validate">
        {{ type === "create" ? "등록" : "수정" }}
      </button>
      <button class="btn btn-secondary ms-2" @click="goList">목록</button>
    </div>
  </div>
</template>

<style scoped>
.blog-post {
  margin: 2rem auto;
  padding: 2rem;
  background-color: #f8f9fa;
  border-radius: 0.5rem;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
}

.blog-post-title {
  margin-bottom: 1rem;
  font-size: 2rem;
  font-weight: 600;
}

.blog-post-meta {
  margin-bottom: 2rem;
  color: #6c757d;
}

.title-line {
  margin-bottom: 1.5rem;
  border-bottom: 2px solid #ccc;
}

.title-input {
  width: 100%;
  border: none;
  outline: none;
  font-size: 1.5rem;
  background-color: transparent;
  padding: 0.5rem 0;
}

.title-placeholder {
  position: absolute;
  top: -0.8em;
  left: 0;
  background-color: #f8f9fa;
  padding: 0 0.5em;
  color: #6c757d;
  font-style: italic;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-secondary {
  background-color: #6c757d;
  border-color: #6c757d;
}
</style>
