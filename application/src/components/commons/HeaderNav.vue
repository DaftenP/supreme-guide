<script setup>
import { Vue3Lottie } from "vue3-lottie";
import { useRouter } from "vue-router";
import { ref } from "vue";
import { useUserStore } from "@/stores/userStore";
import BearJson from "@/assets/animations/Bear.json";
import TripJson from "@/assets/animations/Trip.json";
import settingCookie from "@/utils/settingCookie";
const router = useRouter();
const userStore = useUserStore();

const goQna = () => {
  router.push({
    name: "QnaList",
  });
};

const goHome = () => {
  router.push({
    name: "home",
  });
};

const goAttraction = () => {
  router.push({
    path: "/attraction",
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

const goNotice = () => {
  router.push({
    name: "NoticeList",
  });
};

const goLogout = () => {};

const logout = () => {
  userStore.setUserId("");
  settingCookie("remove");
  alert("로그아웃 되었습니다.");
  router.push({ name: "home" });
};

const isMenuOpen = ref(false);
const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value;
};
</script>

<template>
  <header class="bg-white shadow-md z-30">
    <nav
      class="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8"
      aria-label="Global">
      <div class="flex lg:flex-1 items-center">
        <a
          @click="goHome"
          class="-m-1.5 p-1.5 cursor-pointer flex items-center space-x-2">
          <span class="text-lg font-semibold text-gray-900">YJTrip</span>
          <Vue3Lottie
            :animationData="TripJson"
            style="width: 50px; height: 50px" />
        </a>
      </div>
      <div class="hidden lg:flex lg:gap-x-12">
        <button
          @click="goAttraction"
          class="text-sm font-semibold leading-6 text-gray-900 hover:text-indigo-600 transition duration-300">
          관광지 검색
        </button>
        <button
          @click="goQna"
          class="text-sm font-semibold leading-6 text-gray-900 hover:text-indigo-600 transition duration-300">
          Q&A 게시판
        </button>
        <button
          @click="goNotice"
          class="text-sm font-semibold leading-6 text-gray-900 hover:text-indigo-600 transition duration-300">
          공지사항
        </button>
      </div>
      <div
        class="hidden lg:flex lg:flex-1 lg:justify-end items-center space-x-6">
        <template v-if="userStore.isLoggedIn">
          <span
            class="text-gray-700 transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-110"
            >{{ userStore.userId }}님 환영합니다.</span
          >
          <button
            @click="logout"
            class="text-sm font-semibold leading-6 text-gray-900 hover:text-indigo-600 transition duration-300">
            로그아웃
          </button>
        </template>
        <template v-else>
          <button
            @click="goLogin"
            class="text-sm font-semibold leading-6 text-gray-900 hover:text-indigo-600 transition duration-300">
            로그인
          </button>
          <button
            @click="goJoin"
            class="text-sm font-semibold leading-6 text-gray-900 hover:text-indigo-600 transition duration-300">
            회원가입
          </button>
        </template>
      </div>
      <div class="flex lg:hidden">
        <button
          type="button"
          class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700"
          @click="toggleMenu">
          <span class="sr-only">Open main menu</span>
          <svg
            class="h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            aria-hidden="true">
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
          </svg>
        </button>
      </div>
    </nav>
    <!-- 화면 반응형 -->
    <div v-if="isMenuOpen" class="lg:hidden" role="dialog" aria-modal="true">
      <div class="fixed inset-0 z-10"></div>
      <div
        class="fixed inset-y-0 right-0 z-10 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10">
        <div class="flex items-center justify-between">
          <a
            @click="goHome"
            class="-m-1.5 p-1.5 cursor-pointer flex items-center space-x-2">
            <span class="text-lg font-semibold text-gray-900">YJTrip</span>
            <Vue3Lottie
              :animationData="BearJson"
              style="width: 50px; height: 50px" />
          </a>
          <button
            type="button"
            class="-m-2.5 rounded-md p-2.5 text-gray-700"
            @click="toggleMenu">
            <span class="sr-only">Close menu</span>
            <svg
              class="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              aria-hidden="true">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
        <div class="mt-6 flow-root">
          <div class="-my-6 divide-y divide-gray-500/10">
            <div class="space-y-2 py-6">
              <button
                @click="goAttraction"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:text-indigo-600 transition duration-300">
                관광지 검색
              </button>
              <button
                @click="goQna"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:text-indigo-600 transition duration-300">
                Q&A 게시판
              </button>
              <button
                @click="goNotice"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:text-indigo-600 transition duration-300">
                공지사항
              </button>
            </div>
            <div class="py-6">
              <template v-if="userStore.isLoggedIn">
                <button
                  @click="logout"
                  class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:text-indigo-600 transition duration-300">
                  로그아웃
                </button>
              </template>
              <template v-else>
                <button
                  @click="goLogin"
                  class="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 hover:text-indigo-600 transition duration-300">
                  로그인
                </button>
                <button
                  @click="goJoin"
                  class="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 hover:text-indigo-600 transition duration-300">
                  회원가입
                </button>
              </template>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>
.text-gray-700:hover {
  color: #007bff;
}
</style>
