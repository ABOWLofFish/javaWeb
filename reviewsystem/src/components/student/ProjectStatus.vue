
<!-- template - 显示所有活动状态组件 -->

<template>

  <!-- table - 显示该学生参与的所有活动状态 -->
  <el-table :data="projectStatus">
    <el-table-column prop="project_id" label="活动号" width="120">
    </el-table-column>
    <el-table-column prop="project_name" label="活动名" width="180">
    </el-table-column>
    <el-table-column prop="team_name" label="团队名" width="180">
    </el-table-column>
    <el-table-column prop="teacher_name" label="指导老师" width="120">
    </el-table-column>
    <el-table-column label="是否提交" width="80">
      <template v-slot="scope">
        <el-icon style="color: #42b983;margin-left: 12px" :size="20" v-if="projectStatus[scope.$index].submit_status">
          <CircleCheck />
        </el-icon>
        <el-icon style="color: orange;margin-left: 12px" :size="20" v-if="!projectStatus[scope.$index].submit_status">
          <Clock />
        </el-icon>
      </template>
    </el-table-column>
    <el-table-column label="是否审评" width="80">
      <template v-slot="scope">
        <el-icon style="color: #42b983;margin-left: 12px" :size="20" v-if="projectStatus[scope.$index].review_status">
          <CircleCheck />
        </el-icon>
        <el-icon style="color: orange;margin-left: 12px" :size="20" v-if="!projectStatus[scope.$index].review_status">
          <Clock />
        </el-icon>
      </template>
    </el-table-column>
    <el-table-column fixed="right" width="150">
      <template v-slot="scope">

        <!-- button - 查看评审结果 -->
        <el-button :disabled="!projectStatus[scope.$index].review_status" type="primary"
          @click="reviewReportCheck(scope.row)" style="margin:5px" v-show="true">查看审评结果
        </el-button>

      </template>
    </el-table-column>
  </el-table>

  <!-- dialog - 显示评审报告 -->
  <el-dialog v-model="displayReviewClick" title="评审报告" width="60vw"
    style="display:flex;justify-content:center;align-items:center;">
    <CheckReviewReport :checkTeamInform="checkTeamInform" :report_content="report_content" :averageScore="averageScore"
      :review_inform="review_inform" />
  </el-dialog>
</template>

<script>
import CheckReviewReport from '@/components/dialog/CheckReviewReport.vue';

export default {
  components: {
    CheckReviewReport
  },
  data() {
    return {
      displayReviewClick: false,
      projectStatus: [],
      list: [],
      checkTeamInform: {},
      report_content: '',
      averageScore: '',
      review_inform: []
    }
  }, created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.getData();
  },
  methods: {
    //获取所有活动及状态
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/myReportsStatus")
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
    //显示活动及状态
    setData(item) {
      this.projectStatus = [];
      for (let i = 0; i < item.data.length; i++) {
        let inform = {};
        inform.project_name = item.data[i].project_name;
        inform.project_id = item.data[i].project_id;
        inform.team_name = item.data[i].team_name;
        inform.teacher_name = item.data[i].teacher_name;
        inform.submit_status = item.data[i].submit_status;
        inform.review_status = item.data[i].review_status;
        this.projectStatus.push(inform)
      }

    },
    //查看评审报告
    reviewReportCheck(item) {
      this.getTeamInfo(item.project_id)
      this.displayReviewClick = true;

    },
    //获取团队消息
    getTeamInfo(project_id) {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + project_id + "/display_teamInfo")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            console.log(successResponse.data)
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
    //获取团队报告消息
    getReportInfo(project_id) {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + project_id + "/report" + this.checkTeamInform.team_id)
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.report_content = successResponse.data.data.report_content;
            this.getReviewInfo(project_id)
          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        });
    },
    //获取评审消息
    getReviewInfo(project_id) {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + project_id + "/review" + this.checkTeamInform.team_id)
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.review_inform = [];
            let grade = 0;
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