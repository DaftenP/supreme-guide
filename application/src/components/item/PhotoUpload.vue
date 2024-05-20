<template>
  <div class="image-box">
    <div v-if="imagePreview">
      <div>
        <img :src="imagePreview" alt="preview" :style="{ width, height }" />
      </div>
      <button @click="handleImageRemove">재업로드</button>
    </div>
    <div v-else>
      <input type="file" accept="image/*" @change="handleImageChange" />
      <p>등록하고싶은 사진을 첨부해주세요!</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    width: {
      type: String,
      default: "120px",
    },
    height: {
      type: String,
      default: "100%",
    },
  },
  data() {
    return {
      selectedFile: null,
      imagePreview: null,
    };
  },
  methods: {
    handleImageChange(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onloadend = () => {
          const base64String = reader.result;
          this.selectedFile = file; // 변경점
          this.imagePreview = base64String;
          console.log(base64String);
        };
        reader.readAsDataURL(file);
      }
    },
    handleImageRemove() {
      URL.revokeObjectURL(this.imagePreview);
      this.selectedFile = null;
      this.imagePreview = null;
    },
  },
};
</script>

<style scoped>
.image-box {
  /* 스타일을 여기에 추가 */
}
</style>
