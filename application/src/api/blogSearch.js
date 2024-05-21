import axios from "axios";

export const getBlog = (query, display, success, fail) => {
  axios
    .get(`/api/v1/search/blog.json?query=${query}&display=${display}`, {
      headers: {
        "Content-Type": "application/json;charset=utf-8",
        "X-Naver-Client-Id": "n3WROpRU3nzjCW_iL95U",
        "X-Naver-Client-Secret": "VK56D9JTKI",
      },
    })
    .then(success)
    .catch(fail);
};
