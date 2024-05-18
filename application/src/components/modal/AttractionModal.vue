<script setup>
import { ref } from "vue";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

const modalOpen = () => {
  mapStore.modalCheck = !mapStore.modalCheck;
};
</script>

<template>
  <div class="modal-wrap" v-show="mapStore.modalCheck" @click="modalOpen">
    <div class="modal-container overflow-auto" @click.stop="">
      <div>
        <img class="modal-img" :src="mapStore.attractionInfo.firstImage" />
      </div>
      <div>{{ mapStore.attractionInfo.title }}</div>
      <div>{{ mapStore.attractionInfo.addr1 }}</div>
      <div>{{ mapStore.attractionInfo.addr2 }}</div>
      <div>{{ mapStore.attractionInfo.zipcode }}</div>
      <div>{{ mapStore.attractionInfo.tel }}</div>
      <div>{{ mapStore.attractionInfo.description }}</div>

      <div class="modal-btn">
        <button @click="modalOpen">닫기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  z-index: 1;
}
/* modal or popup */
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  max-height: 70%;
  transform: translate(-50%, -50%);
  width: 550px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
.modal-img {
  width: 100%;
}
::-webkit-scrollbar {
  width: 0.3rem;
  height: 0.4rem;
  padding-right: 10;
}
::-webkit-scrollbar-thumb {
  background: rgba(127, 130, 140, 0.6);
}
</style>
