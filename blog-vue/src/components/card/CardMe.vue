<template>
  <el-card>
    <h1 class="me-author-name">博客文章</h1>
    <div class="me-author-description">
      <span><i class="el-icon-location-outline"></i> &nbsp;安徽&安庆</span>
      <span><i class="me-icon-job"></i> &nbsp;java后端&前端</span>
    </div>
    <div class="me-author-tool">
      <i @click="showTool(qq)" :title="qq.title" class="iconfont icon-qq"></i>
      <i @click="showTool(bilibili)" :title="bilibili.title" class="iconfont icon-bilibili-fill"></i>
    </div>
  </el-card>

</template>

<script>
export default {
  name: 'CardMe',
  data() {
    return {
      qq: { title: 'QQ', message: '563827223' },
      bilibili: {
        title: '哔哩哔哩',
        message: '<a target="_blank" href="https://space.bilibili.com/473844125">https://space.bilibili.com/473844125</a>'
      }
    }
  },
  methods: {
    showTool(tool) {
      this.$message({
        duration: 0,
        showClose: true,
        dangerouslyUseHTMLString: true,
        message: '<strong>' + tool.message + '</strong>'
      });
    }
  }
}
</script>

<style scoped>
.me-author-name {
  text-align: center;
  font-size: 30px;
  border-bottom: 1px solid #5fb878;
}

.me-author-description {
  padding: 8px 0;
}

.me-icon-job {
  padding-left: 16px;
}

.me-author-tool {
  text-align: center;
  padding-top: 10px;
}

.me-author-tool i {
  cursor: pointer;
  padding: 4px 10px;
  font-size: 30px;
}
</style>
