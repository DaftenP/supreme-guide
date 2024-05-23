<script setup>
import { ref, onMounted } from "vue";
import { postPrompt } from "@/api/chatGPT";
import { getRegion } from "@/api/attraction";
import { useMapStore } from "@/stores/map";
import { getBlog } from "@/api/blogSearch";
import { getVideo } from "@/api/videoSearch";
import AttractionModal from "../modal/AttractionModal.vue";
import Modal from "bootstrap/js/dist/modal";

let myModal = null;
const mapStore = useMapStore();
const sidoSelectedOption = ref(0);
const gugunSelectedOption = ref(0);
const region = ref([]);
const gugun = ref([]);
const word = ref("제주도 맛집을 추천해줘.");
const title = ref();

const onLoading = ref(false);

const response = ref();
const send = () => {
  onLoading.value = true;
  let query = "";
  if (sidoSelectedOption.value == "input") {
    query = word.value;
  } else {
    const sidoName = region.value.filter(
      (e) => e.sidoCode == sidoSelectedOption.value
    );
    const gugunName = gugun.value.filter(
      (e) => e.gugunCode == gugunSelectedOption.value
    );
    query = `${sidoName[0].sidoName} ${gugunName[0].gugunName}에서 가볼만한 곳 5곳을 추천해줘.`;
  }
  postPrompt(
    query,
    (resp) => {
      response.value = resp.data;
      onLoading.value = false;
    },
    (err) => {
      alert("오류가 발생 하였습니다.");
      console.log(err);
      onLoading.value = false;
    }
  );
};

const sidoSelect = () => {
  if (sidoSelectedOption.value > 0) {
    gugunSelectedOption.value = 0;
    const index = region.value.findIndex(
      (opt) => opt.sidoCode == sidoSelectedOption.value
    );
    gugun.value = region.value[index].gugunList;
  } else {
    gugun.value = [];
    gugunSelectedOption.value = 0;
  }
};

const handleClick = (event) => {
  if (event.target.tagName === "A") {
    const keyword = event.target.innerText;
    title.value = keyword;
    getBlog(
      keyword,
      5,
      (resp) => {
        mapStore.blogList = resp.data;
      },
      (err) => {
        console.log(err);
      }
    );
    getVideo(
      keyword,
      (resp) => {
        mapStore.videoList = resp.data;
      },
      (err) => {
        console.log(err);
      }
    );

    if (myModal) {
      myModal.show(); // 모달 창을 엽니다.
    }
  }
};
onMounted(() => {
  const modalElement = document.getElementById("attractionModal");
  myModal = new Modal(modalElement);
});

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
  <div
    id="main"
    class="bg-animation flex flex-col items-center justify-center min-h-screen">
    <div class="bg-white shadow-lg rounded-lg p-8 mb-8 w-full max-w-4xl">
      <div class="text-center mt-15">
        <h2
          class="custom-fonts text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl"
          data-aos="fade-up">
          관광명소를 추천해드려요
        </h2>
        <p
          class="mt-2 text-lg leading-8 text-gray-600"
          data-aos="fade-up"
          data-aos-delay="100">
          AI가 추천하는 여행지로 떠나볼까요?
        </p>
      </div>
      <div class="flex justify-center space-x-4 mt-8">
        <div
          class="flex flex-wrap justify-center space-x-4 mt-8 mb-8"
          data-aos="fade-up"
          data-aos-delay="200">
          <div>
            <select
              v-if="sidoSelectedOption !== 'input'"
              class="block w-48 py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
              name="sido"
              id="search-area-sido"
              v-model="sidoSelectedOption"
              @change="sidoSelect">
              <option value="0" selected>시/도</option>
              <option
                v-for="sido in region"
                :key="sido.sidoCode"
                :sido="sido"
                :value="sido.sidoCode">
                {{ sido.sidoName }}
              </option>
              <option value="input">직접 입력</option>
            </select>
          </div>
          <div>
            <select
              v-if="sidoSelectedOption !== 'input'"
              class="block w-48 py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
              name="gugun"
              id="search-area-gugun"
              v-model="gugunSelectedOption">
              <option value="0" selected>구/군</option>
              <option
                v-for="g in gugun"
                :key="g.gugunCode"
                :g="g"
                :value="g.gugunCode">
                {{ g.gugunName }}
              </option>
            </select>
          </div>
          <div :class="sidoSelectedOption !== 'input' ? 'hidden' : ''">
            <input
              class="block w-96 py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
              type="text"
              v-model="word"
              placeholder="제주도 맛집 추천해줘" />
          </div>
          <button class="btn" @click="send">
            <span>
              <font-awesome-icon
                :icon="['fas', 'arrow-right']"
                style="color: #000000" />
            </span>
          </button>
        </div>
      </div>
      <div
        v-if="response"
        v-html="
          response.choices[0].message.content
            .replaceAll('-', `<br/>`)
            .replaceAll('<a>', `<a class=&quot;search-link text-lg mb-2&quot;>`)
            .replaceAll()
        "
        @click="handleClick"></div>
    </div>
  </div>

  <AttractionModal
    :title="title"
    :blogList="mapStore.blogList"
    :videoList="mapStore.videoList"></AttractionModal>
  <div
    id="loadingOverlay"
    class="fixed inset-0 bg-gray-500 bg-opacity-50 flex items-center justify-center z-50"
    :class="onLoading ? '' : 'hidden'">
    <div role="status">
      <svg
        :aria-hidden="onLoading"
        class="inline w-20 h-20 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600"
        viewBox="0 0 100 101"
        fill="none"
        xmlns="http://www.w3.org/2000/svg">
        <path
          d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
          fill="currentColor" />
        <path
          d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
          fill="currentFill" />
      </svg>
      <span class="sr-only">Loading...</span>
    </div>
  </div>
</template>

<style scoped>
.custom-font1 {
  font-family: "CustomFont";
  font-size: 50px;
}
.custom-fonts {
  font-family: "CustomFont3";
  font-size: 50px;
}
.none {
  display: none;
}
.block {
  display: block;
}
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
.animate-spin {
  animation: spin 1s linear infinite;
}
</style>
