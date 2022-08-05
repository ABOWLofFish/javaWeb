<template>
    <router-view />
</template>

<script>
// @ is an alias to /src

export default {
  data() {
    return {
      teacherHead:'reviews',
      studentHead:'projects',
      adminHead:'manageUser'
    }
  },
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
