import axios from "axios";
const { VITE_API_OPEN_AI_API_KEY } = import.meta.env;

export const postPrompt = (query, success, fail) => {
  const payload = {
    model: "gpt-3.5-turbo-0125",
    messages: [
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
