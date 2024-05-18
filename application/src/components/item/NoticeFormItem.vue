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
  <div class="mb-3">
    <label class="form-label" for="title">제목</label>
    <input
      class="form-control"
      type="text"
      id="title"
      name="title"
      v-model="notice.noticeTitle"
      ref="refTitle" />

    <label class="form-label pt-3" for="writer">작성자</label>
    <input
      class="form-control"
      type="text"
      id="writer"
      name="writer"
      v-model="notice.noticeWriter"
      ref="refWriter"
      disabled />

    <label class="form-label pt-3" for="content">내용</label>
    <textarea
      class="form-control"
      id="content"
      name="content"
      rows="5"
      v-model="notice.noticeContent"
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



<style scoped>

</style>