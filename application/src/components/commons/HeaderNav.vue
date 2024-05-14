<script setup>
import { Vue3Lottie } from "vue3-lottie";

import { useRouter } from "vue-router";

const router = useRouter();
import BearJson from "@/assets/animations/Bear.json";

const goList = () => {
  router.push({
    name: "BoardList",
  });
};

const goHome = () => {
  router.push({
    name: "home",
  });
};
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <!-- <Vue3Lottie :animationData="BearJson" style="width: 50px; height: 50px" /> -->
      <a class="navbar-brand" href="#" @click="goHome">EnjoyTrip</a>
      <Vue3Lottie :animationData="BearJson" style="width: 50px; height: 50px" />
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <button class="nav-link" href="#">관광지 검색</button>
          </li>
          <li class="nav-item">
            <button class="nav-link" @click="goList">Q&A 게시판</button>
          </li>
          <li class="nav-item">
            <button class="nav-link" href="#">공지사항</button>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.navbar-nav .nav-item:hover .nav-link {
  color: #007bff;
}

.navbar-nav .nav-item {
  transition: background-color 0.3s ease;
}

.navbar-toggler-icon {
  background-color: #007bff;
}
</style>
