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
    <div class="content">
      <div class="title">
        <h1>{{ tripStore.trip.tripName }}</h1>
      </div>
      <div class="info">
        <span>작성자: {{ tripStore.trip.userName }}</span>
        <span>{{ tripStore.trip.tripCreateDate }}</span>
      </div>
      <!-- <font-awesome-icon 
      :icon="['fas', 'ellipsis-vertical']" style="color: #d3d5d9;"
      @click="toggleActions" /> -->
      <div class="body">
        <div class="d-flex row pd-0 mg-0">
          <div class="col-lg-12 col-md-12">
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
            <div class="col-lg-12 col-md-12">
              <MapComponent :tripList="tripStore.trip.tripItems"></MapComponent>
            </div>
          </div>
        </div>
      </div>
      <div class="actions">
        <button
          v-if="tripStore.trip.userId === userStore.userId"
          @click.prevent="goModify">
          수정
        </button>
        <button
          v-if="tripStore.trip.userId === userStore.userId"
          @click="deleteQna">
          삭제
        </button>
        <button @click="goList">목록</button>
      </div>

      <div class="divider"></div>
      <div class="comments">
        <h3>댓글</h3>
        <form @submit.prevent="registerComment">
          <textarea
            v-model="newComment.commentContent"
            placeholder="댓글 작성"></textarea>
          <button type="submit">댓글 등록</button>
        </form>
        <ul>
          <li v-for="comment in comments" :key="comment.tripCommentId">
            <div class="comment">
              <span
                :class="comment.harmful ? 'blur-sm cursor-pointer' : ''"
                @click="comment.harmful = false"
                class="select-none transition duration-500 ease-in-out">
                <strong>{{ comment.userId }}:</strong>
                {{ comment.content }}
              </span>
              <button
                v-if="comment.userId === userStore.userId"
                @click="deleteComment(comment.id)">
                삭제
              </button>
            </div>
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

.container {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: "Arial", sans-serif;
}

.content {
  background-color: #fff;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.title h1 {
  font-size: 24px;
  margin-bottom: 10px;
}

.info {
  font-size: 14px;
  color: #555;
  margin-bottom: 20px;
}

.info span {
  margin-right: 15px;
}

.body p {
  font-size: 16px;
  line-height: 1.6;
  white-space: pre-line;
}

.actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.actions button {
  margin-left: 10px;
  padding: 5px 10px;
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.actions button:hover {
  background-color: #f0f0f0;
}

.comments {
  margin-top: 30px;
}

.comments h3 {
  font-size: 20px;
  margin-bottom: 10px;
}

.comments form {
  margin-bottom: 20px;
}

.comments textarea {
  width: 100%;
  height: 60px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
}

.comments textarea:focus {
  outline: none;
  border-color: #aaa;
}

.comments button {
  padding: 5px 10px;
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.comments button:hover {
  background-color: #f0f0f0;
}

.comments ul {
  list-style: none;
  padding: 0;
}

.comments li {
  padding: 10px;
}

.divider {
  height: 2px;
  background: linear-gradient(to right, #ff7e5f, #feb47b);
  margin: 30px 0;
}
</style>
