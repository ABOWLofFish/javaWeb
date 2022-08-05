
<!-- template - 进行评审组件 -->

<template>
  <div>
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
    <el-divider />

    <div>
       
       <!-- form - 进行评审 -->
      <el-form ref="team_info" label-width="70px">

        <!-- 评审分数 -->
        <el-form-item label="评审分数">
          <el-input-number v-model="review_result.review_score" :min="0" :max="100" controls-position="right" />
        </el-form-item>

        <!-- 评审意见 -->
        <el-form-item label="评审意见">
          <el-input type="textarea" :autosize="{ minRows: 10, maxRows: 15 }" v-model="review_result.review_content">
          </el-input>
        </el-form-item>

        <!-- 提交评审 -->
        <el-form-item>
          <el-button @click="onSave()">
            缓存
          </el-button>
          <el-button type="primary" @click="submitResult()">
            提交评审
          </el-button>
        </el-form-item>
      </el-form>
      
    </div>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus'

export default {
  props: {
    team_information: Object,
    project_id: String
  },
  data() {
    return {
      review_result: {
        review_score: '',
        review_content: '',
      },
      report_content: '',
      list: [],
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
    this.getData();
  },
  methods: {
    //获取团队报告内容
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/" + this.team_information.team_id + "/report")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.setReport(successResponse.data);
          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },
    //显示团队报告信息
    setReport(item) {
      this.report_content = item.data.report_content
      this.getCache();
    },
    //获取暂存内容
    getCache() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/" + this.team_information.team_id + "/report/cache")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.review_result.review_content = successResponse.data.data.review_content
            this.review_result.review_score = successResponse.data.data.review_level
          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },
    //暂存评审内容
    onSave() {
      this.$http
        .post("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/makeReview/cache", {
          review_content: this.review_result.review_content,
          review_level: this.review_result.review_score
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            ElMessage({
              showClose: true,
              message: '缓存成功',
              type: 'success',
            })

          } else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },
    //提交审评报告
    submitResult() {
      this.$http
        .post("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/" + this.team_information.team_id + "/makeReview", {
          review_content: this.review_result.review_content,
          review_level: this.review_result.review_score
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            ElMessage({
              message: '提交成功',
              type: 'success',
            });
            this.$emit("Close");
          } else if (successResponse.data.code === 500) {
            ElMessage.error('提交失败')
          }
        })
        .catch(failResponse => {
        })
    }
  }
};
</script>