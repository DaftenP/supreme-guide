<template>
  <div class="image-box">
    <div v-if="imagePreview">
      <div class="image-preview">
        <img :src="imagePreview" alt="preview" :style="{ width, height }" />
      </div>
      <button @click="handleImageRemove" class="remove-btn">재업로드</button>
    </div>
    <div v-else class="upload-section">
      <label for="file-input" class="file-label">
        <i class="fas fa-upload"></i>
        등록하고 싶은 사진을 첨부해주세요!
      </label>
      <input type="file" id="file-input" accept="image/*" @change="handleImageChange" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  width: {
    type: String,
    default: "120px",
  },
  height: {
    type: String,
    default: "100%",
  },
});

const emit = defineEmits(['update:image']);

const selectedFile = ref(null);
const imagePreview = ref(null);

const handleImageChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onloadend = () => {
      const base64String = reader.result;
      selectedFile.value = file;
      imagePreview.value = base64String;
      emit("update:image", base64String);
    };
    reader.readAsDataURL(file);
  }
};

const handleImageRemove = () => {
  URL.revokeObjectURL(imagePreview.value);
  selectedFile.value = null;
  imagePreview.value = null;
  emit("update:image", null);
};
</script>

<style scoped>
.image-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  border: 2px dashed #ccc;
  padding: 20px;
  border-radius: 10px;
  background-color: #f9f9f9;
}

.image-preview img {
  border-radius: 10px;
  max-width: 100%;
  height: auto;
}

.remove-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #dc3545;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.remove-btn:hover {
  background-color: #c82333;
}

.upload-section .file-label {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 20px;
  border: 2px solid #007bff;
  border-radius: 5px;
  background-color: white;
  color: #007bff;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.upload-section .file-label:hover {
  background-color: #007bff;
  color: white;
}

.upload-section .file-label i {
  font-size: 20px;
}

#file-input {
  display: none;
}
</style>
