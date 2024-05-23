<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import MapComponent from "@/components/commons/MapComponent.vue";
import AttractionSearch from "@/components/attraction/AttractionSearch.vue";
import AttractionListItem from "@/components/item/AttractionListItem.vue";
import AttractionModal from "@/components/modal/AttractionModal.vue";
import { useMapStore } from "@/stores/map";
import { getAttraction } from "@/api/attraction";
import { useRoute, useRouter } from "vue-router";
import draggable from "vuedraggable";
import { useTripStore } from "@/stores/trip";

const tripStore = useTripStore();
const mapStore = useMapStore();
const props = defineProps({
  trip: Object,
  type: String,
});
const router = useRouter();
const emit = defineEmits(["evtProcess"]);
const tripItems = ref([]);
const refTitle = ref(null);
const refContent = ref(null);
const refWriter = ref(null);
const scrollContainer = ref(null);

const handleItemClick = (item) => {
  mapStore.lat = item.latitude;
  mapStore.lng = item.longitude;
};

const handleWheel = (event) => {
  event.preventDefault(); // 수직 스크롤 방지
  const { deltaX, deltaY } = event;
  scrollContainer.value.scrollLeft += deltaY + deltaX;
};

onBeforeUnmount(() => {
  if (scrollContainer.value) {
    scrollContainer.value.removeEventListener("wheel", handleWheel);
  }
});
onMounted(() => {
  if (props.type === "modify") {
    tripItems.value = tripStore.trip.tripItems;
    mapStore.lat = tripItems.value[0].latitude;
    mapStore.lng = tripItems.value[0].longitude;
  }
  mapStore.attractions = ref([]);
  if (scrollContainer.value) {
    scrollContainer.value.addEventListener("wheel", handleWheel, {
      passive: false,
    });
  }
});

// methods
const getModal = (id) => {
  getAttraction(
    id,
    (resp) => {
      mapStore.attractionInfo = resp.data;
    },
    (err) => {
      console.log(err);
    }
  );
};

const addItem = (attraction) => {
  mapStore.lat = attraction.latitude;
  mapStore.lng = attraction.longitude;
  tripItems.value.push(attraction);
};

const validate = () => {
  let isValid = true;
  let errMsg = "";

  !props.trip.tripName
    ? ((isValid = false),
      (errMsg = "제목을 입력해주세요."),
      refTitle.value.focus())
    : !props.trip.tripContent
    ? ((isValid = false),
      (errMsg = "내용을 입력해주세요."),
      refContent.value.focus())
    : tripItems.value.length == 0
    ? ((isValid = false), (errMsg = "여행지를 입력해주세요."))
    : (isValid = true);
  if (!isValid) {
    alert(errMsg);
  } else {
    emit("evtProcess", {
      ...props.trip,
      tripItems: tripItems.value.map((el) => ({
        contentId: el.contentId,
      })),
    });
  }
};

const goList = () => {
  router.push({
    name: "TripList",
  });
};

const delItem = (tar) => {
  tripItems.value = tripItems.value.filter((el) => el != tar);
};
</script>

