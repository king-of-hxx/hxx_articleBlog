<template>
  <div>
    <!-- <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load"> -->
    <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>
    <!-- </scroll-page> -->
    <el-row type="flex" justify="end">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="innerPage.pageNumber" :page-sizes="[8, 10, 12, 15]" :page-size="innerPage.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="innerPage.total">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
import ArticleItem from '@/components/article/ArticleItem'
import ScrollPage from '@/components/scrollpage'
import { getArticles } from '@/api/article'

export default {
  name: "ArticleScrollPage",
  props: {
    offset: {
      type: Number,
      default: 100
    },
    page: {
      type: Object,
      default() {
        return {}
      }
    },
    query: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  watch: {
    // 'query': {
    //   handler() {
    //     this.noData = false
    //     this.articles = []
    //     this.innerPage.pageNumber = 1
    //     this.getArticles()
    //   },
    //   deep: true
    // },
    // 'page': {
    //   handler() {
    //     this.noData = false
    //     this.articles = []
    //     this.innerPage = this.page
    //     this.getArticles()
    //   },
    //   deep: true
    // }
  },
  created() {
    this.getArticles()
  },
  data() {
    return {
      loading: false,
      noData: false,
      innerPage: {
        total: 0,
        pageSize: 8,
        pageNumber: 1,
        name: 'a.createDate',
        sort: 'desc'
      },
      articles: []
    }
  },
  methods: {
    load() {
      this.getArticles()
    },
    view(id) {
      this.$router.push({ path: `/view/${id}` })
    },
    handleSizeChange(val) {
      this.innerPage.pageSize = val
      this.getArticles()
    },
    handleCurrentChange(val) {
      this.innerPage.pageNumber = val
      this.getArticles()
    },
    getArticles() {
      let that = this
      that.loading = true

      getArticles(that.query, that.innerPage).then(data => {
        this.articles = data.data
        // let newArticles = data.data
        // console.log('====================================');
        // console.log(data);
        // console.log('====================================');
        this.innerPage.total = data.data[0].total
        // if (newArticles && newArticles.length > 0) {
        //   that.innerPage.pageNumber += 1
        //   that.articles = that.articles.concat(newArticles)
        // } else {
        //   that.noData = true
        // }

      }).catch(error => {
        if (error !== 'error') {
          that.$message({ type: 'error', message: '文章加载失败!', showClose: true })
        }
      }).finally(() => {
        that.loading = false
      })

    }
  },
  components: {
    'article-item': ArticleItem,
    'scroll-page': ScrollPage
  }

}
</script>

<style scoped>
.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 10px;
}
</style>
