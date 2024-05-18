<script setup>
import HeaderNav from "./components/commons/HeaderNav.vue";
</script>

<template>
  <HeaderNav />
  <RouterView />
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
</style>
