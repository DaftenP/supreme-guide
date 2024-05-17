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
      path: "/board",
      name: "BoardHome",
      component: () => import("@/views/BoardView.vue"),
      redirect: "BoardList",
      children: [
        {
          path: "",
          name: "BoardList",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:board_id",
          name: "BoardDetail",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "modify/:board_id",
          name: "BoardModify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
        {
          path: "write",
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
        },
      ],
    },
  ],
});

export default router;
