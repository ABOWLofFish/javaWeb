
<!-- template - 查看所有活动组件 -->

<template>

  <!-- table - 所有活动的信息 -->
  <el-table :data="projectInform">
    <el-table-column prop="project_id" label="活动号" width="120">
    </el-table-column>
    <el-table-column prop="project_name" label="活动名" width="200">
    </el-table-column>
    <el-table-column prop="project_start" label="活动发起时间" width="160" sortable>
    </el-table-column>
    <el-table-column prop="project_end" label="活动截止时间" width="160" sortable>
    </el-table-column>
    <el-table-column fixed="right" width="110">
      <template v-slot="scope">

        <!-- button - 查看活动下的所有团队 -->
        <el-button type="primary" @click="goDisplayTeamPage(scope.row)" style="margin:5px">活动团队</el-button>
      </template>
    </el-table-column>
  </el-table>

</template>

<script>
export default {
  name: 'ProjectInform',
  data() {
    return {
      projectInform: [],
      list: []
    }
  },

  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
    this.getData();
  },
  methods: {
    //获取所有项目信息
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/displayProjects")
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
    //展示活动列表
    setData(item) {
      for (let i = 0; i < item.data.length; i++) {
        let inform = {};
        inform.project_id = item.data[i].project_id;
        inform.project_name = item.data[i].project_name;
        inform.project_start = item.data[i].project_start;
        inform.project_end = item.data[i].project_end;
        inform.status = item.data[i].status;
        this.projectInform.push(inform)
      }
    },
    //页面跳转 展示活动中的所有团队
    goDisplayTeamPage(item) {
      console.log(item)
      this.$router.push({
        path: '/admin/displayTeam',
        //页面传参
        query: {
          project_id: item.project_id,
          beforeItem: 'displayProject'
        }
      })
    }
  }
}
</script>
