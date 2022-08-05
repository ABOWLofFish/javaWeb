
<!-- template - 新建活动组件 -->

<template>
  <div v-loading="commitLoading" style="margin-left: 20px;margin-right: 20px;width: 35vw;">

    <!-- form - 新建活动 -->
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="活动名称">
        <el-input v-model="form.project_name"></el-input>
      </el-form-item>
      <el-form-item label="活动发起时间">
        <el-col :span="9">
          <el-date-picker type="datetime" placeholder="选择发起时间" v-model="form.project_start" style="width: 100%;">
          </el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="活动截止时间">
        <el-col :span="9">
          <el-date-picker type="datetime" placeholder="选择截止时间" v-model="form.project_end" style="width: 100%;">
          </el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="活动形式">
        <el-input type="textarea" v-model="form.project_description"></el-input>
      </el-form-item>
      <el-form-item>

        <!-- button - 点击创建活动 -->
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
        
      </el-form-item>
    </el-form>

  </div>
</template>


<script>
export default {
  name: 'NewProject',
  data() {
    return {
      form: {
        project_name: '',
        project_start: '',
        project_end: '',
        project_description: ''
      },
      list: [],
      commitLoading: false
    }
  },

  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
  },
  methods: {
    //转化日期格式
    getTime(time) {
      var date = new Date(time)
      var y = date.getFullYear()
      var m = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1)
      var d = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate())
      return y + '-' + m + '-' + d
    },
    //提交新项目
    onSubmit() {
      if (this.form.project_name === '' || this.form.project_start === '' || this.form.project_end === '' || this.form.project_description === '') {
        this.$message.error('请填写完整后再提交!');
        return;
      } else {
        this.commitLoading = true
        var start_time = this.getTime(this.form.project_start)
        var end_time = this.getTime(this.form.project_end)
        this.$http
          .post("/" + this.list.identity + this.list.user_id + "/addProject", {
            project_name: this.form.project_name,
            project_start: start_time,
            project_end: end_time,
            project_description: this.form.project_description
          })
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              this.commitLoading = false
              this.$message({
                message: '新建成功',
                type: 'success'
              });
              setTimeout(() => {
                this.$router.go(0)
              }, 500)
            } else if (successResponse.data.code === 500) {
              console.log("FAIL CODE 500")
              this.commitLoading = false
              this.$router.go(0)
            }
          })
          .catch(failResponse => {
          })
      }
    }
  }
}
</script>