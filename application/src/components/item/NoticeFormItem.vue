<script setup>
import { ref, toRefs } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const props = defineProps({
    type: String,
    notice: Object,
})

const emit = defineEmits(["evtProcess"]);

const { notice } = toRefs(props);

const emitProcess = () => {
    emit("evt-process", notice.value);
}

const refTitle = ref(null);
const refContent = ref(null);
const refWriter = ref(null);

const validate = () => {
  let isValid = true;
  let errMsg = "";

  !props.notice.noticeTitle
    ? ((isValid = false),
      (errMsg = "제목을 입력해주세요."),
      refTitle.value.focus())
    : !props.notice.noticeContent
    ? ((isValid = false),
      (errMsg = "내용을 입력해주세요."),
      refContent.value.focus())
    : !props.notice.noticeWriter
    ? ((isValid = false),
      (errMsg = "작성자를 입력해주세요."),
      refWriter.value.focus())
    : (isValid = true);
  if (!isValid) {
    alert(errMsg);
  } else {
    emit("evtProcess");
  }
}

const goList = () => {
    router.push({
        name: "NoticeList",
    })
}
</script>

<template>
  <div class="blog-post">
    <h2 class="blog-post-title">공지사항 {{ type === "create" ? "등록" : "수정" }}</h2>
    <p class="blog-post-meta">{{ new Date().toLocaleDateString() }} by <a href="#">{{ notice.noticeWriter }}</a></p>

    <div class="title-line">
      <input
        class="title-input"
        type="text"
        id="title"
        name="title"
        v-model="notice.noticeTitle"
        ref="refTitle"
        placeholder="여기에 제목을 적어주세요"
      />
    </div>

    <div class="mb-3">
      <label class="form-label visually-hidden" for="writer">작성자</label>
      

      <label class="form-label visually-hidden" for="content">내용</label>
      <textarea
        class="form-control"
        id="content"
        name="content"
        rows="10"
        v-model="notice.noticeContent"
        ref="refContent"
        placeholder="내용을 여기에 입력하세요..."
      ></textarea>
    </div>

    <div class="d-flex justify-content-end mt-4">
      <button class="btn btn-primary" @click="validate">
        {{ type === "create" ? "등록" : "수정" }}
      </button>
      <button class="btn btn-secondary ms-2" @click="goList">
        목록
      </button>
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

.title-input, .form-control {
  width: 100%;
  border: none;
  outline: none;
  font-size: 1.5rem;
  background-color: transparent;
  padding: 0.5rem 0;
}

.form-control {
  margin-top: 1rem;
  font-size: 1rem;
  padding: 0.5rem;
  background-color: #fff;
  border: 1px solid #ced4da;
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
