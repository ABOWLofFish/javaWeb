
<!-- template - 新建评审活动组件 -->

<template>
  <div v-loading="commitLoading" style="margin-left: 20px;margin-right: 20px;width: 35vw;">

    <!-- form - 新建评审活动 -->
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="活动号">
        <el-input v-model="form.project_id"></el-input>
      </el-form-item>
      <el-form-item label="评审名">
        <el-input v-model="form.review_name"></el-input>
      </el-form-item>
      <el-form-item label="评审发起时间">
        <el-col :span="9">
          <el-date-picker type="datetime" placeholder="选择发起时间" v-model="form.review_start" style="width: 100%;">
          </el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="评审截止时间">
        <el-col :span="9">
          <el-date-picker type="datetime" placeholder="选择截止时间" v-model="form.review_end" style="width: 100%;">
          </el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="评审内容">
        <el-input type="textarea" v-model="form.review_description"></el-input>
      </el-form-item>
      <el-form-item label="评审老师">
        <el-select-v2 v-model="form.review_teachers" :options="teacher_options" placeholder="请选择评审老师"
          style="width: 240px" multiple filterable clearable :reserve-keyword="false" />
      </el-form-item>
      <el-form-item>

        <!-- button - 点击创建评审活动 -->
        <el-button type="primary" @click="onSubmit">立即创建</el-button>

      </el-form-item>
    </el-form>

  </div>
</template>

<script>
export default {
  name: 'NewReviewProject',
  data() {
    return {
      form: {
        project_id: '',
        review_name: '',
        review_start: '',
        review_end: '',
        review_description: '',
        review_teachers: []
      },
      teacher_options: [],
      list: [],
      commitLoading: false
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
    this.getData();
  },
  methods: {
    //获取所有老师信息 用于管理员选择
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/addReview/get_teachers")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            for (let i = 0; i < successResponse.data.data.length; i++) {
              this.teacher_options[i] = {
                value: successResponse.data.data[i].id,
                label: successResponse.data.data[i].name
              }

            }

          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },
    //转化日期格式
    getTime(time) {
      var date = new Date(time)
      var y = date.getFullYear()
      var m = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1)
      var d = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate())
      return y + '-' + m + '-' + d
    },
    //提交新评审
    onSubmit() {
      //未全部填写则弹窗提示警告不予通过
      if (this.form.review_name === '' || this.form.review_start === '' || this.form.review_end === '' || this.form.review_description === '') {
        this.$message.error('请填写完整后再提交!');
        return;
      } else {
        this.commitLoading = true
        var start_time = this.getTime(this.form.review_start)
        var end_time = this.getTime(this.form.review_end)
        this.$http
          .post("/" + this.list.identity + this.list.user_id + "/addReview", {
            review_id: this.form.project_id,
            review_name: this.form.review_name,
            review_start: start_time,
            review_end: end_time,
            review_description: this.form.review_description,
            ids: this.form.review_teachers
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
              console.log('FAIL CODE 500')
              this.commitLoading = false
              this.$message({
                message: '查询不到对应活动号，创建失败!',
                type: 'warning'
              });
            }
          })
          .catch(failResponse => {
          })
      }
    }
  }
}
</script>