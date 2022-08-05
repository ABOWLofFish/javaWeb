import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import Register from '../views/RegisterView.vue'
import StudentView from '../views/StudentView.vue'
import TeacherView from '../views/TeacherView.vue'
import AdminView from '../views/AdminView.vue'
import InforView from '../views/InfoView.vue'
import UpdateView from '../views/UpdateView.vue'
import  CheckView from  '../components/teacher/Empty'


const routes = [{
        path: '/',
        name: 'login',
        component: LoginView
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    }, {
        path: '/student/:functionItem',
        name: 'student',
        component: StudentView
    }, {
        path: '/teacher/:functionItem/:review_id',
        name: 'teacher',
        component: TeacherView
    },
    {
        path: '/admin/:functionItem',
        name: 'Admin',
        component: AdminView
    }, {
        path: '/info/:identity',
        name: 'info',
        component: InforView
    }, {
        path: '/update/:identity',
        name: 'update',
        component: UpdateView
    },{
        path: '/check/:review_id',
        name: 'check',
        component: CheckView
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router