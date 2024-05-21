import "./assets/main.css";

import { useKakao } from "vue3-kakao-maps/@utils";
import { createApp } from "vue";
import { createPinia } from "pinia";

import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import Vue3Lottie from "vue3-lottie";

import vueCookies from "vue-cookies";

import App from "./App.vue";
import router from "./router";

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./assets/tailwind.css";

const app = createApp(App).use(Vue3Lottie);
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const { VITE_API_KAKAO_MAP_API_KEY } = import.meta.env;
useKakao(VITE_API_KAKAO_MAP_API_KEY, ["clusterer", "services", "drawing"]);

app.use(router);

app.use(vueCookies);
app.use(createPinia().use(piniaPluginPersistedstate));
app.$cookies.config("7d"); // 쿠키 만료일 -> 7일 default

app.mount("#app");
