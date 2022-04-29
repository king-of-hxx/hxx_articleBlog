import { Loading } from "element-ui";

let loading = "";

export function openLoading() {
  loading = Loading.service({
    lock: true,
    text: "拼命读取中",
    spinner: "el-icon-loading",
    background: "rgba(0, 0, 0, 0.8)",
  });
}

export function closeLoading() {
  loading.close();
}
