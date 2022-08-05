
<!-- template - 显示所有活动组件 -->

<template>

  <!-- table - 显示所有活动信息 -->
  <el-table :data="projectInform" row-key="item">
    <el-table-column prop="project_id" label="活动号" width="120" sortable>
    </el-table-column>
    <el-table-column prop="project_name" label="活动名" width="200">
    </el-table-column>
    <el-table-column prop="project_start" label="活动发起时间" width="180" sortable>
    </el-table-column>
    <el-table-column prop="project_end" label="活动截止时间" width="180" sortable>
    </el-table-column>
    <el-table-column fixed="right" width="110">
      <template v-slot="scope">

        <!-- button - 注册或查看团队 -->
        <el-button type="primary" plain v-if="!projectInform[scope.$index].signFlag" @click="toSignTeamPage(scope.row)"
          style="margin:5px">注册团队
        </el-button>
        <el-button type="primary" v-if="projectInform[scope.$index].signFlag" @click="teamInformCheck(scope.row)"
          style="margin:5px">查看团队
        </el-button>

      </template>
    </el-table-column>
    <el-table-column fixed="right" width="110">
      <template v-slot="scope">

        <!-- button - 提交或查看报告 -->
        <el-button type="primary" plain :disabled="!projectInform[scope.$index].signFlag"
          v-if="!projectInform[scope.$index].commitFlag" @click="toCommitPage(scope.row)" style="margin:5px">提交报告
        </el-button>
        <el-button type="primary" :disabled="!projectInform[scope.$index].signFlag"
          v-if="projectInform[scope.$index].commitFlag" @click="reportInformCheck(scope.row)" style="margin:5px">查看报告
        </el-button>

      </template>
    </el-table-column>
  </el-table>

  <!-- dialog - 显示团队基本信息 -->
  <el-dialog v-model="teamInformClick" title="团队信息" width="30vw">
    <CheckTeamInfo :team_information="this.team_information" :project_id="this.project_id" />
  </el-dialog>

  <!-- dialog - 显示团队报告 -->
  <el-dialog v-model="reportInformClick" title="报告信息">
    <CheckTeamReport :team_information="this.team_information" :project_id="this.project_id" />
  </el-dialog>

</template>

<script>
import CheckTeamReport from "../dialog/CheckTeamReport.vue"
import CheckTeamInfo from "../dialog/CheckTeamInfo.vue"

export default {
  components: {
    CheckTeamReport, CheckTeamInfo
  },
  data() {
    return {
      list: [],
      teamInformClick: false,
      reportInformClick: false,
      projectInform: [],
      team_information: [],
      project_id: ''
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.getData();
  },
  methods: {
    //获取所有活动及状态
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/projects")
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
    //显示活动消息
    setData(item) {
      for (let i = 0; i < item.data.length / 3; i++) {
        if (!item.data[i].status)
          continue;
        let inform = {};
        inform.project_id = item.data[i].project_id;
        inform.project_name = item.data[i].project_name;
        inform.project_start = item.data[i].project_start;
        inform.project_end = item.data[i].project_end;
        inform.signFlag = item.data[(item.data.length / 3) + i];
        inform.commitFlag = item.data[(item.data.length / 3) * 2 + i];
        this.projectInform.push(inform)
      }
    },
    //跳转注册团队界面
    toSignTeamPage(item) {
      console.log(item)
      this.$router.push({
        path: '/student/registerTeam',
        query: {
          project_id: item.project_id,
          project_name: item.project_name,
        }
      })
    },
    //跳转提交报告界面
    toCommitPage(item) {
      console.log(item)
      this.$router.push({
        path: '/student/submitReport',
        query: {
          project_id: item.project_id,
          project_name: item.project_name,
        }
      })
    },
    //显示报告消息弹窗
    reportInformCheck(item) {
      this.reportInformClick = true;
      this.project_id = item.project_id;
      this.team_information = item;
    },
    //显示团队消息弹窗
    teamInformCheck(item) {
      this.teamInformClick = true;
      this.project_id = item.project_id;
      this.team_information = item;
    }
  }
}

</script>