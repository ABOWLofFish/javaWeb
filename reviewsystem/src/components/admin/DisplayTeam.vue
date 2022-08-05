
<!-- template - 展示所有团队组件 -->

<template>

  <!-- table - 显示该活动/评审活动下的所有团队 -->
  <el-table :data="teamInfo">
    <el-table-column prop="team_id" label="团队号" width="120" sortable>
    </el-table-column>
    <el-table-column prop="team_name" label="团队名" width="160">
    </el-table-column>
    <el-table-column prop="captain_id" label="队长号" width="120">
    </el-table-column>
    <el-table-column prop="captain_name" label="队长名" width="120">
    </el-table-column>
    <el-table-column prop="teacher_id" label="指导老师编号" width="120">
    </el-table-column>
    <el-table-column fixed="right" width="150">
      <template v-slot="scope">

        <!-- button - 点击查看团队报告 -->
        <el-button type="primary" v-if="this.beforeItem == 'displayProject'" @click="checkTeamReport(scope.row)"
          style="margin:5px">查看团队报告
        </el-button>

        <!-- button - 点击查看评审报告 -->
        <el-button type="primary" v-if="this.beforeItem == 'displayReview'" @click="checkReviewReport(scope.row)"
          style="margin:5px">查看评审报告
        </el-button>

      </template>
    </el-table-column>
  </el-table>

  <!-- dialog - 显示团队报告 -->
  <el-dialog v-model="displayTeamClick" title="团队报告" width="60vw"
    style="display:flex;justify-content:center;align-items:center;">
    <CheckTeamReport :user_id="user_id" :project_id="project_id" />
  </el-dialog>

  <!-- dialog - 显示该团队的所有评审报告 -->
  <el-dialog v-model="displayReviewClick" title="评审报告" width="60vw"
    style="display:flex;justify-content:center;align-items:center;">
    <CheckReviewReport :checkTeamInform="checkTeamInform" :report_content="report_content" :averageScore="averageScore"
      :review_inform="review_inform" />
  </el-dialog>

</template>

<script>
import CheckTeamReport from '@/components/dialog/CheckTeamReport.vue';
import CheckReviewReport from '@/components/dialog/CheckReviewReport.vue';

export default {
  name: 'Review',
  components: {
    CheckReviewReport,
    CheckTeamReport
  },
  data() {
    return {
      list: [],
      project_id: '',
      user_id: '',
      report_content: '',
      displayTeamClick: false,
      displayReviewClick: false,
      teamInfo: [],
      beforeItem: '',
      checkTeamInform: {},
      review_inform: [],
      averageScore: ''
    }
  },
  //页面刷新
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
    //获取上个页面的传参
    this.project_id = this.$route.query.project_id;
    this.beforeItem = this.$route.query.beforeItem;
    this.getData();
  },
  methods: {
    //获取页面数据 该活动所有团队
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id)
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
    //展示数据
    setData(item) {
      for (let i = 0; i < item.data.length; i++) {
        let inform = {};
        inform.team_id = item.data[i].team_id;
        inform.team_name = item.data[i].team_name;
        inform.captain_id = item.data[i].captain_id;
        inform.captain_name = item.data[i].captain_name;
        inform.teacher_id = item.data[i].teacher_id;
        this.teamInfo.push(inform)
      }
    },
    //显示查看评审弹窗
    checkReviewReport(item) {
      setTimeout(() => {
        this.displayReviewClick = true
      }, 500)
      this.getTeamInfo(this.project_id, item.captain_id) //在该页面获取所需数据 传给弹窗
    },
    //显示查看报告弹窗
    checkTeamReport(item) {
      setTimeout(() => {
        this.displayTeamClick = true
      }, 500)
      this.user_id = item.captain_id
    },
    //获取团队信息
    getTeamInfo(project_id, captain_id) {
      this.$http.get("/" + "student" + captain_id + "/" + project_id + "/display_teamInfo")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.checkTeamInform.team_id = successResponse.data.data.team_id,
              this.checkTeamInform.team_name = successResponse.data.data.team_name,
              this.checkTeamInform.captain_id = successResponse.data.data.captain_id,
              this.checkTeamInform.captain_name = successResponse.data.data.captain_name,
              this.checkTeamInform.teacher_id = successResponse.data.data.teacher_id;
            this.getReportInfo(project_id)
          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {

        });
    },
    //获取报告内容
    getReportInfo(project_id) {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + project_id + "/report" + this.checkTeamInform.team_id)
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            console.log(successResponse.data)
            this.report_content = successResponse.data.data.report_content;
            this.getReviewInfo(project_id)
          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        });
    },
    //获取评审内容
    getReviewInfo(project_id) {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + project_id + "/review" + this.checkTeamInform.team_id)
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.review_inform = [];
            let grade = 0;
            console.log(successResponse.data)
            for (let i = 0; i < successResponse.data.data.length; i++) {
              let inform = {};
              inform.review_teacher = successResponse.data.data[i].teacher_id;
              inform.review_content = successResponse.data.data[i].review_content;
              inform.review_score = successResponse.data.data[i].review_level;
              grade += successResponse.data.data[i].review_level;
              this.review_inform.push(inform)
            }
            if (successResponse.data.data.length != 0)
              this.averageScore = grade / successResponse.data.data.length;
          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        });
    },
  }
}
</script>