
<!-- template - 登录页面父页面 -->

<template>
  <div class="login">
    <Login />
  </div>
</template>

<script>
// @ is an alias to /src
import Login from '@/components/login/Login.vue'

export default {
  name: 'LoginView',
  components: {
    Login
  }
}
</script>
