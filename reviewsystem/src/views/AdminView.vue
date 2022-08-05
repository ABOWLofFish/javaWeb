
<!-- template - 管理员主要功能页面 -->

<template>
  <el-container style=" margin:auto;width:80%">

    <!-- head - 顶部显示 -->
    <el-header class="navBackgrond"
               style="height:15vh;display: flex;align-items: center;background-color: #ffffff; padding-left:20px;padding-right:20px;border-bottom:2px solid #eee;padding-bottom: 20px;">
      <img src="../assets/checkone.png" style="width: 12vh;height:9vh;margin-top: 30px;" />
      <div style="margin-top:60px;margin-left: 5px;font-size: larger;font-weight: 600;">
        项目审核系统
      </div>
      <div class="tipsMenu"
        style="display: flex;flex-direction: row;margin-left: auto;padding-right: 20px;margin-top:70px;font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif">
        <div class="selfInfor" @mouseover="tipsMouseOnFir" @mouseleave="tipsMouseOutFir" @click="toInformPage"
          :style="activeItemFir">
          Hello, {{ user_name }}!
        </div>&nbsp|&nbsp
        <div class="about" @mouseover="tipsMouseOnSec" @mouseleave="tipsMouseOutSec" :style="activeItemSec">
          About us
        </div>
      </div>
      <img src="../assets/blackpink.jpg" style="width:9vh;height:9vh;border-radius: 50%;margin:15px 0 0 0;" />
    </el-header>

    <el-main>
      <el-container>
        <el-aside width="200px" style="background-color: #fff;">

          <!-- menu - 导航栏菜单显示 -->
          <el-menu :default-active="activeTab" class="el-menu-vertical-demo" style="padding-left:20px;">
            <el-menu-item index="1" @click="changeTab" id="index">
              <el-icon style="vertical-align: middle;">
                <user />
              </el-icon>
              <span slot="title">用户管理</span>
            </el-menu-item>
            <el-menu-item index="2" @click="changeTab" id="index">
              <el-icon style="vertical-align: middle;">
                <folder-add />
              </el-icon>
              <span slot="title">新建项目活动</span>
            </el-menu-item>
            <el-menu-item index="3" @click="changeTab" id="index">
              <el-icon style="vertical-align: middle;">
                <document-add />
              </el-icon>
              <span slot="title">新建评审活动</span>
            </el-menu-item>
            <el-menu-item index="4" @click="changeTab" id="index">
              <el-icon style="vertical-align: middle;">
                <document />
              </el-icon>
              <span slot="title">查看活动报告</span>
            </el-menu-item>
            <el-menu-item index="5" @click="changeTab" id="index">
              <el-icon style="vertical-align: middle;">
                <chat-line-square />
              </el-icon>
              <span slot="title">查看评审意见</span>
            </el-menu-item>
          </el-menu>

        </el-aside>
        <el-main>
          
          <!-- components - 各功能组件显示 -->
          <UserManagement v-if="functionItem === 'manageUser'" />
          <NewProject v-show="functionItem === 'addProject'" />
          <NewReviewProject v-show="functionItem === 'addReview'" />
          <ProjectInform v-if="functionItem === 'displayProject'" />
          <ReviewCheck v-if="functionItem === 'displayReview'" />
          <DisplayTeam v-if="functionItem === 'displayTeam'" />

        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import UserManagement from '../components/admin/UserManagement.vue'
import NewProject from '../components/admin/NewProject.vue'
import NewReviewProject from '../components/admin/NewReviewProject.vue'
import ProjectInform from '../components/admin/ProjectInform.vue'
import ReviewCheck from '../components/admin/ReviewCheck.vue'
import DisplayTeam from '../components/admin/DisplayTeam.vue'

export default {
  name: 'StudentView',
  components: {
    ReviewCheck, ProjectInform, NewReviewProject, NewProject,
    UserManagement, DisplayTeam
  },
  data() {
    return {

      list: [],
      functionItem: '',
      activeTab: '',
      user_name: "Iris",
      activeItemFir: '',
      activeItemSec: '',
      identity: 'admin'
    }
  },
  beforeUpdate() {
    console.log("FunctionItem: " + this.$route.params.functionItem)
    this.getFunctionItem()
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
    this.getFunctionItem()
    this.getData();
  },
  methods: {
    
    getFunctionItem()
    {
      this.functionItem = this.$route.params.functionItem
      switch (this.functionItem) {
        case 'manageUser': {
          this.activeTab = '1';
          break;
        }
        case 'addProject': {
          this.activeTab = '2';
          break;
        } case 'addReview': {
          this.activeTab = '3';
          break;
        } case 'displayProject': {
          this.activeTab = '4';
          break;
        } case 'displayReview': {
          this.activeTab = '5';
          break;
        } case 'displayTeam': {
          if (this.$route.query.beforeItem == 'displayProject')
            this.activeTab = '4';
          else
            this.activeTab = '5';
          break;
        }
        default:
          break;
      }
    },
    //获取用户信息
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/info")
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.setData(successResponse.data);
          }
          else if (successResponse.data.code === 500) {
            console.log("FAIL CODE 500")
          }
        })
        .catch(failResponse => {
        })
    },
    //显示用户信息
    setData(item) {
      this.identity = this.list.identity;
      this.user_name = item.data.user_name;
    },
    changeTab(e) {
      console.log(e.index)
      switch (e.index) {
        case '1':
          this.$router.replace({ path: '/admin/manageUser' })
          break;
        case '2':
          this.$router.replace({ path: '/admin/addProject' })
          break;
        case '3':
          this.$router.replace({ path: '/admin/addReview' })
          break;
        case '4':
          this.$router.replace({ path: '/admin/displayProject' })
          break;
        case '5':
          this.$router.replace({ path: '/admin/displayReview' })
          break;
        default:
          console.log("ERROR TAB")
          break;
      }
    },
    tipsMouseOnFir() {
      this.activeItemFir = "color:RGB(83,161,252);cursor:pointer;"
    },
    tipsMouseOutFir() {
      this.activeItemFir = "color:#000"
    },
    tipsMouseOnSec() {
      this.activeItemSec = "color:RGB(83,161,252);cursor:pointer;"
    },
    tipsMouseOutSec() {
      this.activeItemSec = "color:#000"
    },
    //跳转个人中心
    toInformPage() {
      this.$router.push({
        path: '/info/' + this.identity,
      })
    }
  }
}
</script>

<style>
.navBackgrond{
  background: url("../assets/navBackground.png") no-repeat;
  background-position: center;
  background-size: 100% 100%;
}
</style>