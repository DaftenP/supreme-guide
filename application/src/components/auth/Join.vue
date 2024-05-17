<template>
  <div>
    <h1>Register Page</h1>
    <form @submit.prevent="joinUser">
      <!-- 각 입력 필드와 관련된 상태를 ref로 정의 -->
      <div>
        <label for="name">이름:</label>
        <input type="text" id="name" v-model="name" />
        <span>{{ nameMessage }}</span>
      </div>
      <div>
        <label for="id">아이디:</label>
        <input type="text" id="id" v-model="id" @blur="checkId" />
        <span>{{ idMessage }}</span>
        <button type="button" @click="checkId">아이디 중복 확인</button>
      </div>
      <div>
        <label for="password">비밀번호:</label>
        <input
          type="password"
          id="password"
          v-model="password"
          @change="onChangePassword" />
        <span>{{ passwordMessage }}</span>
      </div>
      <div>
        <label for="confirmPassword">비밀번호 확인:</label>
        <input
          type="password"
          id="confirmPassword"
          v-model="confirmPassword"
          @change="onChangePasswordConfirm" />
        <span>{{ passwordConfirmMessage }}</span>
      </div>
      <div>
        <label for="email">이메일:</label>
        <input type="email" id="email" v-model="email" />
        <span>{{ emailMessage }}</span>
      </div>
      <div>
        <label for="admin">관리자 여부</label>
        <input type="int" id="admin" v-model="admin" />
      </div>
      <button type="submit">가입하기</button>
    </form>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import noAuthClient from "@/api/noAuthClient";

export default {
  setup() {
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
      const passwordRegex =
        /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
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
              admin: admin.value,
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

    // 컴포넌트 옵션 반환
    return {
      name,
      id,
      password,
      confirmPassword,
      nameMessage,
      idMessage,
      passwordMessage,
      passwordConfirmMessage,
      onChangePassword,
      onChangePasswordConfirm,

      checkId,
      joinUser,
    };
  },
};
</script>

<style>
/* 스타일링 */
</style>
