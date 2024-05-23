<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import AOS from "aos";
import noAuthClient from "@/api/noAuthClient";
import "aos/dist/aos.css";
import GPTRecommend from "@/components/item/GPTRecommend.vue";

const IMAGE_URL = import.meta.env.VITE_IMAGE_BASE_URL;

const hotplaces = ref([]);
const images = ref([]);
const images2 = ref([
  "/images/home2.jpg",
  "/images/home3.jpg",
  "/images/home4.jpg",
]);
let currentIndex = ref(0);
const router = useRouter();

onMounted(() => {
  images.value = [...images.value, ...images.value];
});

const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % images2.value.length;
};

// 핫플레이스 상세 페이지로 이동 함수
const navigateToHotplaceDetail = (hotplaceId) => {
  // vue-router를 사용하는 경우
  router.push({ name: "HotplaceDetail", params: { id: hotplaceId } });
};
// slider에 뿌릴 hotplace list가져오기
const fetchHotPlaces = async () => {
  try {
    let url = `${import.meta.env.VITE_API_BASE_URL}/hotplace/all`;

    const res = await noAuthClient({
      method: "get",
      url: url,
    });
    hotplaces.value = res.data || [];
    images.value = hotplaces.value.map(
      (hotplace) => `/${IMAGE_URL}/${hotplace.image}`
    );
    // const startIndex =
    //   (searchCondition.value.currentPage - 1) *
    //   searchCondition.value.countPerPage;
    // const endIndex = startIndex + searchCondition.value.countPerPage;
    // hotplaces.value = res.data
    //   .slice(startIndex, endIndex)
    //   .map((item, index) => ({
    //     ...item,
    //     index: index + startIndex + 1,
    //     image: `/${IMAGE_URL}/${item.image}`,
    //   }));
    // totalPages.value = Math.ceil(
    //   res.data.length / searchCondition.value.countPerPage
    // );
    console.log(res.data);

    // pagination 처리
  } catch (error) {
    console.log(error);
    alert("리스트를 불러오는 데 실패했습니다.");
  }
};

onMounted(() => {
  setInterval(nextSlide, 3000);
  AOS.init();
});

onBeforeMount(async () => {
  await fetchHotPlaces();
});
</script>

<template>
  <div class="relative home-view-fullscreen">
    <div class="relative slider-container">
      <img
        v-for="(image, index) in images2"
        :key="index"
        :src="image"
        class="home-image absolute inset-0 w-full h-full object-cover transition-opacity transform"
        :class="{
          'opacity-100': index === currentIndex,
          'opacity-0': index !== currentIndex,
        }"
        :style="{ zIndex: index === currentIndex ? 10 : 0 }" />
    </div>
    <div class="overlay-text z-20">
      <h1
        class="heading text-white font-bold text-5xl md:text-7xl custom-fonts">
        Enjoy Trip!
      </h1>
      <p
        class="sub-heading text-white text-lg md:text-xl custom-fonts"
        id="user-font">
        꿈꾸던 여행지를 찾아보세요! <br /><br />어디를 가고 싶으신가요?
      </p>
    </div>
    <div class="box-container z-20" style="margin-bottom: 5dvb">
      <div
        v-for="(hotplace, index) in hotplaces"
        :key="hotplace.id"
        class="box"
        @click="navigateToHotplaceDetail(hotplace.hotplaceId)">
        <img
          :src="`${IMAGE_URL}/${hotplace.image}`"
          alt="hotplace image"
          class="box-image" />
        <!-- <h3 class="box-title">{{ hotplace.hotplaceName }}</h3> -->
      </div>
    </div>
  </div>

  <div id="main" class="bg-animation home-view-fullscreen">
    <GPTRecommend></GPTRecommend>
  </div>
</template>

<style scoped>
.home-view-fullscreen {
  width: 100%;
  height: 90vh; /* 높이를 조정하여 메인 화면을 설정합니다 */
  position: relative;
}

.slider-container {
  width: 100%;
  height: 60vh; /* 높이를 조정합니다 */
  overflow: hidden;
  transition: transform 0.5s ease;
}

.slider {
  width: 100%;
}

.home-image {
  transition: opacity 1s ease, transform 0.5s ease; /* transform 추가 */
}

.overlay-text {
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
  text-align: center;
  z-index: 20; /* 이미지 위에 텍스트가 나타나도록 */
}

.heading {
  font-size: 3rem;
  font-family: "Arial", sans-serif;
  margin-bottom: 100px;
}

.sub-heading {
  font-size: 1.5rem;
  font-family: "Arial", sans-serif;
}

@keyframes slideInfinite {
  from {
    transform: translateX(0%);
  }
  to {
    transform: translateX(-100%);
  }
}
.box-container {
  overflow: hidden;
  position: absolute;
  bottom: 3%; /* 이미지와 겹치도록 박스 위치 조정 */
  left: 50%;
  transform: translate(-50%, 60%); /* Y 축으로 90% 이동하여 겹침 효과 */
  display: flex;
  justify-content: space-around;
  width: 100%;
  z-index: 20;
  animation: slideInfinite 20s linear infinite;
}

.box {
  background: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
  flex: 1;
  margin: 0 10px;
  height: auto; /* 박스 높이를 자동으로 조정 */
  max-height: 250px; /* 최소 높이 설정 */
  animation: slideToLeft 10s linear infinite;
  display: inline-block;
  white-space: nowrap;
}

.box-image {
  width: 100%; /* 이미지가 박스 크기에 맞게 조정 */
  height: auto; /* 이미지 비율 유지 */
}

.box h3 {
  font-size: 1.25rem;
  margin-bottom: 10px;
}

.box p {
  font-size: 1rem;
}

@media (max-width: 768px) {
  .box-container {
    flex-direction: column; /* 화면이 작아질 때 박스를 세로로 정렬 */
    width: 90%; /* 컨테이너 너비 조정 */
    bottom: 0;
    transform: translate(-50%, 0);
  }

  .box {
    margin-bottom: 20px; /* 박스 사이의 간격 조정 */
  }
}

.custom-font1 {
  font-family: "CustomFont";
  font-size: 50px;
}
.custom-fonts {
  font-family: "CustomFont3";
  font-size: 50px;
}

#user-font {
  font-size: 20px;
}

#main {
  background-color: azure;
  margin: 0;
  padding: 0;
}

.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 999;
  background-color: #ffffff;
  height: 88px; /* 헤더의 높이를 88px로 설정하세요 */
}
</style>
