import { localAxios } from "@/utils/http-commons";

const axios = localAxios(`/attraction`);

export const getRegion = (success, fail) => {
  axios.get(`/region`).then(success).catch(fail);
};

export const getAllAttraction = (params, success, fail) => {
  axios.get(`/search`, { params }).then(success).catch(fail);
};

export const getAttraction = (id, success, fail) => {
  axios.get(`/search/${id}`).then(success).catch(fail);
};
