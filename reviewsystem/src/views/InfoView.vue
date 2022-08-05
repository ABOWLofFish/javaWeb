
<!-- template - 个人信息父页面 -->

<template>
  <div class="info">
    <Info />
  </div>
</template>

<script>
// @ is an alias to /src
import Info from '@/components/information/Info.vue'

export default {
  name: 'InfoView',
  components: {
    Info
  }
}
</script>
