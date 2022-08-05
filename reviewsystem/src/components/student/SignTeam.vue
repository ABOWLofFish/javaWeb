
<!-- template - 注册团队组件 -->

<template>
  <div v-loading="commitLoading" style="margin-left: 20px;margin-right: 20px;width: 20vw;">
    <ElHeader> {{ project_name }}</ElHeader>

    <!-- form - 注册团队 -->
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="团队名">
        <el-input v-model="form.team_name"></el-input>
      </el-form-item>
      <el-form-item label="队长编号">
        <el-input v-model="form.captain_id"></el-input>
      </el-form-item>
      <el-form-item label="组员1编号">
        <el-input v-model="form.member1_id"></el-input>
      </el-form-item>
      <el-form-item label="组员2编号">
        <el-input v-model="form.member2_id"></el-input>
      </el-form-item>
      <el-form-item label="指导老师编号">
        <el-input v-model="form.teacher_id"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">注册团队</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>


<script>
import { ElMessage } from 'element-plus'

export default {
  name: 'NewProject',
  data() {
    return {
      form: {
        team_name: '',
        captain_id: '',
        member1_id: '',
        member2_id: '',
        teacher_id: '',
      },
      commitLoading: false,
      list: [],
      project_id: '',
      project_name: ''
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.project_id = this.$route.query.project_id;
    this.project_name = this.$route.query.project_name;
  },
  methods: {
    onSubmit() {
      //未全部填写则弹窗提示警告不予通过
      if (this.form.team_name === '' || this.form.captain_id === '' || this.form.member1_id === '' || this.form.member2_id === '') {
        this.$message.error('请填写完整后再提交!');
        return;
      } else {
        this.commitLoading = true
        this.$http
          .post("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/registerTeam", {
            team_name: this.form.team_name,
            captain_id: this.form.captain_id,
            member1_id: this.form.member1_id,
            member2_id: this.form.member2_id,
            teacher_id: this.form.teacher_id,
          })
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              this.commitLoading = false
              ElMessage({
                message: '注册成功',
                type: 'success',
              })
              setTimeout(() => {
                this.$router.go(-1);
              }, 500);
            } else if (successResponse.data.code === 500) {
              this.commitLoading = false
              if(successResponse.data.msg="find no student")
                ElMessage.error('无法查询到学生账号，创建失败!')
              else if(successResponse.data.msg="find no teacher")
                ElMessage.error('无法查询到导师账号，创建失败!')
            }
          })
          .catch(failResponse => {
          })

      }
    }
  }
}
</script>