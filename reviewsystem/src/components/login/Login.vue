
<!-- template - 登陆页面组件 -->

<template>
  <div id="poster">

    <!-- from - 登录信息填写 -->
    <el-form class="login-container" label-position="left" label-width="0px">
      <h3 class="login_title">系统登录</h3>
      <el-form-item>
        <el-input class="id" type="text" v-model="loginForm.user_id" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-radio-group style=" display:flex; justify-content: center; width: 100%" v-model="loginForm.identity">
          <el-radio label="学生"></el-radio>
          <el-radio label="管理员"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <div style=" display :flex; justify-content: center; width: 100%">

          <!-- button - 选择登录或注册 -->
          <el-button type="primary" style="width: 30%;background: #505458;border: none" v-on:click="login">登录
          </el-button>
          <el-button type="primary" style="width: 30%;background: #505458;border: none" v-on:click="register">注册
          </el-button>
          
        </div>
      </el-form-item>


    </el-form>
  </div>
</template>

<script>
import store from '../../store'
import { ElMessage } from "element-plus";

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        user_id: '',
        password: '',
        identity: '学生'
      },
      responseResult: []
    }
  },
  created() {
    store.state.adminHead
  },
  methods: {
    //登录判断
    login() {
      this.$http
        .post("", {
          user_id: this.loginForm.user_id,
          password: this.loginForm.password,
          identity: this.loginForm.identity
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.UserInfoSubmit()
          }
          else if (successResponse.data.code === 500) {
            console.log(successResponse.data)
            ElMessage({
              type: 'error',
              message: '登录失败',
            })
          }
        })
        .catch(failResponse => {
        })
    },
    //跳转注册页面
    register() {
      this.$router.replace({ path: '/register' })
    },
    //初始化登录用户信息
    UserInfoSubmit() {
      switch (this.loginForm.identity) {
        case '学生': {
          this.loginForm.identity = "student"
          break;
        }
        case '管理员': {
          this.loginForm.identity = "admin"
          break;
        }
      }
      let userInfo = JSON.stringify(this.loginForm);
      sessionStorage.setItem("userInfo", userInfo); //会话存储该用户信息
      //跳转主界面
      switch (this.loginForm.identity) {
        case 'student': {
          this.$router.replace({ path: '/student/projects' })
          break;
        }
        case 'admin': {
          this.$router.replace({ path: '/admin/manageUser' })
          break;
        }
      }
    },
  }
}
</script>


<style>
#poster {
  background: url("../../assets/logininterface.png") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}

body {
  margin: 0px;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin-top: 230px;
  margin-left:250px;
  width: 300px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
