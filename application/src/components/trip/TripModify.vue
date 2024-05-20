<script setup>
import TripFormItem from "@/components/item/TripFormItem.vue";
import axios from "axios";
import { ref, onMounted, reactive, onUpdated } from "vue";
import { useRoute, useRouter } from "vue-router";
import { putArticle } from "@/api/trip";
import { useUserStore } from "@/stores/userStore";
import authClient from "@/api/authClient";
import Cookies from "vue-cookies";
import { useTripStore } from "@/stores/trip";

// data
const userStore = useUserStore();
const tripStore = useTripStore();
const route = useRoute();
const router = useRouter();

// LifeCycle
onUpdated(() => {
  console.log(`정보: ${JSON.stringify(trip)}`);
});

const goList = () => {
  router.push({
    name: "TripList",
  });
};

const modifyArticle = async (param) => {
  await putArticle(param, (res) => {
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
        <TripFormItem
          type="modify"
          :trip="tripStore.trip"
          @evt-process="modifyArticle" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
