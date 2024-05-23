import axios from "axios";
const { VITE_API_OPEN_AI_API_KEY } = import.meta.env;

export const postPrompt = (query, success, fail) => {
  const payload = {
    model: "gpt-3.5-turbo-0125",
    messages: [
      {
        role: "system",
        content:
          "너는 여행 정보 사이트에서 여행에 관련된 정보를 추천해주는 역할이야. 사실을 기반으로 대답해줘. 대답은 항목을 열거하며, 주소와 함께 간단한 설명을 해줘. 대답은 html 태그를 이용하여 내용을 구분 짓고, 이름은 a 태그로 감싸줘. 직접 링크를 걸어줄 필요는 없어.",
      },
      {
        role: "user",
        content: query,
      },
    ],
    temperature: 0.5,
  };
  const headers = {
    Authorization: `Bearer ${VITE_API_OPEN_AI_API_KEY}`,
  };
  axios
    .post("https://api.openai.com/v1/chat/completions", payload, { headers })
    .then(success)
    .catch(fail);
};
