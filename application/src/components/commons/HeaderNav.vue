<script setup>
import { Vue3Lottie } from "vue3-lottie";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import Cookies from "vue-cookies";
import BearJson from "@/assets/animations/Bear.json";
const router = useRouter();
const userStore = useUserStore();

const goList = () => {
  router.push({
    name: "QnaList",
  });
};

const goHome = () => {
  router.push({
    name: "home",
  });
};

const goJoin = () => {
  router.push({
    name: "AuthJoin",
  });
};

const goLogin = () => {
  router.push({
    name: "AuthLogin",
  });
};

const logout = () => {
  userStore.setUserId("");
  Cookies.remove("accessToken");
  Cookies.remove("refreshToken");
  alert("로그아웃 되었습니다.");
  router.push({ name: "home" });
};
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
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
          <!-- 로그인 상태에 따라 다른 버튼을 표시 -->
          <li class="nav-item" v-if="userStore.isLoggedIn">
            <button class="nav-link" @click="logout">로그아웃</button>
          </li>
          <li class="nav-item" v-else>
            <button class="nav-link" @click="goLogin">로그인</button>
          </li>
          <li class="nav-item" v-if="!userStore.isLoggedIn">
            <button class="nav-link" @click="goJoin">회원가입</button>
          </li>
          <li v-if="userStore.isLoggedIn">
            <span>{{ userStore.userId }}</span>
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
