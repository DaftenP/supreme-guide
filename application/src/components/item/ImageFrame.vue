<template>
  <div class="frame">
    <img :src="imageSrc" alt="핫플레이스 이미지" class="hotplace-image" />
  </div>
</template>

<script setup>
defineProps({
  imageSrc: String,
});
</script>

<style scoped>
.hotplace-image {
  width: 100%;
  height: 100%;
  margin-bottom: 20px;
}

.frame {
  width: 300px; /* 액자의 너비 */
  height: 400px; /* 액자의 높이 */
  border: 10px solid #ddd0c7; /* 액자의 테두리 */
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.5); /* 액자에 그림자 추가 */
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden; /* 액자 밖으로 이미지가 나가지 않도록 */
}

.frame img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain; /* 이미지가 액자 안에 꽉 차게 */
}
</style>
