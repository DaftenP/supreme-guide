<script setup>
import Footer from "./components/commons/Footer.vue";
import HeaderNav from "./components/commons/HeaderNav.vue";
</script>

<template>
  <HeaderNav />
  <RouterView />
  <Footer />
</template>

<style>
::-webkit-scrollbar {
  width: 0.3rem;
  height: 0.4rem;
  padding-right: 10;
}
::-webkit-scrollbar-thumb {
  background: rgba(127, 130, 140, 0.6);
  border-radius: 15px;
}
::-webkit-scrollbar-button {
  display: block;
  height: 3px;
}

@font-face {
  font-family: "CustomFont"; /* 사용할 글꼴의 이름을 지정합니다. */
  src: url("/fonts/font1.ttf") format("truetype"); /* font1.ttf의 경로를 지정합니다. */
}

@font-face {
  font-family: "CustomFont2"; /* 사용할 글꼴의 이름을 지정합니다. */
  src: url("/fonts/font2.ttf") format("truetype"); /* font1.ttf의 경로를 지정합니다. */
}

@font-face {
  font-family: "CustomFont3"; /* 사용할 글꼴의 이름을 지정합니다. */
  src: url("/fonts/font3.ttf") format("truetype"); /* font1.ttf의 경로를 지정합니다. */
}

#app {
  background-image: url("/images/new-background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed;
}
</style>
