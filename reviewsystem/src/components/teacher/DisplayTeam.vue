
<!-- template - 显示所有团队组件 -->

<template>

  <!-- table - 显示该活动下的所有团队 -->
  <el-table :data="teamInfo">
    <el-table-column prop="team_id" label="团队号" width="100" sortable>
    </el-table-column>
    <el-table-column prop="team_name" label="团队名" width="160">
    </el-table-column>
    <el-table-column prop="captain_id" label="队长号" width="100" sortable>
    </el-table-column>
    <el-table-column prop="captain_name" label="队长名" width="120">
    </el-table-column>
    <el-table-column prop="teacher_id" label="指导老师编号" width="120">
    </el-table-column>
    <el-table-column fixed="right" width="110">
      <template v-slot="scope">

        <!-- button - 查看评审或进行评审 -->
        <el-button type="primary" @click="reviewCheck(scope.row)" style="margin:5px"
          v-if="teamInfo[scope.$index].submit">
          查看评审
        </el-button>
        <el-button type="primary" @click="reviewSubmit(scope.row)" style="margin:5px"
          v-if="!teamInfo[scope.$index].submit">
          进行评审
        </el-button>

      </template>
    </el-table-column>
  </el-table>

  <!-- dialog - 查看评审 -->
  <el-dialog v-model="checkReviewClick" title="查看评审">
    <CheckSelfReview :team_information="checkTeamInfo" :project_id="project_id" @Close="Close" />
  </el-dialog>

  <!-- dialog - 进行评审 -->
  <el-dialog v-model="submitReviewClick" title="进行评审" width="50vw">
    <SubmitReview :project_id="project_id" :team_information="checkTeamInfo" @Close="Close" />
  </el-dialog>
</template>


<script>
import SubmitReview from '../dialog/SubmitReview.vue'
import CheckSelfReview from '../dialog/CheckSelfReviewReport.vue'

export default {
  name: 'Review',
  components: {
    SubmitReview, CheckSelfReview
  },
  data() {
    return {
      checkReviewClick: false,
      submitReviewClick: false,
      list: [],
      teamInfo: [],
      project_id: '',
      user_id: '',
      checkTeamInfo: {
      },
    }
  },
  mounted() {
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.project_id = this.$route.query.project_id;
    this.user_id = this.$route.query.user_id;
    this.getData();
  },
  methods: {
    //获取评审团队
    getData() {
      this.getReviews();
    },
    //获取未被评审的团队
    getReviews() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/displayteams")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            for (let i = 0; i < successResponse.data.data.length; i++) {
              let inform = {};
              inform.team_id = successResponse.data.data[i].team_id;
              inform.team_name = successResponse.data.data[i].team_name;
              inform.captain_id = successResponse.data.data[i].captain_id;
              inform.captain_name = successResponse.data.data[i].captain_name;
              inform.teacher_id = successResponse.data.data[i].teacher_id;
              inform.submit = false;
              this.teamInfo.push(inform)
            }
            this.getReviewed();
          }
          else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },//获取已被评审的团队
    getReviewed() {
      this.$http.get("/" + this.list.identity + this.user_id + "/" + this.project_id + "/displayteams_reviewed")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            console.log(successResponse.data.data.length)
            for (let i = 0; i < successResponse.data.data.length; i++) {
              let inform = {};
              inform.team_id = successResponse.data.data[i].team_id;
              inform.team_name = successResponse.data.data[i].team_name;
              inform.captain_id = successResponse.data.data[i].captain_id;
              inform.captain_name = successResponse.data.data[i].captain_name;
              inform.teacher_id = successResponse.data.data[i].teacher_id;
              inform.submit = true;
              this.teamInfo.push(inform)
            }
          }
          else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },
    //显示提交评审弹窗
    reviewSubmit(item) {
      this.checkTeamInfo = item
      this.submitReviewClick = true;
    },
    //显示查看评审弹窗
    reviewCheck(item) {
      this.checkTeamInfo = item
      this.checkReviewClick = true;
    },
    //关闭弹窗
    Close() {
      this.checkReviewClick = false,
        this.submitReviewClick = false
      setTimeout(() => { this.$router.go(0) }, 500)
    }
  }
}
</script>