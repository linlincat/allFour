import { createApp } from "vue";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import "./common/scss/globalBase.scss";
import App from "./App.vue";

const app = createApp(App);

app.use(ElementPlus);
app.mount("#app");
