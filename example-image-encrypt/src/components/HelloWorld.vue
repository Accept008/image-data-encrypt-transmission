<template>
  <div>
    <h2 id="page-title" style="margin-left: 20px;">图片列表</h2>
    <el-card>
      <el-table v-loading="dataListLoading" :data="dataList">
        <el-table-column type="index" width="50" label="序号" align="center" />
        <el-table-column prop="advertiseName" label="名称" align="center" />
        <el-table-column prop="productIcon" label="图片" align="center">
          <template slot-scope="scope"><img :src="scope.row.advertiseImageUrl" width="50" height="50"></template>
          <!--<template slot-scope="scope"><img :src="`${getImgUrl(scope.row.advertiseImageUrl)}`" width="50" height="50"></template>-->
          <!--<template slot-scope="scope"><img :src="'data:image/jpeg;base64,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'" width="50" height="50"></template>-->
        </el-table-column>
        <el-table-column prop="advertiseLink" label="描述" align="center" />
      </el-table>
      <el-pagination :current-page.sync="currPage" :page-sizes="[10]" :page-size="pageSize" layout="total,sizes, prev, pager, next" :total="totalCount" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </el-card>
  </div>
</template>

<script>
import { getPageImageList } from '../api/ImageList'
import { urlToBuffer } from '../util/aesUtils'

export default {
  name: 'HelloWorld',
  data () {
    return {
      currPage: 0,
      pageSize: 10,
      totalCount: 0,
      dataList: [],
      dataListLoading: false,
      image: ''
    }
  },
  computed: {
  },
  watch: {
  },
  created () {
  },
  mounted () {
    this.getDataList()
  },
  methods: {
    async getImgUrl (url) {
      console.log('step_1.进入方法...')
      const key = 'CPfpNLbzlzBbvLEGP1LZCobEyvxn7Pbq'
      const iv = '53YP80BtCvNjKlql'
      let result
      await urlToBuffer(url, key, iv).then(res => {
        console.log('step_2.图片解码......')
        result = res
      })
      console.log('step_3.最后一行......')
      return result
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.getDataList()
    },
    handleCurrentChange (val) {
      this.currPage = val
      this.getDataList()
    },
    getDataList (type) {
      if (type === 'init') {
        this.currPage = 0
      }
      this.dataListLoading = true
      const that = this
      getPageImageList({
        page: this.currPage >= 1 ? this.currPage - 1 : this.currPage,
        limit: this.pageSize
      }).then(res => {
        // res.data => {code: 200, msg: "操作成功", data: {…}}
        console.log('11111...')
        res.data.data.list.forEach((item, index) => {
          console.log('22222...')
          this.getImgUrl(item.advertiseImageUrl).then(value => {
            const imgUrl = value
            console.log('转化后图片 = ' + imgUrl)
            item.advertiseImageUrl = imgUrl
          })
        })
        that.dataList = res.data.data.list
        that.totalCount = res.data.data.totalCount
        that.dataListLoading = false
      }).catch(() => {
        that.dataListLoading = false
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }
</style>
