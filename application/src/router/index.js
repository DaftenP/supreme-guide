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
      path: "/attraction",
      name: "Attraction",
      component: () => import("@/views/AttractionView.vue"),
      // redirect: "AttractionMain",
      // children: [
      //   {
      //     path: "",
      //     name: "AttractionMain",
      //     component: () =>
      //       import("@/components/attraction/AttractionSearch.vue"),
      //   },
      // ],
    },

    {
      path: "/trip",
      name: "Trip",
      component: () => import("@/views/TripView.vue"),
      redirect: "TripList",
      children: [
        {
          path: "",
          name: "TripList",
          component: () => import("@/components/trip/TripList.vue"),
        },
        {
          path: ":tripId",
          name: "TripDetail",
          component: () => import("@/components/trip/TripDetail.vue"),
        },
        {
          path: "regist",
          name: "TripWrite",
          component: () => import("@/components/trip/TripWrite.vue"),
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

    {
      path: "/notice",
      name: "NoticeHome",
      component: () => import("@/views/NoticeView.vue"),
      redirect: "/",
      children: [
        {
          path: "all",
          name: "NoticeList",
          component: () => import("@/components/notice/NoticeList.vue"),
        },
        {
          path: "view/:noticeId",
          name: "NoticeDetail",
          component: () => import("@/components/notice/NoticeDetail.vue"),
        },
        {
          path: "modify/:noticeId",
          name: "NoticeModify",
          component: () => import("@/components/notice/NoticeModify.vue"),
        },
        {
          path: "/regist",
          name: "NoticeWrite",
          component: () => import("@/components/notice/NoticeWrite.vue"),
        },
      ],
    },
  ],
});

export default router;
