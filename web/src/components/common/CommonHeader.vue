<template>
  <div class="commonHeader">
    <el-image
      style="
        width: 60px;
        height: 60px;
        border-radius: 30px;
        margin-top: 10px;
        float: left;
      "
      :src="'/common/images/header.jpg'"
      :fit="'cover'"
    />
    <ul class="headerUl">
      <li class="cur">首页</li>
      <li>分类</li>
    </ul>
    <div style="margin-top: 30px; float: right">
      <el-dropdown>
        <el-button type="primary">
          Vue+TS<el-icon class="el-icon--right"><arrow-down /></el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>微信小程序</el-dropdown-item>
            <el-dropdown-item>React+TS</el-dropdown-item>
            <el-dropdown-item>Vue+TS</el-dropdown-item>
            <el-dropdown-item>Three.js</el-dropdown-item>
            <el-dropdown-item>微前端</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <div style="margin-top: 30px; margin-right: 20px; float: right">
      <el-dropdown>
        <el-button type="primary">
          占位<el-icon class="el-icon--right"><arrow-down /></el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>登录/注册</el-dropdown-item>
            <el-dropdown-item>退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>
<script setup lang="ts">
// import { ref } from "vue";

// defineProps<{ msg: string }>();
</script>
<style scoped lang="scss">
@import url("../../common/scss/header.scss");
</style>
