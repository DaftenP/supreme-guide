<script setup>
import { ref, toRefs } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const props = defineProps({
  type: String,
  hotplace: Object,
});

const emit = defineEmits(["evtProcess"]);

const { hotplace } = toRefs(props);

const refTitle = ref(null);
const refComment = ref(null);
const refWriter = ref(null);
const refImage = ref(null);
const refAddr = ref(null);

const validate = () => {
  let isValid = true;
  let errMsg = "";

  !props.hotplace.hotplaceName
    ? ((isValid = false),
      (errMsg = "제목을 입력해주세요."),
      refTitle.value.focus())
    : !props.hotplace.comment
    ? ((isValid = false),
      (errMsg = "내용을 입력해주세요."),
      refComment.value.focus())
    : (isValid = true);
  if (!isValid) {
    alert(errMsg);
  } else {
    emit("evtProcess");
  }
};

const goList = () => {
  router.push({
    name: "HotplaceList",
  });
};
</script>

<template>
  <div class="d-flex justify-content-end mt-4">
    <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="validate">
      {{ type === "create" ? "등록" : "수정" }}
    </button>
    <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="goList">
      목록
    </button>
  </div>
</template>

<style scoped>
#div-map {
  height: 600px !important;
}
.container {
  display: flex;
}
.left-panel {
  width: 50%;
  padding: 20px;
}
.right-panel {
  width: 50%;
  padding: 20px;
}
.map {
  width: 100%;
  height: 500px;
}
.search-box {
  margin-bottom: 20px;
}
.selected {
  background-color: yellow;
}
</style>
