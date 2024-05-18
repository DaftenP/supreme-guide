<script setup>
import { ref } from "vue";
import MapComponent from "../commons/MapComponent.vue";
import AttractionModal from "../modal/AttractionModal.vue";
import AttractionSideBar from "@/components/attraction/AttractionSideBar.vue";
import { getRegion, getAllAttraction, getAttraction } from "@/api/attraction";
import { useMapStore } from "@/stores/map";

const mapStore = useMapStore();

// data
const region = ref([]);
const gugun = ref([]);
const attractions = ref([]);
const sidoSelectedOption = ref(0);
const gugunSelectedOption = ref(0);
const categorySelectedOption = ref(0);
const keywordSelectedOption = ref("");

// methods
const sidoSelect = () => {
  if (sidoSelectedOption.value > 0) {
    const index = region.value.findIndex(
      (opt) => opt.sidoCode == sidoSelectedOption.value
    );
    gugun.value = region.value[index].gugunList;
  } else {
    gugun.value = [];
    gugunSelectedOption.value = 0;
  }
};

const search = () => {
  let params = {};
  if (sidoSelectedOption.value != 0) {
    params.sido = sidoSelectedOption.value;
  }
  if (gugunSelectedOption.value != 0) {
    params.gugun = gugunSelectedOption.value;
  }
  if (categorySelectedOption.value != 0) {
    params.category = categorySelectedOption.value;
  }
  if (keywordSelectedOption.value != "") {
    params.key = "title";
    params.word = keywordSelectedOption.value;
  }
  console.log(params);
  getAllAttraction(
    params,
    // success
    (resp) => {
      console.log(resp.data);
      attractions.value = resp.data;
      if (attractions.value.length > 0) {
        mapStore.lat = attractions.value[0].latitude;
        mapStore.lng = attractions.value[0].longitude;
      }
    },
    // fail
    (err) => {
      console.log(err);
    }
  );
};

// created
getRegion(
  // success
  (resp) => {
    region.value = resp.data;
  },
  // fail
  (err) => {
    console.log(err);
  }
);
</script>

<template>
  <div class="flex-grow-1 d-flex flex-column">
    <div class="search-form ps-2 pe-2">
      <div class="row">
        <div class="col-lg-2 col-md-2 col-6 p-2">
          <select
            class="form-select ps-3"
            name="sido"
            id="search-area-sido"
            v-model="sidoSelectedOption"
            @change="sidoSelect">
            <option value="0" selected>시/도 선택</option>
            <option
              v-for="sido in region"
              :key="sido.sidoCode"
              :sido="sido"
              :value="sido.sidoCode">
              {{ sido.sidoName }}
            </option>
          </select>
        </div>
        <div class="col-lg-2 col-md-2 col-6 p-2">
          <select
            class="form-select ps-3"
            name="gugun"
            id="search-area-gugun"
            v-model="gugunSelectedOption">
            <option value="0" selected>구/군 선택</option>
            <option
              v-for="g in gugun"
              :key="g.gugunCode"
              :g="g"
              :value="g.gugunCode">
              {{ g.gugunName }}
            </option>
          </select>
        </div>
        <div class="col-lg-2 col-md-2 col-12 p-2">
          <select
            class="form-select ps-3"
            name="category"
            id="search-content-id"
            v-model="categorySelectedOption">
            <option value="0" selected>관광지 유형</option>
            <option value="12">관광지</option>
            <option value="14">문화시설</option>
            <option value="15">축제공연행사</option>
            <option value="25">여행코스</option>
            <option value="28">레포츠</option>
            <option value="32">숙박</option>
            <option value="38">쇼핑</option>
            <option value="39">음식점</option>
          </select>
        </div>
        <div class="col-lg-6 col-md-6 col-12 p-2 d-flex">
          <input
            class="form-control me-3 ps-3"
            type="search"
            name="keyword"
            id="search-keyword"
            placeholder="검색어 입력"
            aria-label="검색어 입력"
            v-model="keywordSelectedOption" />
          <button
            class="btn btn-outline-primary btn-width"
            id="btn-search"
            @click="search">
            검색
          </button>
        </div>
      </div>
    </div>
    <div
      id="div-map"
      class="flex-grow-1 d-flex align-items-center justify-content-center">
      <MapComponent :attractions="attractions"></MapComponent>
    </div>
    <div>
      <AttractionSideBar :attractions="attractions"></AttractionSideBar>
    </div>
    <AttractionModal />
  </div>
</template>

<style scoped>
.btn-width {
  min-width: 80px;
}
</style>
