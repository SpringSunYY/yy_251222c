<template>
  <div class="app-container home">
    <h1 style="margin: 0 auto;text-align: center;font-weight: bold;font-size: 36px">宿舍管理系统</h1>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.9"
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">

</style>

