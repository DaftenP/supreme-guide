import axios from "axios";
const { VITE_API_NAVER_API_ID, VITE_API_NAVER_API_KEY } = import.meta.env;

export const getBlog = (query, display, success, fail) => {
  axios
    .get(`/blog/v1/search/blog.json?query=${query}&display=${display}`, {
      headers: {
        "Content-Type": "application/json;charset=utf-8",
        "X-Naver-Client-Id": VITE_API_NAVER_API_ID,
        "X-Naver-Client-Secret": VITE_API_NAVER_API_KEY,
      },
    })
    .then(success)
    .catch(fail);
};
