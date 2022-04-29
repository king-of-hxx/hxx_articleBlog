<template>
  <el-card>
    <h1 class="me-author-name">博客文章</h1>
    <div class="me-author-description">
      <div class="author-info">
        <span><i class="fa fa-user-circle"></i> &nbsp;郝祥兴</span>
        <span class="commom"><i class="fa fa-clock-o" aria-hidden="true"></i> &nbsp;1998-10-04</span>
      </div>
      <div class="author-info">
        <span><i class="el-icon-location-outline"></i> &nbsp;安徽&安庆</span>
        <span class="commom"><i class="fa fa-briefcase" aria-hidden="true"></i> &nbsp;java后端&前端</span>
      </div>
    </div>
    <div class="me-author-tool">
      <i @click="showTool(qq)" :title="qq.title" class="iconfont icon-qq"></i>
      <i @click="showTool(bilibili)" :title="bilibili.title" class="iconfont icon-bilibili-fill"></i>
      <i @click="showTool(github)" :title="github.title" class="fa fa-github fa-3x"></i>
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
        message: '<a target="_blank" href="https://space.bilibili.com/482180888">https://space.bilibili.com/482180888</a>'
      },
      github: {
        title: 'github',
        message: '<a target="_blank" href="https://github.com/king-of-hxx">https://github.com/king-of-hxx</a>'
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
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.author-info {
  display: flex;
  justify-content: space-between;
}

.commom {
  width: 130px;
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
i {
  width: 16px;
  height: 16px;
}
</style>
