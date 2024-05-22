<script setup>
import { ref, onMounted } from "vue";
import AOS from "aos";
import "aos/dist/aos.css";

const images = ref([
  "/images/home2.jpg",
  "/images/home3.jpg",
  "/images/home4.jpg",
  "/images/home4.jpg",
]);

let currentIndex = ref(0);

const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % images.value.length;
};

onMounted(() => {
  setInterval(nextSlide, 3000);
  AOS.init();
});

const boxes = [
  { title: "박스 1", content: "내용 1" },
  { title: "박스 2", content: "내용 2" },
  { title: "박스 3", content: "내용 3" },
  { title: "박스 4", content: "내용 4" },
];
const posts = [
  {
    id: 1,
    title: "Boost your conversion rate",
    href: "#",
    description:
      "Illo sint voluptas. Error voluptates culpa eligendi. Hic vel totam vitae illo. Non aliquid explicabo necessitatibus unde. Sed exercitationem placeat consectetur nulla deserunt vel. Iusto corrupti dicta.",
    date: "Mar 16, 2020",
    datetime: "2020-03-16",
    category: { title: "Marketing", href: "#" },
    author: {
      name: "Michael Foster",
      role: "Co-Founder / CTO",
      href: "#",
      imageUrl:
        "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    },
  },
  // More posts...
  {
    id: 2,
    title: "Boost your conversion rate",
    href: "#",
    description:
      "Illo sint voluptas. Error voluptates culpa eligendi. Hic vel totam vitae illo. Non aliquid explicabo necessitatibus unde. Sed exercitationem placeat consectetur nulla deserunt vel. Iusto corrupti dicta.",
    date: "Mar 16, 2020",
    datetime: "2020-03-16",
    category: { title: "Marketing", href: "#" },
    author: {
      name: "Michael Foster",
      role: "Co-Founder / CTO",
      href: "#",
      imageUrl:
        "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    },
  },
  {
    id: 3,
    title: "Boost your conversion rate",
    href: "#",
    description:
      "Illo sint voluptas. Error voluptates culpa eligendi. Hic vel totam vitae illo. Non aliquid explicabo necessitatibus unde. Sed exercitationem placeat consectetur nulla deserunt vel. Iusto corrupti dicta.",
    date: "Mar 16, 2020",
    datetime: "2020-03-16",
    category: { title: "Marketing", href: "#" },
    author: {
      name: "Michael Foster",
      role: "Co-Founder / CTO",
      href: "#",
      imageUrl:
        "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    },
  },
  {
    id: 4,
    title: "Boost your conversion rate",
    href: "#",
    description:
      "Illo sint voluptas. Error voluptates culpa eligendi. Hic vel totam vitae illo. Non aliquid explicabo necessitatibus unde. Sed exercitationem placeat consectetur nulla deserunt vel. Iusto corrupti dicta.",
    date: "Mar 16, 2020",
    datetime: "2020-03-16",
    category: { title: "Marketing", href: "#" },
    author: {
      name: "Michael Foster",
      role: "Co-Founder / CTO",
      href: "#",
      imageUrl:
        "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
    },
  },
];
</script>

<template>
  <div class="relative home-view-fullscreen">
    <div class="relative slider-container">
      <img
        v-for="(image, index) in images"
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
        v-for="(image, index) in images"
        :key="index"
        class="box"
        :data-aos="'fade-up'"
        :data-aos-delay="index * 100">
        <img :src="image" alt="image" class="box-image" />
        <h3 class="box-title">hi</h3>
      </div>
    </div>
  </div>

  <div class="bg-animation py-24 sm:py-32">
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <div class="mx-auto max-w-2xl lg:mx-0">
        <h2
          class="custom-fonts text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl"
          data-aos="fade-up">
          관광명소를 추천해드려요
        </h2>
        <p
          class="mt-2 text-lg leading-8 text-gray-600"
          data-aos="fade-up"
          data-aos-delay="100">
          Learn how to grow your business with our expert advice.
        </p>
      </div>
      <div class="flex justify-center space-x-4 mb-8">
        <button
          class="bg-gradient-to-r from-purple-400 to-purple-600 hover:bg-gradient-to-r hover:from-purple-600 hover:to-purple-400 text-white font-bold py-2 px-4 rounded"
          data-aos="fade-up"
          data-aos-delay="200">
          제주도
        </button>
        <button
          class="bg-gradient-to-r from-purple-400 to-purple-600 hover:bg-gradient-to-r hover:from-purple-600 hover:to-purple-400 text-white font-bold py-2 px-4 rounded"
          data-aos="fade-up"
          data-aos-delay="300">
          부산
        </button>
        <button
          class="bg-gradient-to-r from-purple-400 to-purple-600 hover:bg-gradient-to-r hover:from-purple-600 hover:to-purple-400 text-white font-bold py-2 px-4 rounded"
          data-aos="fade-up"
          data-aos-delay="400">
          서울
        </button>
      </div>
      <div
        class="mx-auto mt-10 grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 border-t border-gray-200 pt-10 sm:mt-16 sm:pt-16 lg:mx-0 lg:max-w-none lg:grid-cols-3">
        <article
          v-for="post in posts"
          :key="post.id"
          class="flex max-w-xl flex-col items-start justify-between"
          data-aos="fade-up"
          :data-aos-delay="(post.id - 1) * 100">
          <div class="flex items-center gap-x-4 text-xs">
            <time :datetime="post.datetime" class="text-gray-500">{{
              post.date
            }}</time>
            <a
              :href="post.category.href"
              class="relative z-10 rounded-full bg-gray-50 px-3 py-1.5 font-medium text-gray-600 hover:bg-gray-100"
              >{{ post.category.title }}</a
            >
          </div>
          <div class="group relative">
            <h3
              class="mt-3 text-lg font-semibold leading-6 text-gray-900 group-hover:text-gray-600">
              <a :href="post.href">
                <span class="absolute inset-0" />
                {{ post.title }}
              </a>
            </h3>
            <p class="mt-5 line-clamp-3 text-sm leading-6 text-gray-600">
              {{ post.description }}
            </p>
          </div>
          <div class="relative mt-8 flex items-center gap-x-4">
            <img
              :src="post.author.imageUrl"
              alt=""
              class="h-10 w-10 rounded-full bg-gray-50" />
            <div class="text-sm leading-6">
              <p class="font-semibold text-gray-900">
                <a :href="post.author.href">
                  <span class="absolute inset-0" />
                  {{ post.author.name }}
                </a>
              </p>
              <p class="text-gray-600">{{ post.author.role }}</p>
            </div>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home-view-fullscreen {
  width: 100%;
  height: 90vh; /* 높이를 조정하여 메인 화면을 설정합니다 */
  position: relative;
}

.frame {
  border: 20px solid aquamarine; /* 액자 스타일의 테두리 */
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.5); /* 그림자 효과 */
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

.box-container {
  position: absolute;
  bottom: -10%; /* 이미지와 겹치도록 박스 위치 조정 */
  left: 50%;
  transform: translate(-50%, 30%); /* Y 축으로 90% 이동하여 겹침 효과 */
  display: flex;
  justify-content: space-around;
  width: 80%;
  z-index: 20;
}

.box {
  background: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
  flex: 1;
  margin: 0 10px;
  height: 250px;
}

.box h3 {
  font-size: 1.25rem;
  margin-bottom: 10px;
}

.box p {
  font-size: 1rem;
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

/* 배경 애니메이션 넣기 */
.bg-animation {
  position: relative;
}

@keyframes backgroundAnimation {
  0% {
    background-color: #ffffff; /* 시작 색상 */
  }
  50% {
    background-color: #bdf0e3; /* 중간 색상 */
  }
  100% {
    background-color: #ffffff; /* 끝 색상 */
  }
}

.bg-animation::before {
  /* content: ""; */
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #ffffff; /* 초기 배경 색상 */
  animation: backgroundAnimation 10s infinite alternate; /* 애니메이션 지정 */
}
</style>
