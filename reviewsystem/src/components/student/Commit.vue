
<!-- template - 提交团队报告组件 -->

<template>
  <div v-loading="commitLoading" style="margin-left: 20px;margin-right: 20px;width: 50vw;">

    <!-- descriptions - 显示团队信息 -->
    <el-descriptions title="团队信息">
      <el-descriptions-item label="团队号">{{ team_info.team_id }}</el-descriptions-item>
      <el-descriptions-item label="队长号">{{ team_info.captain_id }}</el-descriptions-item>
      <el-descriptions-item label="指导老师">
        {{ team_info.teacher_id }}
      </el-descriptions-item>
      <el-descriptions-item label="团队名">{{ team_info.team_name }}</el-descriptions-item>
      <el-descriptions-item label="队长">{{ team_info.captain_name }}</el-descriptions-item>
    </el-descriptions>

    <!-- form - 填写提交报告 -->
    <el-form ref="form" label-width="0px">
      <el-form-item>
        <el-input type="textarea" :autosize="{ minRows: 15, maxRows: 20 }" placeholder="请输入提交内容"
          v-model="report_content">
        </el-input>
      </el-form-item>
      <el-form-item>

        <!-- button - 缓存或者提交报告 -->
        <el-button @click="onSave">缓存</el-button>
        <el-button type="primary" @click="onSubmit">提交报告</el-button>

      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { ElMessage } from "element-plus";

export default {
  name: 'NewProject',
  data() {
    return {
      list: [],
      report_content: '',
      team_info: {
        team_id: '',
        team_name: '',
        captain_id: '',
        captain_name: '',
        teacher_id: ''
      },
      project_id: '',
      commitLoading: false
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.project_id = this.$route.query.project_id;
    this.getData();
  },

  beforeUpdate() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.project_id = this.$route.query.project_id;
    this.getData();
  },
  methods: {
    //获取团队信息
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/display_teamInfo")
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
    //显示团队信息
    setData(item) {
      this.team_info = {};
      this.team_info.team_id = item.data.team_id;
      this.team_info.team_name = item.data.team_name;
      this.team_info.captain_id = item.data.captain_id;
      this.team_info.captain_name = item.data.captain_name;
      this.team_info.teacher_id = item.data.teacher_id;
      this.getCache()
    },
    //获取缓存内容
    getCache() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/submitReport")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.report_content = successResponse.data.data
          } else if (successResponse.data.code === 500) {
            console.log("fail get cache")
          }
        })
        .catch(failResponse => {
        })
    },
    //缓存已填写报告内容
    onSave() {
      this.$http
        .post("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/submitReport/cache", {
          report_content: this.report_content
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            console.log("commit success")
            setTimeout(() => {
              this.$router.go(-1)
            }, 500)
          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },

    //提交活动报告
    onSubmit() {
      //未全部填写则弹窗提示警告不予通过
      if (this.report_content === '') {
        this.$message.error('请填写完整后再提交!');
        return;
      } else {
        this.$http
          .post("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/summitReport" + this.team_info.team_id, {
            report_content: this.report_content
          })
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              console.log("commit success")
              ElMessage({
                type: 'success',
                message: '提交成功',
              })
              setTimeout(() => {
                this.$router.go(-1)
              }, 500)
            } else if (successResponse.data.code === 500) {
              ElMessage({
                type: 'error',
                message: '提交失败',
              })
              setTimeout(() => {
                this.$router.go(-1)
              }, 500)
            }
          })
          .catch(failResponse => {
          })
      }
    }
  }
}
</script>