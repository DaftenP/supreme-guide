import axios from "axios";
import VueJwtDecode from "vue-jwt-decode";
import Cookies from "vue-cookies";

import settingCookie from "@/utils/settingCookie";

const authClient = axios.create({
  baseURL: "http://localhost",
  headers: {
    "Content-type": "application/json",
  },
});

// 토큰 만료 확인
const checkToken = async () => {
  console.log("check token!!!!!");
  let token = settingCookie("get-access");
  const exp = VueJwtDecode.decode(token);
  if (Date.now() / 1000 > exp.exp) {
    console.log("해당 토큰은 만료되었습니다.");
    await getNewToken();
  }
};

// 새로운 토큰 발급 -> refresh
const getNewToken = async () => {
  const access = settingCookie("get-access");
  const refresh = settingCookie("get-refresh");

  try {
    const res = await axios({
      method: "post",
      url: "/auth/refresh",
      data: {
        accessToken: access,
        refreshToken: refresh,
      },
    });
    settingCookie("remove");
    Cookies.set("accessToken", res.data.accessToken);
    Cookies.set("refreshToken", res.datarefreshToken);
    return res.data.accessToken;
  } catch (error) {
    alert("error");
  }
};

// axios 요청 전 수행해야할 작업
authClient.interceptors.request.use(async (config) => {
  let token = settingCookie("get-access");
  const exp = VueJwtDecode.decode(token);
  if (Date.now() / 1000 > exp.exp) {
    console.log("만료된 토큰 ", token);
    const newToken = await getNewToken();
    console.log("새 토큰 발급 ", newToken);
    config.headers["Authorization"] = `Bearer ${newToken}`;
  } else {
    config.headers["Authorization"] = `${token}`;
  }
  return config;
});

export default authClient;
