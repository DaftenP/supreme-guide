<script setup>
import { ref } from "vue";
import { postPrompt } from "@/api/chatGPT";

const word = ref();
const response = ref({
  id: "chatcmpl-9RnU6MqG2n3jhEL89zepV1SZYGdEW",
  object: "chat.completion",
  created: 1716412102,
  model: "gpt-3.5-turbo-0125",
  choices: [
    {
      index: 0,
      message: {
        role: "assistant",
        content:
          "<h3>제주도 맛집 추천</h3>\n<ul>\n    <li><strong>1. 한림 포장마차</strong>: 한림 마을에 위치한 포장마차로, 현지인들이 자주 찾는 곳이에요. 특히 해물부침과 갈치구이가 유명해요.</li>\n    <li><strong>2. 용두암식당</strong>: 용두암 근처에 위치한 이색적인 식당으로, 바다 전망을 즐기면서 신선한 해산물을 맛볼 수 있어요.</li>\n    <li><strong>3. 오조포크</strong>: 제주시에 위치한 이자카야로, 현지 맛집으로 손꼽히는 곳이에요. 특히 돼지고기 요리가 맛있어요.</li>\n    <li><strong>4. 제주김만복 본점</strong>: 제주에서 유명한 김밥 전문점으로, 신선한 재료와 정갈한 맛으로 손님들을 매료시킵니다.</li>\n    <li><strong>5. 봉개동해변식당</strong>: 바다를 바라보며 식사를 즐길 수 있는 이색적인 식당으로, 특히 해산물 요리가 인기가 많아요.</li>\n</ul>",
      },
      logprobs: null,
      finish_reason: "stop",
    },
  ],
  usage: {
    prompt_tokens: 140,
    completion_tokens: 400,
    total_tokens: 540,
  },
  system_fingerprint: null,
});
const send = () => {
  response.value = `내용<br><a onclick="evt">버튼</a>내용<br>내용<br>내용<br>내용<br>내용<br>내용<br>내용`;
};
const evt = () => {
  alert("event");
};
</script>

<template>
  <div id="main" class="bg-animation py-24 sm:py-32">
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <div class="mx-auto max-w-2xl lg:mx-0" style="margin-top: 20vh">
        <h2
          class="custom-fonts text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl"
          data-aos="fade-up">
          관광명소를 추천해드려요
        </h2>
        <p
          class="mt-2 text-lg leading-8 text-gray-600"
          data-aos="fade-up"
          data-aos-delay="100">
          AI가 추천하는 여행지로 떠나볼까요?
        </p>
      </div>
      <div class="flex justify-center space-x-4 mb-8">
        <input
          class="form-control"
          type="text"
          v-model="word"
          data-aos="fade-up"
          data-aos-delay="200"
          placeholder="제주도 맛집 추천해줘" />
        <button class="btn btn-primary" :disabled="word == ''" @click="send">
          O
        </button>
      </div>
      <div v-if="response" v-html="response.choices[0].message.content"></div>
      <br /><br /><br /><br /><br />
    </div>
  </div>
</template>

<style scoped></style>
