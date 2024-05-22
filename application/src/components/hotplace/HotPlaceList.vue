<!-- tailwind 카드 형식 -> 한 줄에 3개씩 -->
<script setup>
import { ref, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import noAuthClient from "@/api/noAuthClient";
import AOS from "aos";
import "aos/dist/aos.css";

AOS.init({
  duration: 1000,
});

const router = useRouter();
const userStore = useUserStore();
const hotplaces = ref([]);
const key = ref("");
const word = ref("");
const IMAGE_URL = import.meta.env.VITE_IMAGE_BASE_URL;
console.log(IMAGE_URL);

const searchCondition = ref({
  countPerPage: 6,
  key: "",
  word: "",
  currentPage: 1,
  offset: 0,
  limit: true,
});

const currentPage = ref(1);
const totalPages = ref(0);

const goWrite = () => {
  router.push({ name: "HotplaceWrite" });
};

const goDetail = (id) => {
  router.push({
    name: "HotplaceDetail",
    params: {
      hotplaceId: id,
    },
  });
};



const searchArticle = () => {
  // 검색어 입력 상태에서만 검색 조건 업데이트하도록
  if (word.value !== "") {
    searchCondition.value.key = key.value;
    searchCondition.value.word = word.value;
  } else {
    // 검색어가 입력되지 않은 경우는 초기화
    searchCondition.value.key = "";
    searchCondition.value.word = "";
  }
  searchCondition.value.currentPage = 1;
  fetchHotPlaces();
};

const fetchHotPlaces = async () => {
  try {
    let url = `${import.meta.env.VITE_API_BASE_URL}/hotplace/all`;
    if (searchCondition.value.key && searchCondition.value.word) {
      if (searchCondition.value.key === "writer") {
        searchCondition.value.key = "hotplace_writer";
      } else if (searchCondition.value.key === "comment") {
        searchCondition.value.key = "hotplace_comment";
      } else if (searchCondition.value.key === "hotplaceName") {
        searchCondition.value.key = "hotplace_name";
      }
      url += `?key=${searchCondition.value.key}&word=${searchCondition.value.word}`;
    }
    const res = await noAuthClient({
      method: "get",
      url: url,
    });
    hotplaces.value = res.data || [];
    const startIndex =
      (searchCondition.value.currentPage - 1) *
      searchCondition.value.countPerPage;
    const endIndex = startIndex + searchCondition.value.countPerPage;
    hotplaces.value = res.data
      .slice(startIndex, endIndex)
      .map((item, index) => ({
        ...item,
        index: index + startIndex + 1,
        image: `/${IMAGE_URL}/${item.image}`,
      }));
    totalPages.value = Math.ceil(
      res.data.length / searchCondition.value.countPerPage
    );
    console.log(res.data);

    // pagination 처리
  } catch (error) {
    console.log(error);
    alert("리스트를 불러오는 데 실패했습니다.");
  }
};

const changePage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    searchCondition.value.currentPage = page;
    currentPage.value = page;
    fetchHotPlaces();
  }
};

onBeforeMount(fetchHotPlaces);
</script>

<template>
  <div class="container">
    <div class="row justify-content-center my-4">
      <div class="col-lg-8">
        <h2 class="text-center font-style">HotPlace</h2>
      </div>
      <div class="row align-self-center mb-2">
        <div class="d-flex column p-2">
          <div class="col-6"></div>
          <div class="col-6 d-flex">
            <select class="form-select" v-model="key">
              <option value="qna_title">제목</option>
              <option value="qna_content">내용</option>
              <option value="qna_writer">작성자</option>
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

        <v-row class="mt-8">
          <v-col
            v-for="hotplace in hotplaces"
            :key="hotplace.hotplaceId"
            cols="12"
            md="4">
            <v-card :data-aos="'fade-up'">
              <v-img
                :src="hotplace.image"
                cover
                height="200"
                aspect-ratio="2.75"
                alt="Hotplace Image">
              </v-img>
              <v-card-title>
                <span class="headline">{{ hotplace.hotplaceName }}</span>
              </v-card-title>
              <v-card-text id="font-small">{{ hotplace.comment }}</v-card-text>
              <v-card-actions>
                <v-btn
                  color="primary"
                  text
                  @click="goDetail(hotplace.hotplaceId)">
                  자세히 보기
                </v-btn>
                <v-spacer></v-spacer>
                <v-subheader>{{ hotplace.writer }}</v-subheader>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </div>
    </div>
    <div class="col-md-2 text-start">
      <button
        type="button"
        class="btn btn-sm"
        id="font-title"
        v-if="userStore.userId != ''"
        @click="goWrite">
        핫플레이스 등록
        <font-awesome-icon
          :icon="['fas', 'arrow-right']"
          style="color: black" />
      </button>
    </div>

    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <!-- 이전 버튼 -->
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <button
            class="page-link"
            @click="changePage(currentPage - 1)"
            aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </button>
        </li>
        <!-- 페이지 번호 -->
        <li
          class="page-item"
          v-for="pageNumber in totalPages"
          :key="pageNumber"
          :class="{ active: currentPage === pageNumber }">
          <button class="page-link" @click="changePage(pageNumber)">
            {{ pageNumber }}
          </button>
        </li>
        <!-- 다음 버튼 -->
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
.container {
  max-width: 1000px;
}

.font-style {
  font-family: "CustomFont";
  font-size: 50px;
}

#font-small {
  font-family: "CustomFont3";
  font-size: 20px;
}

#font-title {
  font-family: "CustomFont";
  font-size: 20px;
}



</style>
