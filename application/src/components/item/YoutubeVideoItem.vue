<script setup>
import { ref, onMounted } from "vue";

const props = defineProps({
  videos: Object,
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
  <div
    ref="container"
    class="mx-auto max-w-2xl border-t border-gray-200 mt-10 pt-2">
    <article
      v-for="(video, index) in videos.items"
      :key="index"
      class="flex max-w-xl flex-col items-start justify-between border-b pt-2 pb-2">
      <!-- <div class="group relative">
        <h4
          class="text-md font-semibold leading-6 text-gray-900 group-hover:text-gray-600">
          <a :href="post.link" target="_blank">
            <span class="absolute inset-0" />
            <span class="overflow-ellipsis" v-html="post.title" />
          </a>
        </h4>
        <p
          class="mt-1 line-clamp-3 text-sm leading-6 text-gray-500"
          v-html="post.description"></p>
      </div> -->
      <iframe
        width="320"
        height="180"
        :src="`https://www.youtube.com/embed/${video.id.videoId}`"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen></iframe>
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
