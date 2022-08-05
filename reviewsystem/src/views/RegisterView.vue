
<!-- template - 注册页面父页面 -->

<template>
  <div class="register">
    <Register />
  </div>
</template>

<script>
// @ is an alias to /src
import Register from '@/components/login/Register.vue'

export default {
  name: 'RegisterView',
  components: {
    Register
}
}
</script>
