import "./assets/main.css";

import { useKakao } from "vue3-kakao-maps/@utils";
import { createApp } from "vue";
import { createPinia } from "pinia";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faUser, faEnvelope, fas } from "@fortawesome/free-solid-svg-icons";
import AOS from "aos";
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "aos/dist/aos.css";

library.add(faUser, faEnvelope, fas);
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import Vue3Lottie from "vue3-lottie";

import vueCookies from "vue-cookies";

import App from "./App.vue";
import router from "./router";

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./assets/tailwind.css";
import "vuetify/dist/vuetify.min.css";

const app = createApp(App).use(Vue3Lottie);
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const vuetify = createVuetify({
  components,
  directives,
});
const { VITE_API_KAKAO_MAP_API_KEY } = import.meta.env;
useKakao(VITE_API_KAKAO_MAP_API_KEY, ["clusterer", "services", "drawing"]);

app.use(router);
app.use(vuetify);
app.use(vueCookies);
app.use(createPinia().use(piniaPluginPersistedstate));

app.$cookies.config("7d"); // 쿠키 만료일 -> 7일 default
app.component("font-awesome-icon", FontAwesomeIcon);

app.mount("#app", () => {
  AOS.init();
});
