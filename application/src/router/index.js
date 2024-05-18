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
      name: "BoardHome",
      component: () => import("@/views/BoardView.vue"),
      redirect: "/qna/all",
      children: [
        {
          path: "all",
          name: "BoardList",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:qna_id",
          name: "BoardDetail",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "modify/:qna_id",
          name: "BoardModify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
        {
          path: "/regist",
          name: "BoardWrite",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
      ],
    },
    {
      path: "/attraction",
      name: "Attraction",
      component: () => import("@/views/AttractionView.vue"),
      redirect: "AttractionMain",
      children: [
        {
          path: "",
          name: "AttractionMain",
          component: () =>
            import("@/components/attraction/AttractionSearch.vue"),
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
