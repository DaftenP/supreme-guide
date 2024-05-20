<script setup>
import { ref, defineProps } from "vue";
import MapComponent from "@/components/commons/MapComponent.vue";
import AttractionSearch from "@/components/attraction/AttractionSearch.vue";
import AttractionListItem from "@/components/item/AttractionListItem.vue";
import AttractionModal from "@/components/modal/AttractionModal.vue";
import { useMapStore } from "@/stores/map";
import { getAttraction } from "@/api/attraction";
import draggable from "vuedraggable";

const mapStore = useMapStore();
const props = defineProps({
  trip: Object,
  type: String,
});
const emit = defineEmits(["evtProcess"]);
const tripItems = ref([]);
const refTitle = ref(null);
const refContent = ref(null);
const refWriter = ref(null);

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
    : tripItems.length == 0
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
</script>

<template>
  <div class="mb-3">
    <label class="form-label" for="title">제목</label>
    <input
      class="form-control"
      type="text"
      id="title"
      name="title"
      v-model="trip.tripName"
      ref="refTitle" />

    <label class="form-label pt-3" for="writer">글쓴이</label>
    <input
      class="form-control"
      type="text"
      id="writer"
      name="writer"
      v-model="trip.userId"
      ref="refWriter"
      disabled />
    <div class="d-flex row">
      <div class="col-xl-6 col-lg-12 col-md-12">
        <MapComponent :tripList="tripItems"></MapComponent>
        <draggable
          v-model="tripItems"
          @start="drag = true"
          @end="drag = false"
          item-key="contentId">
          <template #item="{ element }">
            <div class="list">
              {{ element.title }}
            </div>
          </template>
        </draggable>
      </div>
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
    </div>
    <label class="form-label pt-3" for="content">내용</label>
    <textarea
      class="form-control"
      id="content"
      name="content"
      rows="5"
      v-model="trip.tripContent"
      ref="refContent">
    </textarea>

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
.fixed-height {
  height: 500px; /* 고정 높이 설정 */
  overflow-y: auto; /* 수직 스크롤 추가 */
}
</style>
