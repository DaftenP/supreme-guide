<script setup>
import { ref, computed, defineProps, defineEmits } from "vue";
import AttractionListItem from "@/components/item/AttractionListItem.vue";
import { useMapStore } from "@/stores/map";
import { getAttraction } from "@/api/attraction";

const mapStore = useMapStore();
const props = defineProps({
  attractions: Object,
  checkboxes: Object,
});
const emit = defineEmits(["updateCheckbox"]);

const getModal = (id) => {
  getAttraction(
    id,
    (resp) => {
      mapStore.attractionInfo = resp.data;
    },
    (err) => {
      console.log(err);
    }
  );
};
const updateCheckbox = (id, checked) => {
  emit("updateCheckbox", id, checked);
};
</script>

<template>
  <button
    class="btn-sidebar"
    type="button"
    data-bs-toggle="offcanvas"
    data-bs-target="#offcanvasScrolling"
    aria-controls="offcanvasScrolling">
    >
  </button>

  <div
    class="offcanvas offcanvas-start"
    data-bs-scroll="true"
    data-bs-backdrop="false"
    tabindex="-1"
    id="offcanvasScrolling"
    aria-labelledby="offcanvasScrollingLabel">
    <div class="offcanvas-header">
      <h5 class="offcanvas-title" id="offcanvasScrollingLabel">검색 결과</h5>
      <button
        type="button"
        class="btn-close text-reset"
        data-bs-dismiss="offcanvas"
        aria-label="Close"></button>
    </div>

    <div class="offcanvas-body">
      <div>
        <span v-for="checkbox in checkboxes" :key="checkbox.id">
          <input
            type="checkbox"
            :id="checkbox.id"
            :checked="checkbox.checked"
            @change="updateCheckbox(checkbox.id, $event.target.checked)" />
          <label :for="`checkbox-${checkbox.id}`">{{ checkbox.label }}</label>
        </span>
      </div>
      <div>
        <AttractionListItem
          v-for="(attraction, index) in attractions"
          :key="index"
          :attraction="attraction"
          @detail-view="getModal"></AttractionListItem>
      </div>
    </div>
  </div>
</template>

<style scoped>
.btn-sidebar {
  background-color: #d9d9d9;
  display: inline-block;
  cursor: pointer;
  color: #ffffff;
  font-family: Arial;
  font-size: 16px;
  padding: 32px 2px;
  text-decoration: none;
  text-shadow: 0px 1px 0px #2f6627;
  border: none;
  position: fixed;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  z-index: 1;
}
.btn-sidebar:hover {
  background-color: #bfbfbf;
}
.offcanvas {
  --bs-offcanvas-width: min(600px, 35%);
}
</style>
