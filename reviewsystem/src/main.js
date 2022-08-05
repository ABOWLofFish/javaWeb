// 前端 http://localhost:8081/
// 后端
/**
 * @Description: 自定义服务器响应数据结构
 *              200：成功
 *              500：错误，错误信息在Msg字段中
 *              501：bean验证错误，以Map形式返回
 *              555:异常抛出信息
 * @author： 庄婉如
 * @date: 2022/6/27 18:05
 * @Version:v1.0
 */

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'
import locale from "element-plus/lib/locale/lang/zh-cn"
import * as ElIconModules from '@element-plus/icons-vue'

const app = createApp(App)

//设置axios的请求根路径
axios.defaults.baseURL = '/api'

//每个组件可以通过this直接访问到全局挂载的自定义属性
app.config.globalProperties.$http = axios

//挂载icon
Object.keys(ElIconModules).forEach(function(key) {
    app.component(ElIconModules[key].name, ElIconModules[key])
})

//添加自带的store,element-ui和router组件
app.use(store).use(router).use(ElementPlus, { locale }).mount('#app')