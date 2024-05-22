<script setup>
import { ref, defineProps, defineEmits } from "vue";
import AttractionListItem from "@/components/item/AttractionListItem.vue";
import { useMapStore } from "@/stores/map";
import { getAttraction } from "@/api/attraction";
import { getBlog } from "@/api/blogSearch";
import { getVideo } from "@/api/videoSearch";
import BlogArticleItem from "@/components/item/BlogArticleItem.vue";
import YoutubeVideoItem from "@/components/item/YoutubeVideoItem.vue";

const mapStore = useMapStore();
const props = defineProps({
  attractions: Object,
  checkboxes: Object,
});
const emit = defineEmits(["updateCheckbox"]);
const infoPanel = ref();
const offCanvas = ref();

const getInfo = (id, title) => {
  infoPanel.value.style.display = "block";
  offCanvas.value.style.setProperty("--bs-offcanvas-width", "700px");
  getAttraction(
    id,
    (resp) => {
      mapStore.attractionInfo = resp.data;
    },
    (err) => {
      console.log(err);
    }
  );
  getBlog(
    title,
    5,
    (resp) => {
      mapStore.blogList = resp.data;
    },
    (err) => {
      console.log(err);
    }
  );
  getVideo(
    title,
    (resp) => {
      mapStore.videoList = resp.data;
    },
    (err) => {
      console.log(err);
    }
  );
};
const updateCheckbox = (id, checked) => {
  emit("updateCheckbox", id, checked);
};

const closeInfo = () => {
  infoPanel.value.style.display = "none";
  offCanvas.value.style.setProperty("--bs-offcanvas-width", "350px");
};
</script>

<template>
  <button
    type="button"
    class="btn-sidebar text-reset"
    data-bs-toggle="offcanvas"
    data-bs-target="#offcanvasScrolling"
    aria-controls="offcanvasScrolling"></button>

  <div
    class="offcanvas offcanvas-start"
    data-bs-scroll="true"
    data-bs-backdrop="false"
    tabindex="-1"
    id="offcanvasScrolling"
    aria-labelledby="offcanvasScrollingLabel"
    ref="offCanvas">
    <div class="offcanvas-header">
      <button
        type="button"
        class="text-reset close-btn"
        data-bs-dismiss="offcanvas"
        aria-label="Close"></button>
    </div>

    <div class="offcanvas-body d-flex">
      <div class="panel border-e overflow-auto">
        <div class="">
          <span v-for="checkbox in checkboxes" :key="checkbox.id">
            <button
              :class="['checkbox-button', { checked: checkbox.checked }]"
              @click="updateCheckbox(checkbox.id, !checkbox.checked)">
              {{ checkbox.label }}
            </button>
          </span>
        </div>
        <div>
          <AttractionListItem
            v-for="(attraction, index) in attractions"
            :key="index"
            :attraction="attraction"
            @detail-view="getInfo"></AttractionListItem>
        </div>
      </div>
      <div ref="infoPanel" class="panel info-panel overflow-auto">
        <button
          class="btn-close text-reset close-info"
          aria-label="Close"
          @click="closeInfo"></button>
        <div class="d-flex justify-content-center">
          <img
            class="modal-img"
            :src="
              mapStore.attractionInfo.firstImage
                ? mapStore.attractionInfo.firstImage
                : 'http://localhost:5173/src/assets/img/no-img.png'
            " />
        </div>
        <div>{{ mapStore.attractionInfo.addr1 }}</div>
        <div>{{ mapStore.attractionInfo.addr2 }}</div>
        <div>{{ mapStore.attractionInfo.zipcode }}</div>
        <div>{{ mapStore.attractionInfo.tel }}</div>
        <div>{{ mapStore.attractionInfo.description }}</div>
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
            <BlogArticleItem :articles="mapStore.blogList"></BlogArticleItem>
          </div>
          <div
            class="tab-pane fade"
            id="video"
            role="tabpanel"
            aria-labelledby="video-tab">
            <YoutubeVideoItem :videos="mapStore.videoList"></YoutubeVideoItem>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.btn-sidebar {
  position: fixed;
  left: 0;
  top: 55%;
  transform: translateY(-50%);
  z-index: 1;
  background-color: white;
  border: 1px solid #ccc;
  width: 25px;
  height: 70px;
  border-radius: 0 40px 40px 0;
}
.btn-sidebar::before {
  content: "▶";
  font-size: 15px;
  color: #676767; /* 아이콘 색상 */
}
.btn-sidebar:hover {
  background-color: #bfbfbf;
}

.close-info {
  position: absolute;
  top: 5%;
  right: -35px;
  transform: translateY(-50%);
  z-index: 1055;
  width: 25px;
  height: 25px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 0 40px 40px 0;
}

.offcanvas {
  --bs-offcanvas-width: 350px;
  top: 88px;
}
.offcanvas > * {
  padding: 0;
}

.checkbox-button {
  padding: 5px 0px;
  width: 100px;
  margin: 5px;
  border: solid 1.5px rgb(34, 34, 231);
  cursor: pointer;
  font-size: 16px;
  border-radius: 20px;
  transition: background-color 0.3s ease;
}

.checkbox-button.checked {
  background-color: rgb(34, 34, 231);
  color: white;
}

.checkbox-button:not(.checked) {
  color: rgb(34, 34, 231);
}
.close-btn {
  position: absolute;
  top: 50%;
  right: -25px;
  transform: translateY(-50%);
  z-index: 1055;
  background-color: white;
  border: 1px solid #ccc;
  width: 25px;
  height: 70px;
  border-radius: 0 40px 40px 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn::before {
  content: "◀"; /* 닫기 아이콘 */
  font-size: 15px;
  color: #676767; /* 아이콘 색상 */
}
.close-btn:hover {
  background-color: #bfbfbf;
}
.panel {
  width: 350px;
  padding: 1px;
}
.info-panel {
  display: none;
}
.nav-item {
  width: 50%;
  button {
    width: 100%;
  }
}
</style>
