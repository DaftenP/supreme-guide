<script setup>
import { ref, onMounted } from "vue";
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

onMounted(() => {
  if (props.type === "modify") {
    tripItems.value = tripStore.trip.tripItems;
    mapStore.lat = tripItems.value[0].latitude;
    mapStore.lng = tripItems.value[0].longitude;
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
              <button class="btn btn-outline-danger" @click="delItem(element)">
                X
              </button>
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
