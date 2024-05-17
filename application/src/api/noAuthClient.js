import axios from "axios";

const noAuthClient = axios.create({
  baseURL: "http://localhost",
  headers: {
    "Content-Type": "application/json",
  },
});

export default noAuthClient;
