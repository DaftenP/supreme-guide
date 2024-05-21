<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import Cookies from "vue-cookies";
import VueJwtDecode from "vue-jwt-decode";
import noAuthClient from "@/api/noAuthClient";
import { useUserStore } from "@/stores/userStore";
import { Vue3Lottie } from "vue3-lottie";
import LoginJson from "@/assets/animations/Login.json";

const router = useRouter();
const userStore = useUserStore();

const id = ref("");
const password = ref("");

const loginUser = async (e) => {
  if (id.value && password.value) {
    try {
      const res = await noAuthClient({
        method: "post",
        url: `${import.meta.env.VITE_API_BASE_URL}/auth/login`,
        data: {
          id: id.value,
          pw: password.value,
        },
      });
      Cookies.set("accessToken", res.data.accessToken);
      Cookies.set("refreshToken", res.data.refreshToken);
      const decode = VueJwtDecode.decode(res.data.accessToken);
      console.log(decode);
      userStore.setUserId(decode.userId);
      alert("로그인에 성공했습니다.");
      router.push("/");
    } catch (error) {
      console.log(error);
      alert("아이디나 비밀번호를 다시 확인해주세요.");
    }
  } else {
    alert("유효성 검사를 다시 해주세요.");
  }
};
</script>

<template>
  <div class="flex min-h-full justify-center items-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h2
        class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
        Sign in to your account
      </h2>
      <Vue3Lottie :animationData="LoginJson" />
    </div>
    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <!-- <h1 class="text-3xl font-bold mb-6">Sign in your account</h1> -->
      <form @submit.prevent="loginUser" class="space-y-6">
        <div>
          <label
            for="id"
            class="block text-sm font-medium leading-6 text-gray-900"
            >ID</label
          >
          <input
            id="id"
            type="text"
            v-model="id"
            class="pl-1 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
        </div>
        <div>
          <label
            for="password"
            class="block text-sm font-medium leading-5 text-gray-700"
            >Password</label
          >
          <input
            id="password"
            type="password"
            v-model="password"
            class="pl-1 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
        </div>
        <div>
          <button
            type="submit"
            class="w-full py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
            로그인
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style>
/* 스타일링 */
</style>