<template>
  <div class="blog-post">
    <h2 class="blog-post-title">
      여행 {{ type === "create" ? "등록" : "수정" }}
    </h2>
    <p class="blog-post-meta">
      {{ new Date().toLocaleDateString() }} by <a href="#">{{ trip.userId }}</a>
    </p>
    <div class="title-line">
      <input
        class="title-input"
        v-model="trip.tripName"
        ref="refTitle"
        placeholder="여기에 제목을 적어주세요" />
    </div>

    <div class="d-flex row justify-content-center">
      <div class="col-xl-6 col-lg-12 col-md-12">
        <AttractionSearch></AttractionSearch>
        <div class="fixed-height">
          <AttractionListItem
            v-for="(attraction, index) in mapStore.attractions"
            :key="index"
            type="regist"
            :attraction="attraction"
            @detail-view="getModal"
            @add-item="addItem"></AttractionListItem>
          <AttractionModal />
        </div>
      </div>
      <div class="col-xl-6 col-lg-12 col-md-12">
        <MapComponent :tripList="tripItems"></MapComponent>
      </div>
      <div class="container p-0 mt-3 mb-3 border rounded-lg h-30">
        <div
          ref="scrollContainer"
          class="flex flex-row align-items-center overflow-x-auto no-scrollbar w-full">
          <draggable
            v-model="tripItems"
            @start="drag = true"
            @end="drag = false"
            item-key="contentId"
            class="flex">
            <template #item="{ element }">
              <div
                class="list flex-shrink-0 d-flex align-items-center h-20 m-2 border border-gray-500 rounded-md cursor-pointer hover:bg-gray-50"
                @click="handleItemClick(element)"
                @dblclick="delItem(element)">
                <div class="w-20">
                  <img
                    class="thumbnail m-1 border"
                    :src="
                      element.firstImage
                        ? element.firstImage
                        : `/src/assets/img/no-img.png`
                    " />
                </div>
                <div class="w-25 pe-5">
                  <span
                    class="text-sm truncate overflow-hidden whitespace-nowrap">
                    {{ element.title }}
                  </span>
                  <br />
                  <span
                    class="text-xs truncate overflow-hidden whitespace-nowrap">
                    {{ element.addr1 }}
                  </span>
                </div>
              </div>
            </template>
          </draggable>
        </div>
      </div>
    </div>
    <div class="mb-3">
      <label class="form-label visually-hidden" for="content">내용</label>
      <textarea
        class="form-control"
        id="content"
        name="content"
        rows="10"
        v-model="trip.tripContent"
        ref="refContent"
        placeholder="내용을 여기에 입력하세요..."></textarea>
    </div>

    <div class="d-flex justify-content-end mt-4">
      <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="validate">
        {{ type === "create" ? "등록" : "수정" }}
      </button>
      <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="goList">
        목록
      </button>
    </div>
  </div>
</template>

<style scoped>
#div-map {
  width: 100% !important;
  height: 600px !important;
}
.blog-post {
  margin: 2rem auto;
  padding: 2rem;
  background-color: #f8f9fa;
  border-radius: 0.5rem;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
}

.blog-post-title {
  margin-bottom: 1rem;
  font-size: 2rem;
  font-weight: 600;
}

.blog-post-meta {
  margin-bottom: 2rem;
  color: #6c757d;
}

.title-line {
  margin-bottom: 1.5rem;
  border-bottom: 2px solid #ccc;
}

.title-input {
  width: 100%;
  border: none;
  outline: none;
  font-size: 1.5rem;
  background-color: transparent;
  padding: 0.5rem 0;
}

.title-placeholder {
  position: absolute;
  top: -0.8em;
  left: 0;
  background-color: #f8f9fa;
  padding: 0 0.5em;
  color: #6c757d;
  font-style: italic;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-secondary {
  background-color: #6c757d;
  border-color: #6c757d;
}
.no-scrollbar::-webkit-scrollbar {
  display: none;
}
.no-scrollbar {
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
.thumbnail {
  width: 70px; /* 원하는 너비 설정 */
  height: 70px; /* 원하는 높이 설정 */
  object-fit: cover; /* 비율을 유지하면서 주어진 너비와 높이에 맞게 조정 */
  object-position: center; /* 이미지가 잘릴 때 중앙을 기준으로 잘리도록 설정 */
  display: block; /* 이미지를 블록 요소로 설정 */
  overflow: hidden; /* 넘치는 부분은 숨김 */
  border-radius: 10px;
}
.fixed-height {
  height: 540px; /* 고정 높이 설정 */
  overflow-y: auto; /* 수직 스크롤 추가 */
}
.h-30 {
  height: 100px;
}
</style>
