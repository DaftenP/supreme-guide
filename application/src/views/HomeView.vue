<script setup>
import { ref, onMounted } from "vue";

const images = ref([
  "/images/home2.jpg",
  "/images/home3.jpg",
  "/images/home4.jpg",
]);

let currentIndex = ref(0);

const nextSlide = () => {
  // console.log(images.value.length);
  // console.log(images.value[currentIndex.value]);
  currentIndex.value = (currentIndex.value + 1) % images.value.length;
};

onMounted(() => {
  setInterval(nextSlide, 3000);
});
</script>

<template>
  <div class="home-container">
    <div class="top-box">
      <div class="top-content">
        <div class="slider-container">
          <div class="slider">
            <img
              v-for="(image, index) in images"
              :key="index"
              :src="image"
              class="home-image"
              :class="{ active: index === currentIndex }"
              :style="{ width: '100%', height: 'auto' }" />
            <div class="overlay-text">
              <h1 class="heading">Enjoy Trip!</h1>
              <br />
              <p class="sub-heading">
                꿈꾸던 여행지를 찾아보세요! <br />어디를 가고 싶으신가요?
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 하단 박스 -->
    <!-- <div class="bottom-box">
      <ul class="menu-list">
        <li><i class="fas fa-home"></i>Q&A 게시판</li>
        <li><i class="fas fa-info-circle"></i> About Us</li>
      </ul>
    </div> -->
  </div>
</template>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;

  margin-top: 10px;
  /* height: 100vh; */
}

.top-box {
  flex: 1;
  /* background-color: #f0f0f0; */
  /* padding: 20px; */
}

.bottom-box {
  background-color: #ffffff;
  padding: 20px;
}

.top-content {
  text-align: center;
}

.home-image {
  max-width: 100%;
  height: auto;
  /* border-radius: 3%; */
  /* filter: blur(2px); */
}

.menu-list {
  list-style: none;
  padding: 0;
}

.menu-list li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.menu-list li i {
  margin-right: 10px;
  border: 1px solid #333;
  border-radius: 50%;
  padding: 8px;
}

.overlay-text {
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
  text-align: center;
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

.slider-container {
  width: 100%;
  height: 600px;
  overflow: hidden;
}

.slider {
  display: flex;
  transition: transform 0.5s ease;
}

.slider img {
  width: 100%;
  display: none;
  opacity: 0;
  transition: opacity 1s ease;
}

.slider img.active {
  display: block;
  opacity: 1;
}

.slider img:not(.active) {
  opacity: 0;
}
</style>
