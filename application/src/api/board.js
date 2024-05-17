import { localAxios } from "@/utils/http-commons";

const axios = localAxios(`/qna`);

export const getAllArticle = (params, success, fail) => {
  console.log("getAllArticle", params);
  axios.get(`/all`, { params }).then(success).catch(fail);
};

export const getArticle = (board_id, success, fail) => {
  console.log("getArticle", board_id);
  axios.get(`/select/${board_id}`).then(success).catch(fail);
};

export const postArticle = (article, success, fail) => {
  console.log("postAllArticle", article);
  axios.post(`/regist`, JSON.stringify(article)).then(success).catch(fail);
};

export const putArticle = (article, success, fail) => {
  console.log("getAllArticle", article);
  axios.put(`/update`, JSON.stringify(article)).then(success).catch(fail);
};

export const deleteArticle = (board_id, success, fail) => {
  console.log("deleteArticle", board_id);
  axios.delete(`/delete/${board_id}`).then(success).catch(fail);
};
