<script setup>
import { ref } from "vue";
import { useMapStore } from "@/stores/map";
import BlogArticleItem from "@/components/item/BlogArticleItem.vue";
import YoutubeVideoItem from "@/components/item/YoutubeVideoItem.vue";
const props = defineProps({
  title: String,
  blogList: Object,
  videoList: Object,
});
</script>

<template>
  <div
    class="modal fade"
    id="attractionModal"
    tabindex="-1"
    aria-labelledby="attractionModalLabel"
    aria-hidden="true">
    <div class="modal-dialog mt-30">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="attractionModalLabel">
            {{ title }}
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"></button>
        </div>
        <div class="modal-container overflow-auto" @click.stop="">
          <div>
            <ul class="nav nav-tabs" id="myTab" role="tablist">
              <li class="nav-item" role="presentation">
                <button
                  class="nav-link active"
                  id="blog-tab"
                  data-bs-toggle="tab"
                  data-bs-target="#blog"
                  type="button"
                  role="tab"
                  aria-controls="blog"
                  aria-selected="true">
                  네이버 블로그
                </button>
              </li>
              <li class="nav-item" role="presentation">
                <button
                  class="nav-link"
                  id="video-tab"
                  data-bs-toggle="tab"
                  data-bs-target="#video"
                  type="button"
                  role="tab"
                  aria-controls="video"
                  aria-selected="false">
                  유튜브
                </button>
              </li>
            </ul>
          </div>
          <div class="tab-content">
            <div
              class="tab-pane fade show active"
              id="blog"
              role="tabpanel"
              aria-labelledby="blog-tab">
              <BlogArticleItem :articles="blogList"></BlogArticleItem>
            </div>
            <div
              class="tab-pane fade"
              id="video"
              role="tabpanel"
              aria-labelledby="video-tab">
              <YoutubeVideoItem
                :videos="videoList"
                type="modal"></YoutubeVideoItem>
            </div>
          </div>
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
.modal-dialog {
  margin-top: 120px; /* 원하는 값으로 조정 */
}
::-webkit-scrollbar-button:start {
  display: block;
  height: 22px;
}
</style>
