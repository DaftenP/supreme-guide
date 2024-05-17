<script setup>
import { ref } from "vue";
import MapComponent from "../commons/MapComponent.vue";
import AttractionListItem from "@/components/item/AttractionListItem.vue";
import AttractionModal from "../modal/AttractionModal.vue";
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

const showModal = (id) => {
  mapStore.modalCheck = true;
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
  <div class="search-form wow fadeInUp">
    <div class="row">
      <div class="col-lg-2 col-md-2 col-6 p-0">
        <div class="search-input">
          <label for="location">
            <i class="lni lni-map-marker theme-color" />
          </label>
          <select
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
      </div>
      <div class="col-lg-2 col-md-2 col-6 p-0">
        <div class="search-input">
          <label for="location">
            <i class="lni lni-map-marker theme-color" />
          </label>
          <select
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
      </div>
      <div class="col-lg-2 col-md-2 col-12 p-0">
        <div class="search-input">
          <label for="category">
            <i class="lni lni-grid-alt theme-color"></i>
          </label>
          <select
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
      </div>
      <div class="col-lg-4 col-md-4 col-12 p-0">
        <div class="search-input">
          <label for="keyword">
            <i class="lni lni-search-alt theme-color"></i>
          </label>
          <input
            type="search"
            name="keyword"
            id="search-keyword"
            placeholder="검색어 입력"
            aria-label="검색어 입력"
            v-model="keywordSelectedOption" />
        </div>
      </div>
      <div class="col-lg-2 col-md-2 col-12 p-0">
        <div class="search-btn button">
          <button class="btn btn-primary" id="btn-search" @click="search">
            <i class="lni lni-search-alt"></i> 검색
          </button>
        </div>
      </div>
    </div>
  </div>
  <div>
    <MapComponent :attractions="attractions"></MapComponent>
  </div>
  <AttractionModal />
  <div>
    <AttractionListItem
      v-for="(attraction, index) in attractions"
      :key="index"
      :attraction="attraction"
      @detail-view="showModal"></AttractionListItem>
  </div>
</template>

<style scoped></style>
