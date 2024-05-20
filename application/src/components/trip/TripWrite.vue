<script setup>
import TripFormItem from "@/components/item/TripFormItem.vue";
import axios from "axios";
import { ref, onMounted, reactive, onUpdated } from "vue";
import { useRouter } from "vue-router";
import { postArticle } from "@/api/trip";
import { useUserStore } from "@/stores/userStore";
import authClient from "@/api/authClient";
import Cookies from "vue-cookies";
const userStore = useUserStore();

// data
const trip = ref({});

// LifeCycle
onUpdated(() => {
  console.log(`정보: ${JSON.stringify(trip)}`);
});

const router = useRouter();

const goList = () => {
  router.push({
    name: "TripList",
  });
};

onMounted(() => {
  trip.value.userId = userStore.userId;
});

const writeArticle = async (param) => {
  await postArticle(param, (res) => {
    alert("등록 완료");
    goList();
  }),
    (err) => {
      console.log(err);
      alert("등록 실패");
    };
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글쓰기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <TripFormItem type="create" :trip="trip" @evt-process="writeArticle" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
