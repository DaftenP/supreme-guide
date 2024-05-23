<script setup>
import TripFormItem from "@/components/item/TripFormItem.vue";
import { ref, onMounted, onUpdated } from "vue";
import { useRoute, useRouter } from "vue-router";
import { postArticle } from "@/api/trip";
import { useUserStore } from "@/stores/userStore";

const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

// data
const trip = ref({});

// LifeCycle
onUpdated(() => {
  console.log(`정보: ${JSON.stringify(trip)}`);
});

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
      <div class="col-lg-10"></div>
      <div class="col-lg-10 text-start">
        <TripFormItem type="create" :trip="trip" @evt-process="writeArticle" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
