import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import VueDevTools from "vite-plugin-vue-devtools";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), VueDevTools()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  server: {
    proxy: {
      "/blog": {
        target: "https://openapi.naver.com",
        changeOrigin: true, // 도메인이 다른 서버로부터 데이터를 받는 것을 허용
        rewrite: (path) => path.replace(/^\/blog/, ""),
      },
      "/video": {
        target: "https://www.googleapis.com",
        changeOrigin: true, // 도메인이 다른 서버로부터 데이터를 받는 것을 허용
        rewrite: (path) => path.replace(/^\/video/, ""),
      },
    },
  },
});
