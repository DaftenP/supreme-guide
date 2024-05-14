import "./assets/main.css";

import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import Vue3Lottie from "vue3-lottie";

const app = createApp(App).use(Vue3Lottie);

app.use(router);

app.mount("#app");
