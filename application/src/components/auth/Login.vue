<template>
  <div>
    <h1>Login Page</h1>
    <form @submit.prevent="loginUser">
      <!-- 각 입력 필드와 관련된 상태를 ref로 정의 -->
      <div>
        <label for="id">아이디:</label>
        <input type="text" id="id" v-model="id" />
      </div>
      <div>
        <label for="password">비밀번호:</label>
        <input type="password" id="password" v-model="password" />
      </div>

      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import Cookies from "vue-cookies";
import VueJwtDecode from "vue-jwt-decode";
import noAuthClient from "@/api/noAuthClient";
import { useUserStore } from "@/stores/userStore";

export default {
  setup() {
    const router = useRouter();
    const userStore = useUserStore();
    // ref를 사용하여 상태를 정의
    const id = ref("");
    const password = ref("");

    const loginUser = async (e) => {
      // 로그인
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
          console.log(res.data);
          const decode = VueJwtDecode.decode(res.data.accessToken);

          // userId를 Pinia 스토어에 저장한다.
          userStore.setUserId(decode.userId);
          console.log("decode!~1!!!!!" + decode.userId);
          alert("로그인에 성공했습니다.");
          router.push("/");
        } catch (error) {
          console.log(error);
        }
      } else {
        alert("유효성 검사를 다시 해주세요.");
      }
    };

    // 컴포넌트 옵션 반환
    return {
      id,
      password,
      loginUser,
    };
  },
};
</script>

<style>
/* 스타일링 */
</style>
