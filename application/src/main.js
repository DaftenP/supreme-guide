import "./assets/main.css";

import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import Vue3Lottie from "vue3-lottie";
import vueCookies from "vue-cookies";

const app = createApp(App).use(Vue3Lottie);

app.use(vueCookies);
app.use(router);

Vue.$cookies.config("7d"); // 쿠키 만료일 -> 7일 default
app.mount("#app");
