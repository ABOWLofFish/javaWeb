
<!-- template - 查看评审报告组件 -->

<template>
  <div style="margin-left: 20px;margin-right: 20px;">

    <!-- descriptions 展示团队基本信息 -->
    <el-descriptions title="团队信息">
      <el-descriptions-item label="团队号">{{ checkTeamInform.team_id }}</el-descriptions-item>
      <el-descriptions-item label="队长号">{{ checkTeamInform.captain_id }}</el-descriptions-item>
      <el-descriptions-item label="指导老师">
        {{ checkTeamInform.teacher_id }}
        <!-- <el-tag size="small"></el-tag> -->
      </el-descriptions-item>
      <el-descriptions-item label="团队名">{{ checkTeamInform.team_name }}</el-descriptions-item>
      <el-descriptions-item label="队长">{{ checkTeamInform.captain_name }}</el-descriptions-item>
    </el-descriptions>

    <!-- div - 显示团队报告 -->
    <el-divider content-position="center">
      <el-icon>
        <reading />
      </el-icon>
      团队报告
    </el-divider>
    <div style="margin-top:5px;padding:10px">
      {{ report_content }}
    </div>

    <!-- divider - 显示审评均分 -->
    <el-divider content-position="center">
      <el-icon>
        <medal />
      </el-icon>
      审评均分：{{ averageScore }}
    </el-divider>

    <div style="margin-top:5px;padding:10px" v-for="item in review_inform">

    <!-- descriptions - 显示评审信息 -->
      <el-descriptions column="1">
        <!-- 导师编号 -->
        <el-descriptions-item>
          <template #label>
            <el-icon :style="iconStyle">
              <user />
            </el-icon>
            导师编号
          </template>
          {{ item.review_teacher }}
        </el-descriptions-item>

        <!-- 导师打分 -->
        <el-descriptions-item>
          <template #label>
            <el-icon :style="iconStyle">
              <EditPen />
            </el-icon>
            导师打分
          </template>
          {{ item.review_score }}
        </el-descriptions-item>

        <!-- 导师建议 -->
        <el-descriptions-item>
          <template #label>
            <el-icon :style="iconStyle">
              <tickets />
            </el-icon>
            导师建议
          </template>
          {{ item.review_content }}
        </el-descriptions-item>
      </el-descriptions>
      <el-divider>
      </el-divider>
    </div>

  </div>
</template>

<script>

export default {
  props: {
    project_id: String,
    checkTeamInform: Object,
    report_content: String,
    averageScore: String,
    review_inform: Array
  },
  data() {
    return {
      list: []
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
  },
  methods: {}
};
</script>