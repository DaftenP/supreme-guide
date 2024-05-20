import axios from "axios";
// @ts-ignore
import jwtDecode from "jwt-decode";
import Cookies from "vue-cookies";

import settingCookie from "@/utils/settingCookie";

const authClient = axios.create({
  // baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    "Content-type": "application/json",
  },
});

// 토큰 만료 확인
const checkToken = async () => {
  console.log("check token!!!!!");
  let token = await settingCookie("get-access");
  const exp = jwtDecode(token);
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
      url: `${import.meta.env.VITE_API_BASE_URL}/auth/refresh`,
      data: {
        accessToken: access,
        refreshToken: refresh,
      },
    });
    settingCookie("remove");
    Cookies.set("accessToken", res.data.accessToken);
    Cookies.set("refreshToken", res.data.refreshToken);
    return res.data.accessToken;
  } catch (error) {
    alert("Error refreshing token");
  }
};
// axios 요청 전 수행해야할 작업
authClient.interceptors.request.use(
  async (config) => {
    let token = settingCookie("get-access");
    console.log("Initial token: ", token);

    if (token) {
      const exp = jwtDecode(token);
      console.log("Token expiry: ", exp);

      if (Date.now() / 1000 > exp) {
        console.log("Expired token: ", token);
        token = await getNewToken();
        console.log("New token: ", token);
      }

      config.headers["Authorization"] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default authClient;
