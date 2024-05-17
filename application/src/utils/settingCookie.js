import Cookies from "vue-cookies";

const settingCookie = (type) => {
  if (type === "get-access") {
    const token = Cookies.get("accessToken");
    return token;
  } else if (type === "get-refresh") {
    const token = Cookies.get("refreshToken");
    return token;
  } else if (type === "remove") {
    Cookies.remove("accessToken");
    Cookies.remove("refreshToken");
  }
};

export default settingCookie;
