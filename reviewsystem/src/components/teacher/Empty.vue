
<!-- template - 导师链接跳转身份验证组件 -->

<template>

  <!-- dialog - 身份验证 -->
  <el-dialog title="身份验证" v-model="identityValid" width="300px" center :close-on-click-modal="false"
    :close-on-press-escape="false">
    <span style=" margin:atuo;">
      <el-input v-model="teacherName" style="width:170px; " placeholder="请输入您的真实姓名"></el-input>
      <el-Button type="primary" style="margin:10px;" @click="checkInfo">确认</el-Button>
    </span>
  </el-dialog>

</template>

<script>

import { ElMessage } from "element-plus";

export default {
  data() {
    return {
      identityValid: true,
      teacherName: '',
      review_id: '',
      information: {
        user_name: '',
        user_id: '',
        identity: ''
      }
    }
  },
  methods: {
    //检查该导师是否有权限
    checkInfo() {
      this.review_id = this.$route.params.review_id //从路由获取评审号
      this.$http
        .post("/teacher/" + this.review_id + "/check", {
          user_name: this.teacherName
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            //初始化导师消息
            this.information.user_name = this.teacherName
            this.information.user_id = successResponse.data.data.user_id
            this.information.identity = 'teacher'

            let userInfo = JSON.stringify(this.information);
            sessionStorage.setItem("userInfo", userInfo); //会话存储该用户信息
            this.identityValid = false
            //跳转显示参与团队界面
            this.$router.push({
              path: '/teacher/displayTeam/' + this.review_id,
              query: {
                project_id: this.review_id,
                user_id: this.information.user_id
              }
            })
          } else if (successResponse.data.code === 500) {
            console.log("commit fail")
            ElMessage({
              type: 'error',
              message: '您没有权限',
            })
          }
        })
        .catch(failResponse => {
        })
    }
  }
}


</script>