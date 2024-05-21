<!-- tailwind 카드 형식 -> 한 줄에 3개씩 -->
<script setup>
import { ref, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";
import noAuthClient from "@/api/noAuthClient";

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
    <div class="row justify-content-center">
      <div class="col-lg-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">HotPlace</mark>
        </h2>
      </div>
      <div class="row align-self-center mb-2">
        <div class="col-md-2 text-start">
          <button
            type="button"
            class="btn btn-outline-primary btn-sm"
            v-if="userStore.userId != ''"
            @click="goWrite">
            핫플레이스 등록
          </button>
        </div>
        <div class="col-md-5 offset-5">
          <form class="d-flex">
            <div class="input-group input-group-sm">
              <select class="form-select" v-model="key">
                <option value="hotplaceName">제목</option>
                <option value="comment">내용</option>
                <option value="writer">작성자</option>
              </select>
              <input
                type="text"
                class="form-control"
                placeholder="검색어"
                v-model="word" />
              <button class="btn btn-dark" type="button" @click="searchArticle">
                검색
              </button>
            </div>
          </form>
        </div>

        <div class="bg-white">
          <div class="mx-auto max-w-7xl px-4 py-16 sm:px-6 sm:py-24 lg:px-8">
            <div
              class="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-3 xl:gap-x-8">
              <div
                v-for="hotplace in hotplaces"
                :key="hotplace.hotplaceId"
                class="group relative">
                <div
                  class="aspect-w-1 aspect-h-1 w-full overflow-hidden rounded-md bg-gray-200 lg:aspect-none group-hover:opacity-75 lg:h-80">
                  <img
                    :src="hotplace.image"
                    :alt="hotplace.hotplaceName"
                    class="h-full w-full object-cover object-center lg:h-full lg:w-full" />
                </div>
                <div class="mt-4 flex justify-between">
                  <div>
                    <h3 class="text-sm text-gray-700">
                      <a @click="goDetail(hotplace.hotplaceId)">
                        <span
                          aria-hidden="true"
                          class="absolute inset-0"></span>
                        {{ hotplace.hotplaceName }}
                      </a>
                    </h3>
                    <p class="mt-1 text-sm text-gray-500">
                      {{ hotplace.comment }}
                    </p>
                  </div>
                  <p class="text-sm font-medium text-gray-900">
                    {{ hotplace.writer }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
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
  padding: 1rem;
}
</style>
