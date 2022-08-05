
<!-- template - 老师主要功能页面 -->

<template>
  <el-container style=" margin:auto;width:80%">

    <!-- head 顶部显示 -->
    <el-header class="navBackgrond"
      style="height:15vh;display: flex;align-items: center;background-color: #ffffff; padding-left:20px;padding-right:20px;border-bottom:2px solid #eee;padding-bottom: 20px;">
      <img src="../assets/checkone.png" style="width: 12vh;height:9vh;margin-top: 30px;" />
      <div style="margin-top:60px;margin-left: 5px;font-size: larger;font-weight: 600;">
        项目审核系统
      </div>
      <div class="tipsMenu"
        style="display: flex;flex-direction: row;margin-left: auto;padding-right: 20px;margin-top:70px;font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif">
        <div class="selfInfor" @mouseover="tipsMouseOnFir" @mouseleave="tipsMouseOutFir" :style="activeItemFir">
          Hello, {{ list.user_name }}!
        </div>
        &nbsp|&nbsp
        <div class="about" @mouseover="tipsMouseOnSec" @mouseleave="tipsMouseOutSec" :style="activeItemSec">
          About us
        </div>
      </div>
      <img src="../assets/blackpink.jpg" style="width: 9vh;height:9vh;border-radius: 50%;margin:15px 0 0 0;" />
    </el-header>

    <el-main style="margin: auto;width: 75%">

      <!-- components - 各功能组件显示 -->
      <DisplayTeam />

    </el-main>
  </el-container>
</template>

<script>
import DisplayTeam from '../components/teacher/DisplayTeam.vue'

export default {
  name: 'TeacherView',
  components: {
    DisplayTeam
  },
  data() {
    return {
      list: [],
      activeItemFir: '',
      activeItemSec: '',
    }
  },
  mounted() {
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
  },

  methods: {
    //hover
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
  },

}
</script>

<style>
.navBackgrond {
  background: url("../assets/navBackground.png") no-repeat;
  background-position: center;
  background-size: 100% 100%;
}
</style>