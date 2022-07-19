<template>
  <div id="home">
    <!-- <video preload="auto" class="me-video-player" autoplay="autoplay" loop="loop">
      <source src="../static/vedio/sea.mp4" type="video/mp4">
    </video> -->
    <el-container class="container">

      <base-header :activeIndex="activeIndex"></base-header>

      <router-view class="me-container" />

      <base-footer v-show="footerShow"></base-footer>

    </el-container>

  </div>

</template>

<script>
import BaseFooter from '@/components/BaseFooter'
import BaseHeader from '@/views/BaseHeader'

export default {
  name: 'Home',
  data() {
    return {
      activeIndex: '/index',
      footerShow: true
    }
  },
  components: {
    'base-header': BaseHeader,
    'base-footer': BaseFooter
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.activeIndex = to.path
    })
  },
  beforeRouteUpdate(to, from, next) {
    if (to.path == '/index') {
      this.footerShow = true
    } else {
      this.footerShow = false
    }
    this.activeIndex = to.path
    next()
  }
}
</script>

<style>
/* #home {
  border: 1px solid red;
  overflow: auto;
} */
.container {
  /* border: 1px solid red; */
  height: 90vh;
  overflow: auto;
}
.me-container {
  margin: 100px auto 140px;
}
.me-video-player {
  background-color: transparent;
  width: 100%;
  height: 100%;
  object-fit: fill;
  display: block;
  position: absolute;
  left: 0;
  z-index: -1;
  top: 0;
}
</style>
