<script setup>
import { ref, onMounted } from "vue";

const props = defineProps({
  articles: Object,
});

const container = ref(null);

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
      v-for="(post, index) in articles.items"
      :key="index"
      class="flex max-w-xl flex-col items-start justify-between border-b pt-2 pb-2">
      <div class="group relative">
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
      </div>
      <div class="relative mt-3 flex items-center gap-x-4">
        <div class="text-sm leading-6">
          <p class="font-semibold text-gray-900"></p>
          <p class="text-gray-600"></p>
        </div>
      </div>
      <div class="flex items-center gap-x-4 text-xs">
        <time :datetime="post.postdate" class="text-gray-500 pe-3 border-e">{{
          post.postdate
        }}</time>
        <a :href="`https://${post.bloggerlink}`" target="_blank">
          {{ post.bloggername }}
        </a>
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
