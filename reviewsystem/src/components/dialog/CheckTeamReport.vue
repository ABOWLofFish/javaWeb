
<!-- template - 查看团队报告组件 -->

<template>
  <div style="margin-left: 20px;margin-right: 20px;">

    <!-- descriptions - 显示团队信息 -->
    <el-descriptions title="团队信息">
      <el-descriptions-item label="团队号">{{ team_information.team_id }}</el-descriptions-item>
      <el-descriptions-item label="队长号">{{ team_information.captain_id }}</el-descriptions-item>
      <el-descriptions-item label="指导老师">
        {{ team_information.teacher_id }}
      </el-descriptions-item>
      <el-descriptions-item label="团队名">{{ team_information.team_name }}</el-descriptions-item>
      <el-descriptions-item label="队长">{{ team_information.captain_name }}</el-descriptions-item>
    </el-descriptions>

    <!-- divider - 显示团队报告 -->
    <el-divider content-position="center">
      <el-icon>
        <reading />
      </el-icon>
      团队报告
    </el-divider>
    <el-form ref="team_info" label-width="0px">
      <el-form-item>
        <div style="margin-top:5px;padding:10px">
          {{ report_content }}
        </div>
      </el-form-item>
    </el-form>
    <el-divider>
    </el-divider>

  </div>
</template>

<script>
export default {
  props: {
    project_id: String,
    user_id: String
  },
  data() {
    return {
      list: [],
      report_content: '',
      team_information: {}
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.getData();
  },
  //页面数据改变 重新获取数据
  beforeUpdate() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.getData();
  },
  methods: {
    //获取团队信息
    getData() {
      //如果父组件未传参 则为管理员查看报告
      if (this.user_id == '' || this.user_id == undefined) {
        this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/display_teamInfo")
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              this.team_information = successResponse.data.data;
              this.setData()
            }
            else if (successResponse.data.code === 500) {
              console.log("FAIL CODE 500")
            }
          })
          .catch(failResponse => {
          });
      }
      //否则为学生查看报告
      else {
        this.$http.get("/student" + this.user_id + "/" + this.project_id + "/display_teamInfo")
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              this.team_information = successResponse.data.data;
              this.setData()
            }
            else if (successResponse.data.code === 500) {
              console.log("FAIL CODE 500")
            }
          })
          .catch(failResponse => {
          });
      }
    },
    //显示团队报告信息
    setData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/report" + this.team_information.team_id)
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            if (successResponse.data.data.report_content == null)
              this.report_content = "暂未提交报告"
            else
              this.report_content = successResponse.data.data.report_content
          }
          else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        });

    }
  }
};
</script>