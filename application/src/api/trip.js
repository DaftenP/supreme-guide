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

export const postComment = async (article, success, fail) => {
  try {
    const res = await authClient({
      method: "post",
      url: `${import.meta.env.VITE_API_BASE_URL}/trip/comment/regist`,
      data: article,
    });
    success(res);
  } catch (err) {
    fail(err);
  }
};

export const putArticle = async (article, success, fail) => {
  try {
    const res = await authClient({
      method: "put",
      url: `${import.meta.env.VITE_API_BASE_URL}/trip/modify`,
      data: article,
    });
    success(res);
  } catch (err) {
    fail(err);
  }
};

export const putComment = async (article, success, fail) => {
  try {
    const res = await authClient({
      method: "put",
      url: `${import.meta.env.VITE_API_BASE_URL}/trip/comment/regist`,
      data: article,
    });
    success(res);
  } catch (err) {
    fail(err);
  }
};

export const deleteArticle = async (board_id, success, fail) => {
  try {
    const res = await authClient({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/trip/${board_id}`,
    });
    success(res);
  } catch (err) {
    fail(err);
  }
};

export const deleteComment = async (comment_id, success, fail) => {
  try {
    const res = await authClient({
      method: "delete",
      url: `${import.meta.env.VITE_API_BASE_URL}/trip/comment/${comment_id}`,
    });
    success(res);
  } catch (err) {
    fail(err);
  }
};
