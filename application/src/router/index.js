import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("@/views/HomeView.vue"),
    },
    {
      path: "/qna",
      name: "QnaHome",
      component: () => import("@/views/QnaView.vue"),
      redirect: "/qna/all",
      children: [
        {
          path: "all",
          name: "QnaList",
          component: () => import("@/components/qna/QnaList.vue"),
        },
        {
          path: "view/:qnaId",
          name: "QnaDetail",
          component: () => import("@/components/qna/QnaDetail.vue"),
        },
        {
          path: "modify/:qnaId",
          name: "QnaModify",
          component: () => import("@/components/qna/QnaModify.vue"),
        },
        {
          path: "/regist",
          name: "QnaWrite",
          component: () => import("@/components/qna/QnaWrite.vue"),
        },
      ],
    },
    {
      path: "/auth",
      name: "AuthHome",
      component: () => import("@/views/AuthView.vue"),
      redirect: "/",
      children: [
        {
          path: "/login",
          name: "AuthLogin",
          component: () => import("@/components/auth/Login.vue"),
        },
        {
          path: "/join",
          name: "AuthJoin",
          component: () => import("@/components/auth/Join.vue"),
        },
      ],
    },
  ],
});

export default router;
