const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    proxy:{
      //api是后端数据接口的路径
      'api':{
        //后端数据接口的地址
        target:'http://localhost:8086',
        //允许跨域
        changeOrigin:true,
        //调用时用api替代根路径
        pathRewrite:{
          '^/api':""
        }
      }
    }
  },
  //关闭eslint校验
  lintOnSave:false
})
