<script setup lang="ts">
import CommonHeader from "./components/common/CommonHeader.vue";
import CommonFooter from "./components/common/CommonFooter.vue";
</script>

<template>
  <CommonHeader />
  <div style="height: 6px; clear: both"></div>
  <div style="height: 100px; clear: both; background: #bababa"></div>
  <CommonFooter />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
