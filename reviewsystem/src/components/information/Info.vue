
<!-- template - 查看个人信息组件 -->

<template>
    <el-container style=" margin:auto;width:80%">

        <!-- head - 顶部显示 -->
        <el-header class="navBackgrond"
            style="height:15vh;display: flex;align-items: center;background-color: #ffffff; padding-left:20px;padding-right:20px;border-bottom:2px solid #eee;padding-bottom: 20px;">
            <img src="../../assets/checkone.png" style="width: 12vh;height:9vh;margin-top: 30px;" />
            <div style="margin-top:60px;margin-left: 5px;font-size: larger;font-weight: 600;">
                项目审核系统
            </div>
            <div class="tipsMenu"
                style="display: flex;flex-direction: row;margin-left: auto;padding-right: 20px;margin-top:70px;font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif">
                <div class="selfInfor" @mouseover="tipsMouseOnFir" @mouseleave="tipsMouseOutFir" :style="activeItemFir">
                    Hello, {{ user_name }}!
                </div>&nbsp|&nbsp
                <div class="about" @mouseover="tipsMouseOnSec" @mouseleave="tipsMouseOutSec" :style="activeItemSec">
                    About us
                </div>
            </div>
            <img src="../../assets/blackpink.jpg" style="width: 9vh;height:9vh;border-radius: 50%;margin:15px 0 0 0;" />
        </el-header>

        <el-main>

            <!-- div - 页面提示 -->
            <div class="infor">
                <h3 style="margin: 25px">个人信息</h3>
                <img src="../../assets/blackpink.jpg"
                    style="width: 20vh;height:20vh;border-radius: 50%;margin:0 0 0 0;" />
            </div>

            <!-- divider - 个人基本信息展示 -->
            <el-divider></el-divider>
            <div class="text">
                <h3>用户名</h3>
                <span>{{ user_name }}</span>
            </div>
            <el-divider></el-divider>
            <div class="text">
                <h3>用户账号id</h3>
                <span>{{ user_id }}</span>
            </div>
            <el-divider></el-divider>
            <div class="text">
                <h3>手机号</h3>
                <span>{{ telephone }}</span>
            </div>
            <el-divider></el-divider>
            <div class="text">
                <h3>邮箱</h3>
                <span>{{ e_mail }}</span>
            </div>
            <el-divider></el-divider>
            <div class="text">
                <h3>身份</h3>
                <span>{{ identity }}</span>
            </div>

            <!-- button - 点击进入修改信息页面 -->
            <el-button style="margin: 25px" type="primary" plain v-on:click="update">修改信息</el-button>

        </el-main>
    </el-container>
</template>

<script>
export default {
    name: 'info',
    components: {
    },
    data() {
        return {
            list: [],
            user_name: "",
            user_id: "",
            telephone: "",
            e_mail: "",
            identity: "",
            activeItemFir: '',
            activeItemSec: '',
        }

    },
    created() {

        this.list = JSON.parse(sessionStorage.getItem("userInfo"));
        this.getData();
    },
    methods: {
        //获取个人信息
        getData() {
            this.$http.get("/" + this.list.identity + this.list.user_id + "/info")
                .then(successResponse => {
                    if (successResponse.data.code === 200) {
                        this.setData(successResponse.data);
                    }
                    else if (successResponse.data.code === 500) {
                        console.log("FAIL CODE 500")
                    }
                })
                .catch(failResponse => {
                })
        },
        //显示个人信息 
        setData(item) {
            if (this.list.identity == 'student')
                this.identity = '学生';
            else
                this.identity = '管理员';
            this.user_id = this.list.user_id;
            if (this.$route.query.user_name !== undefined)
                this.user_name = this.$route.query.user_name;
            else
                this.user_name = item.data.user_name;
            if (this.$route.query.telephone !== undefined)
                this.telephone = this.$route.query.telephone;
            else
                this.telephone = item.data.telephone;
            if (this.$route.query.e_mail !== undefined)
                this.e_mail = this.$route.query.e_mail;
            else
                this.e_mail = item.data.e_mail;
        },
        //跳转更新个人信息界面
        update() {
            this.$router.replace({ path: '/update/' + this.identity })
        },

        //标题变色显示
        tipsMouseOnFir() {
            this.activeItemFir = "color:RGB(83,161,252);cursor:pointer;"
        },
        tipsMouseOutFir() {
            this.activeItemFir = "color:#000"
        },
        tipsMouseOnSec() {
            this.activeItemSec = "color:RGB(83,161,252);cursor:pointer;"
        },
        tipsMouseOutSec() {
            this.activeItemSec = "color:#000"
        }
    }
}
</script>

<style scoped>
.infor {
    display: flex;
    align-items: center;
    font-size: smaller;
}

.text {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin: 25px;
    font-size: smaller;
}

.navBackgrond {
    background: url("../../assets/navBackground.png") no-repeat;
    background-position: center;
    background-size: 100% 100%;
}
</style>