
<!-- template - 查看所有评审活动组件 -->

<template>

  <!-- table - 所有评审活动的信息 -->
  <el-table :data="reviewInform">
    <el-table-column prop="review_id" label="评审号" width="120">
    </el-table-column>
    <el-table-column prop="review_name" label="评审名" width="200">
    </el-table-column>
    <el-table-column prop="review_start" label="评审发起时间" width="160" sortable>
    </el-table-column>
    <el-table-column prop="review_end" label="评审截止时间" width="160" sortable>
    </el-table-column>
    <el-table-column fixed="right" width="150">
      <template v-slot="scope">

        <!-- button - 复制评审链接至剪切板 -->
        <el-button type="primary" @click="copyReviewLink(scope.row)" style="margin:5px">复制评审链接</el-button>

      </template>
    </el-table-column>
    <el-table-column fixed="right" width="110">
      <template v-slot="scope">

        <!-- button - 跳转页面查看评审活动下的所有团队 -->
        <el-button @click="goDisplayTeamPage(scope.row)" style="margin:5px">评审团队</el-button>

      </template>
    </el-table-column>
  </el-table>

</template>

<script>
import clipboard3 from 'vue-clipboard3'
import { ElMessage } from "element-plus";

export default {
  name: 'ReviewCheck',
  data() {
    return {
      reviewInform: [],
      list: []
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
    this.getData();
  },
  methods: {
    //获取所有评审活动
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/displayReviews")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.setData(successResponse.data);
          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },
    //展示评审活动列表
    setData(item) {
      for (let i = 0; i < item.data.length; i++) {
        let inform = {};
        inform.review_id = item.data[i].review_id;
        inform.review_name = item.data[i].review_name;
        inform.review_start = item.data[i].review_start;
        inform.review_end = item.data[i].review_end;
        this.reviewInform.push(inform)
      }
    },
    //跳转页面 显示所有团队
    goDisplayTeamPage(item) {
      this.$router.push({
        path: '/admin/displayTeam',
        //页面传参
        query: {
          project_id: item.review_id,
          beforeItem: 'displayReview'
        }
      })
    },
    //复制老师评审链接
    copyReviewLink(item) {
      // 获取当前页面地址，如http://localhost:8080/admin/index
      let wPath = window.document.location.href;
      // 获取当前页面主机地址之后的目录，如：/admin/index
      let pathName = this.$route.path;
      let pos = wPath.indexOf(pathName);
      // 获取主机地址，如：http://localhost:8080
      let localhostPath = wPath.substring(0, pos);
      localhostPath = localhostPath + "/check/" + item.review_id
      const { toClipboard } = clipboard3();
      try {
        toClipboard(localhostPath);
        ElMessage({
          type: 'success',
          message: '链接已复制到剪切板',
        })
      } catch (e) {
        ElMessage({
          type: 'error',
          message: '复制失败',
        })
      }
    }

  }
}
</script>