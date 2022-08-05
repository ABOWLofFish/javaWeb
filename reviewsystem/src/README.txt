6.27 
即分享文件之后的修改
1.增加了动态修改路由的方式
App.vue
<router-link :to="'/'+identity+'/inform'">self</router-link>
identity为变量，可赋值

router/index.js
{
        path: '/:identity/inform',
        name: 'self',
        component: SelfView
},

2.增加了SelfView.vue同其他页面的交集
即点击Hello Iris! 会跳转到个人中心该交互作业

--iris

6.27

--ppduck