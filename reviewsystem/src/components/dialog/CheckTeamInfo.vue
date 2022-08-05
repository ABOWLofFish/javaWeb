
<!-- template - 查看团队基本信息组件 -->

<template>
  <div style="width:25vw;margin-left: 15px;">
    <el-divider content-position="center" style="margin-top:0;">
      {{ team_info.team_id }}-{{ team_info.team_name }}
    </el-divider>

    <!-- descriptions - 显示团队基本信息 -->
    <el-descriptions :column="2" style="margin-left:30px; ">
      <el-descriptions-item label="队长号">
        {{ team_info.captain_id }}
      </el-descriptions-item>
      <el-descriptions-item label="队长名">
        {{ team_info.captain_name }}
      </el-descriptions-item>
      <el-descriptions-item label="组员1编号">
        {{ team_info.member1_id }}
      </el-descriptions-item>
      <el-descriptions-item label="组员1名">
        {{ team_info.member1_name }}
      </el-descriptions-item>
      <el-descriptions-item label="组员2编号">
        {{ team_info.member2_id }}
      </el-descriptions-item>
      <el-descriptions-item label="组员2名">
        {{ team_info.member2_name }}
      </el-descriptions-item>
      <el-descriptions-item label="导师编号">
        {{ team_info.teacher_id }}
      </el-descriptions-item>
      <el-descriptions-item label="导师名">
        {{ team_info.teacher_name }}
      </el-descriptions-item>
    </el-descriptions>
    
    <el-divider content-position="center" style="margin-top:10px;">
      已报名成功！
    </el-divider>
  </div>
</template>

<script>
export default {
  props: {
    project_id: String,
  },
  data() {
    return {
      team_info: {
        team_id: '',
        team_name: '',
        captain_id: '',
        captain_name: '',
        member1_id: '',
        member1_name: '',
        member2_id: '',
        member2_name: '',
        teacher_id: '',
        teacher_name: ''
      },
      list: []
    }
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
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
            console.log("fail get")
          }
        })
        .catch(failResponse => {
        });
    },
    //显示团队信息
    setData(item) {
      console.log("setdata")
      this.team_info.team_id = item.data.team_id,
        this.team_info.team_name = item.data.team_name,
        this.team_info.captain_id = item.data.captain_id,
        this.team_info.captain_name = item.data.captain_name,
        this.team_info.member1_id = item.data.member1_id,
        this.team_info.member1_name = item.data.member1_name,
        this.team_info.member2_id = item.data.member2_id,
        this.team_info.member2_name = item.data.member2_name,
        this.team_info.teacher_id = item.data.teacher_id,
        this.team_info.teacher_name = item.data.teacher_name
    }
  }
}
</script>