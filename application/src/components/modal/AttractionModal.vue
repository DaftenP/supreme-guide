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
    <div class="modal-container" @click.stop="">
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
  transform: translate(-50%, -50%);
  width: 550px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
</style>
