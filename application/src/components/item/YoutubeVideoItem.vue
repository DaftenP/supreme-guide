<script setup>
import { ref, onMounted } from "vue";

const props = defineProps({
  videos: Object,
  type: String,
});

const container = ref();

onMounted(() => {
  adjustGridColumns();
  window.onresize = adjustGridColumns;
});

const adjustGridColumns = () => {
  const containerWidth = container.value.offsetWidth;
  const columnsClass = `grid-cols-${
    containerWidth > 800 ? "3" : containerWidth > 500 ? "2" : "1"
  }`;

  container.value.className = container.value.className.replace(
    /\bgrid-cols-\d\b/g,
    ""
  );
  container.value.classList.add(columnsClass);
};
</script>

<template>
  <div ref="container" class="mx-auto max-w-2xl p-2">
    <article
      v-for="(video, index) in videos.items"
      :key="index"
      class="d-flex max-w-xl flex-col items-start justify-between border-b pt-2 pb-2">
      <iframe
        :width="type ? 432 : 320"
        :height="type ? 243 : 180"
        :src="`https://www.youtube.com/embed/${video.id.videoId}`"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen></iframe>
      <div class="flex items-center gap-x-4 text-xs">
        <a
          class="me-0 pe-0 a-title mt-2"
          :href="`https://youtube.com/channel/${video.snippet.channelId}`"
          target="_blank">
          <span class="p-2">{{ video.snippet.channelTitle }}</span>
        </a>
        <time
          :datetime="video.snippet.publishTime"
          class="text-gray-500 ps-4 border-s mt-2"
          >{{ video.snippet.publishTime.slice(0, 10) }}</time
        >
      </div>
    </article>
  </div>
</template>

<style scoped>
::v-deep b {
  background-color: rgb(255, 255, 164);
}
a {
  background-color: rgba(0, 0, 0, 0);
}
</style>
