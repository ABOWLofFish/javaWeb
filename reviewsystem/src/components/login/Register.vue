
<!-- template - 注册页面组件 -->

<template>
  <div id="poster">

    <!-- form - 注册账号信息填写 -->
    <el-form class="register-container" :rules="rules" ref="registerForm" :model="registerForm" label-position="left"
      label-width="0px">
      <h3 class="register_title">注册账号</h3>
      <el-form-item prop="name">
        <el-input type="text" v-model="registerForm.user_name" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item prop="pass">
        <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item prop="checkPass">
        <el-input type="password" v-model="registerForm.confirm_password" placeholder="确认密码"></el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input type="text" v-model="registerForm.telephone" placeholder="电话"></el-input>
      </el-form-item>
      <el-form-item prop="e_mail">
        <el-input type="text" v-model="registerForm.e_mail" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="identity">
        <el-radio-group style=" display:flex; justify-content: center; width: 100%" v-model="registerForm.identity">
          <el-radio label="学生"></el-radio>
          <el-radio label="管理员"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <div style=" display :flex; justify-content: center; width: 100%">

          <!-- button 注册或取消注册 -->
          <el-button type="primary" style="width: 30%;background: #505458;border: none"
            @click="submitForm('registerForm')">注册</el-button>
          <el-button type="primary" style="width: 30%;background: #505458;border: none" v-on:click="cancel">取消
          </el-button>

        </div>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>

export default {
  name: 'Register',
  data() {
    //输入信息格式判断
    var validatePass = (rule, value, callback) => {
      var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
      if (this.registerForm.password === '' || this.registerForm.password === null) {
        callback(new Error('请输入密码'));
      } else if (!reg.test(this.registerForm.password)) {
        callback([new Error('密码格式不正确')]);
      }
      else {
        if (this.registerForm.confirm_password !== '') {
          this.$refs.registerForm.validateField('checkPass');
        }
        callback();
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (this.registerForm.confirm_password === '') {
        callback(new Error('请再次输入密码'));
      } else if (this.registerForm.confirm_password != this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }
    var checkUser_name = (rule, value, callback) => {
      if (this.registerForm.user_name === '') {
        callback(new Error('请输入用户名'));
      } else {
        callback();
      }
    }
    var checkTelephone = (rule, value, callback) => {
      var reg = /[0-9]{11}$/;
      if (this.registerForm.telephone === '') {
        callback(new Error('请输入手机号'));
      } else if (!reg.test(this.registerForm.telephone)) {
        callback([new Error('手机号格式不正确')]);
      } else {
        callback();
      }
    }
    var checkE_mail = (rule, value, callback) => {
      var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (this.registerForm.e_mail === '') {
        callback(new Error('请输入邮箱'));
      } else if (!reg.test(this.registerForm.e_mail)) {
        callback([new Error('邮箱格式不正确')]);
      } else {
        callback();
      }
    }
    var checkIdentity = (rule, value, callback) => {
      if (this.registerForm.identity === '') {
        callback(new Error('请选择身份'));
      } else {
        callback();
      }
    }
    return {
      registerForm: {
        user_id: '',
        user_name: '',
        password: '',
        confirm_password: '',
        telephone: '',
        e_mail: '',
        identity: ''
      },
      rules: {
        name: [
          { validator: checkUser_name, trigger: 'blur' }
        ],
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        phone: [
          { validator: checkTelephone, trigger: 'blur' }
        ],
        e_mail: [
          { validator: checkE_mail, trigger: 'blur' }
        ],
        identity: [
          { validator: checkIdentity, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    //提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http
            .post("/Register", {
              user_name: this.registerForm.user_name,
              password: this.registerForm.password,
              confirm_password: this.registerForm.confirm_password,
              telephone: this.registerForm.telephone,
              e_mail: this.registerForm.e_mail,
              identity: this.registerForm.identity
            })
            .then(successResponse => {
              if (successResponse.data.code === 200) {
                this.registerForm.user_id = successResponse.data.data
                this.goMainPage();
              }
              else if (successResponse.data.code === 500) {
                console.log('FAIL CODE 500')
              }
            })
            .catch(failResponse => {
            })
        } else {
          return false;
        }
      });
    },
    //注册成功 初始化用户信息 
    goMainPage() {
      switch (this.registerForm.identity) {
        case '学生': {
          this.registerForm.identity = "student"
          break;
        }
        case '管理员': {
          this.registerForm.identity = "admin"
          break;
        }
      }
      let userInfo = JSON.stringify(this.registerForm); //会话存储该用户信息
      sessionStorage.setItem("userInfo", userInfo);
      //跳转主页面
      switch (this.registerForm.identity) {
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
    //取消注册 跳转登录页面
    cancel() {
      this.$router.replace({ path: '/' })
    }
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

.register-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin-top: 160px;
  margin-left: 250px;
  width: 300px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.register_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>

