<script setup>
import { ref, computed, onBeforeMount, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import noAuthClient from "@/api/noAuthClient";
import Cookies from "vue-cookies";
import { useUserStore } from "@/stores/userStore";
import {
  getArticle,
  deleteArticle,
  postComment,
  putComment,
  deleteComment,
} from "@/api/trip";
import MapComponent from "@/components/commons/MapComponent.vue";
import { useMapStore } from "@/stores/map";
import { useTripStore } from "@/stores/trip";

const tripStore = useTripStore();
const mapStore = useMapStore();
const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

const comments = computed(() => tripStore.trip.tripComments);

// data
const tripId = route.params.tripId;

const computedStartDate = computed(() => {
  return tripStore.trip.tripStartDate;
});

const computedEndDate = computed(() => {
  return tripStore.trip.tripEndDate;
});

const newComment = reactive({
  content: "",
});

const goModify = () => {
  router.push({
    name: "TripModify",
    params: { tripId: tripStore.trip.tripId },
  });
};

const deleteTrip = async () => {
  await deleteArticle(
    tripId,
    (resp) => {
      alert("삭제가 완료 되었습니다.");
      goList();
    },
    (err) => {
      alert("삭제에 실패 하였습니다.");
    }
  );
};

const goList = () => {
  router.push({
    name: "TripList",
  });
};

const registComment = async () => {
  postComment(
    { ...newComment, articleId: tripId },
    (res) => {
      alert("댓글 등록이 완료 되었습니다.");
      tripStore.trip.tripComments.push({
        ...newComment,
        articleId: tripId,
        userId: userStore.userId,
        id: res.data,
      });
      newComment.content = "";
    },
    (err) => {
      console.log(err);
      alert("댓글 등록에 실패 하였습니다.");
    }
  );
};

// 댓글 삭제
const removeComment = async (commentId) => {
  deleteComment(
    commentId,
    (res) => {
      alert("댓글이 삭제 되었습니다.");
      tripStore.trip.tripComments = tripStore.trip.tripComments.filter(
        (el) => el.id != commentId
      );
    },
    (err) => {
      console.log(err);
      alert("댓글 삭제에 실패 하였습니다.");
    }
  );
};

const getTrip = () => {
  getArticle(
    tripId,
    (resp) => {
      tripStore.trip = resp.data;
      console.log(tripStore.trip);
    },
    (err) => {
      console.log(err);
      alert("게시글을 불러오는 데에 실패 하였습니다.");
    }
  );
};

// 게시글 상세 조회 및 댓글 목록 조회
onBeforeMount(async () => {
  await getTrip();
  if (tripStore.trip.tripItems) {
    mapStore.lat = tripStore.trip.tripItems[0].latitude;
    mapStore.lng = tripStore.trip.tripItems[0].longitude;
  }
});
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">여행 상세 페이지</mark>
        </h2>
      </div>
      <div class="col-lg-12">
        <h1>{{ tripStore.trip.tripName }}</h1>
        <div class="d-flex justify-content-between">
          <div>
            <label for="writer">작성자 :</label>
            {{ tripStore.trip.userName }}
          </div>
          <div>
            {{ tripStore.trip.tripCreateDate }}
            <label class="ms-1" for="view">조회 </label>
            {{ tripStore.trip.tripView }}
          </div>
        </div>
        <hr />
        <div class="d-flex row pd-0 mg-0">
          <div class="col-lg-6 col-md-12">
            <MapComponent :tripList="tripStore.trip.tripItems"></MapComponent>
          </div>
          <div class="col-lg-6 col-md-12">
            <div>
              {{ computedStartDate }}
            </div>
            <div>
              {{ computedEndDate }}
            </div>
            <div>
              <span>설명 : </span>
              <span v-html="tripStore.trip.tripContent"></span>
            </div>
            <div>
              <li
                v-for="item in tripStore.trip.tripItems"
                :key="item.contentId">
                {{ item.title }}
              </li>
            </div>
          </div>
        </div>
        <hr />
        <div class="d-flex justify-content-end">
          <a
            class="btn btn-outline-primary ms-2 pe-4 ps-4"
            v-if="tripStore.trip.userId === userStore.userId"
            href="#"
            @click.prevent="goModify"
            >수정</a
          >
          <a
            class="btn btn-outline-danger ms-2 pe-4 ps-4"
            v-if="tripStore.trip.userId == userStore.userId"
            @click="deleteTrip"
            >삭제</a
          >
          <button class="btn btn-outline-dark ms-2 pe-4 ps-4" @click="goList">
            목록
          </button>
        </div>
        <h3>댓글</h3>
        <form @submit.prevent="registComment">
          <div class="mb-3">
            <label for="newComment" class="form-label">댓글 작성</label>
            <textarea
              class="form-control"
              id="newComment"
              rows="3"
              v-model="newComment.content"></textarea>
          </div>
          <button type="submit" class="btn btn-primary">댓글 등록</button>
        </form>
        <hr />
        <ul class="list-group mb-3">
          <li
            class="list-group-item"
            v-for="comment in comments"
            :key="comment.tripCommentId">
            <strong>{{ comment.userId }}:</strong> {{ comment.content }}

            <button
              v-if="comment.userId === userStore.userId"
              class="btn btn-sm btn-outline-danger ms-2"
              @click="removeComment(comment.id)">
              삭제
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
#div-map {
  width: 100% !important;
  height: 600px !important;
}
</style>
