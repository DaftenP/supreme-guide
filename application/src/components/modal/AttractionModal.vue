<script setup>
import { ref } from "vue";
import { useMapStore } from "@/stores/map";
import BlogArticleItem from "@/components/item/BlogArticleItem.vue";

const mapStore = useMapStore();
</script>

<template>
  <div class="modal-wrap" v-show="mapStore.modalCheck" @click="modalOpen"></div>

  <!-- Modal -->
  <div
    class="modal fade"
    id="attractionModal"
    tabindex="-1"
    aria-labelledby="attractionModalLabel"
    aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="attractionModalLabel">
            {{ mapStore.attractionInfo.title }}
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"></button>
        </div>
        <div class="modal-container overflow-auto" @click.stop="">
          <div class="d-flex justify-content-center">
            <img
              class="modal-img"
              :src="
                mapStore.attractionInfo.firstImage
                  ? mapStore.attractionInfo.firstImage
                  : 'http://localhost:5173/src/assets/img/no-img.png'
              " />
          </div>
          <div></div>
          <div>{{ mapStore.attractionInfo.addr1 }}</div>
          <div>{{ mapStore.attractionInfo.addr2 }}</div>
          <div>{{ mapStore.attractionInfo.zipcode }}</div>
          <div>{{ mapStore.attractionInfo.tel }}</div>
          <div>{{ mapStore.attractionInfo.description }}</div>
          <BlogArticleItem :articles="mapStore.blogList"></BlogArticleItem>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* modal or popup */
.modal-container {
  max-height: 70%;
  width: 100%;
  padding: 20px;
}
.modal-img {
  width: 80%;
}

::-webkit-scrollbar-button:start {
  display: block;
  height: 22px;
}
</style>
