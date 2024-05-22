<script setup>
import { ref, onBeforeMount, computed } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import Cookies from "vue-cookies";
import noAuthClient from "@/api/noAuthClient";
import { useUserStore } from "@/stores/userStore";
import { getAllArticle } from "@/api/trip";

const userStore = useUserStore();
const router = useRouter();
const trips = ref([]);
const key = ref("");
const word = ref("");
const trip = ref({});
const currentPage = ref(1);
const totalPages = ref(0);
const searchCondition = ref({
  countPerPage: 5,
  currentPage: 1,
  offset: 0,
  limit: true,
});

const movePage = () => {
  router.push({ name: "TripWrite" });
};

const changePage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    searchCondition.value.currentPage = page;
    currentPage.value = page; // 현재 페이지 업데이트
    getTrips();
  }
};

// searchArticle구현
const searchArticle = () => {
  // 검색어 입력 상태에서만 검색 조건을 업데이트하도록 추가
  if (word.value !== "") {
    searchCondition.value.key = key.value;
    searchCondition.value.word = word.value;
  } else {
    // 검색어가 입력되지 않은 경우 검색 조건 초기화
    searchCondition.value.key = "";
    searchCondition.value.word = "";
  }
  // 검색 버튼 클릭 시 currentPage를 1로 초기화하여 첫 페이지부터 조회하도록 처리
  searchCondition.value.currentPage = 1;
  getTrips();
};

const getTrips = () => {
  getAllArticle(
    searchCondition.value,
    (resp) => {
      trips.value = resp.data;
      console.log(trips.value);
    },
    (err) => {
      console.log(err);
      alert("리스트를 불러오는 데에 실패 하였습니다.");
    }
  );
};

onBeforeMount(getTrips);

const goDetail = (id) => {
  console.log(id);
  router.push({
    name: "TripDetail",
    params: {
      tripId: id,
    },
  });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center my-4">
      <div class="col-lg-8">
        <h2 class="text-center font-style">여행</h2>
      </div>
      <div class="d-flex column p-2">
        <div class="col-6"></div>
        <div class="col-6 d-flex">
          <select class="form-select" v-model="key">
            <option value="trip_title">제목</option>
            <option value="trip_content">내용</option>
            <option value="trip_writer">작성자</option>
          </select>
          <input
            type="text"
            class="form-control"
            placeholder="검색어를 입력해 주세요"
            v-model="word" />
          <button class="btn btn-dark" type="button" @click="searchArticle">
            <font-awesome-icon
              :icon="['fas', 'magnifying-glass']"
              style="color: #fcfcfc" />
          </button>
        </div>
      </div>
      <div class="row">
        <div
          class="col-md-4 mb-4"
          v-for="(trip, index) in trips"
          :key="trip.tripId"
          :index="index">
          <div
            class="card h-100"
            @click="goDetail(trip.tripId)"
            :data-aos="'fade-up'"
            :data-aos-delay="index * 100">
            <div class="card-body">
              <h5 class="card-title">
                {{ trip.tripName }}
              </h5>

              <!-- {{ trip.tripContent }} -->
              <br />
              <br />
              <div class="card-text d-flex">
                <p>
                  {{
                    new Date(trip.tripCreateDate).toLocaleDateString("ko-KR")
                  }}
                </p>
              </div>
              <p>
                {{ trip.userId }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-2 text-start">
      <button
        type="button"
        class="btn btn-sm"
        id="font-small"
        v-if="userStore.userId != ''"
        @click="movePage">
        질문 등록
        <font-awesome-icon
          :icon="['fas', 'arrow-right']"
          style="color: black" />
      </button>
    </div>

    <nav aria-label="Page navigation example" class="mt-4">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <button
            class="page-link"
            @click="changePage(currentPage - 1)"
            aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </button>
        </li>
        <li
          class="page-item"
          v-for="pageNumber in totalPages"
          :key="pageNumber"
          :class="{ active: currentPage === pageNumber }">
          <button class="page-link" @click="changePage(pageNumber)">
            {{ pageNumber }}
          </button>
        </li>
        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
          <button
            class="page-link"
            @click="changePage(currentPage + 1)"
            aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </button>
        </li>
      </ul>
    </nav>
  </div>
</template>

<style scoped>
/* 페이지 처리 스타일 */
.pagination {
  margin-top: 20px;
}

.card {
  position: relative; /* 아이콘을 상대적으로 위치시키기 위해 설정 */
  transition: all 0.3s ease;
  cursor: pointer;
  max-width: 300px;
  border: none;
  border-radius: 10px;
  overflow: hidden;
  background-color: white;
  border: 1px solid #ddd;
}

.card-container {
  /* max-width: 800px; 원하는 최대 너비 설정 */
  /* margin: 0 auto; 가운데 정렬 */
  max-width: 1200px;
  flex-wrap: wrap;
  display: flex;
  gap: 1rem;
}

.font-style {
  font-family: "CustomFont";
  font-size: 50px;
}

#font-small {
  font-family: "CustomFont3";
  font-size: 20px;
}

.container {
  max-width: 1000px;
}

.card:hover {
  transform: translateY(-10px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.card-icon {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 2.5rem; /* 아이콘 크기 조정 */
  color: #63e6be;
}

.card-title {
  color: #333;
  font-weight: bold;
  font-family: "CustomFont";
  font-size: 25px;
}

.card-title:hover {
  text-decoration: underline;
}

.card-body {
  padding: 20px;
}

.card-text p {
  margin-bottom: 0;
  color: #666;
}

.card-text p:last-child {
  margin-top: 10px;
  color: #999;
}
</style>
