import axios from "axios";
const { VITE_API_GOOGLE_API_KEY } = import.meta.env;

export const getVideo = (query, success, fail) => {
  axios
    .get(
      `/video/youtube/v3/search?part=snippet&type=video&q=${query}&key=${VITE_API_GOOGLE_API_KEY}`,
      {
        headers: {
          "Content-Type": "application/json;charset=utf-8",
        },
      }
    )
    .then(success)
    .catch(fail);
};
