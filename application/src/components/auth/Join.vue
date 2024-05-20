<template>
  <div class="flex min-h-full justify-center items-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h2
        class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
        Sign up for an account
      </h2>
      <Vue3Lottie :animationData="WelcomeJson" />
    </div>
    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="joinUser" class="space-y-6">
        <div>
          <label
            for="name"
            class="block text-sm font-medium leading-6 text-gray-900"
            >Name</label
          >
          <input
            id="name"
            type="text"
            v-model="name"
            class="pl-1 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
          <span>{{ nameMessage }}</span>
        </div>
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
            class="pl-1 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            @blur="checkId" />
          <span>{{ idMessage }}</span>
        </div>
        <div class="mt-2">
          <button
            type="button"
            @click="checkId"
            class="block w-full py-1.5 px-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500">
            아이디 중복체크
          </button>
        </div>
        <div>
          <label
            for="password"
            class="block text-sm font-medium leading-6 text-gray-900"
            >Password</label
          >
          <input
            id="password"
            type="password"
            v-model="password"
            class="pl-1 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            @change="onChangePassword" />
          <span>{{ passwordMessage }}</span>
        </div>
        <div>
          <label
            for="confirmPassword"
            class="block text-sm font-medium leading-6 text-gray-900"
            >Confirm Password</label
          >
          <input
            id="confirmPassword"
            type="password"
            v-model="confirmPassword"
            class="pl-1 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            @change="onChangePasswordConfirm" />
          <span>{{ passwordConfirmMessage }}</span>
        </div>
        <div>
          <label
            for="email"
            class="block text-sm font-medium leading-6 text-gray-900"
            >Email Address</label
          >
          <input
            id="email"
            type="email"
            v-model="email"
            class="pl-1 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
          <span>{{ emailMessage }}</span>
        </div>
        <!-- 우선 관리자 제외 -->
        <!-- <div>
          <label for="admin" class="block text-sm font-medium leading-6 text-gray-900">Admin</label>
          <input id="admin" type="number" v-model="admin" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
        </div> -->
        <div>
          <button
            type="submit"
            class="w-full py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
            Sign up
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import noAuthClient from "@/api/noAuthClient";
import { Vue3Lottie } from "vue3-lottie";
import WelcomeJson from "@/assets/animations/Welcome.json";

const router = useRouter();
// ref를 사용하여 상태를 정의
const name = ref("");
const id = ref("");
const password = ref("");
const confirmPassword = ref("");
const nameMessage = ref("");
const idMessage = ref("");
const passwordMessage = ref("");
const passwordConfirmMessage = ref("");
const emailMessage = ref("");

// 각 입력 필드의 값이 변경될 때마다 해당 상태를 업데이트하는 함수 정의
const onChangePassword = (e) => {
  password.value = e.target.value;
  // 유효성 검사 로직
  const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
  if (!passwordRegex.test(password.value)) {
    passwordMessage.value =
      "숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!";
  } else {
    passwordMessage.value = "안전한 비밀번호에요 : )";
  }
};

const onChangePasswordConfirm = (e) => {
  confirmPassword.value = e.target.value;
  // 유효성 검사 로직
  if (password.value === confirmPassword.value) {
    passwordConfirmMessage.value = "비밀번호를 똑같이 입력했어요 : )";
  } else {
    passwordConfirmMessage.value = "비밀번호가 틀려요. 다시 확인해주세요";
  }
};

const checkId = async () => {
  // 아이디 중복 확인 로직
  console.log(id.value);
  if (id.value !== "") {
    try {
      const res = await axios({
        method: "get",
        url: `${import.meta.env.VITE_API_BASE_URL}/auth/validate/id/${
          id.value
        }`,
      });
      if (res.data === "") {
        alert("이미 가입이 되어있는 ID입니다.");
      } else {
        alert("사용가능한 ID입니다.");
      }
    } catch (error) {
      console.log(error);
    }
  }
};

const joinUser = async (e) => {
  // 회원가입 로직
  if (id.value && password.value && name.value && email.value) {
    try {
      const res = await axios({
        method: "post",
        url: `${import.meta.env.VITE_API_BASE_URL}/auth/join`,
        data: {
          id: id.value,
          pw: password.value,
          name: name.value,
          email: email.value,
        },
      });
      console.log(res.data);
      alert("회원가입에 성공했습니다.");
      router.push("/");
    } catch (error) {
      const err = error.response;
      console.log(err);
    }
  } else {
    alert("유효성 검사를 다시 해주세요.");
  }
};
</script>

<style>
/* 스타일링 */
</style>
