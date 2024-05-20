import { localAxios } from "@/utils/http-commons";
import authClient from "@/api/authClient";

const axios = localAxios(`/trip`);

export const getAllArticle = (params, success, fail) => {
  axios.get(``, { params }).then(success).catch(fail);
};

export const getArticle = (board_id, success, fail) => {
  axios.get(`/${board_id}`).then(success).catch(fail);
};

export const postArticle = async (article, success, fail) => {
  console.log(article);
  try {
    const res = await authClient({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/trip/regist`,
      data: article,
    });
    success(res);
  } catch (err) {
    fail(err);
  }
};

export const postComment = (article, success, fail) => {
  axios
    .post(`/comment/regist`, JSON.stringify(article))
    .then(success)
    .catch(fail);
};

export const putArticle = (article, success, fail) => {
  axios.put(`/modify`, JSON.stringify(article)).then(success).catch(fail);
};

export const putComment = (article, success, fail) => {
  axios
    .put(`/comment/modify`, JSON.stringify(article))
    .then(success)
    .catch(fail);
};

export const deleteArticle = (board_id, success, fail) => {
  axios.delete(`/delete/${board_id}`).then(success).catch(fail);
};

export const deleteComment = (comment_id, success, fail) => {
  axios.delete(`/comment/delete/${comment_id}`).then(success).catch(fail);
};
