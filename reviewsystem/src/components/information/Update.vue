
<!-- template - 修改个人信息组件 -->

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
                    Hello, {{ ruleForm.user_name }}!
                </div>&nbsp|&nbsp
                <div class="about" @mouseover="tipsMouseOnSec" @mouseleave="tipsMouseOutSec" :style="activeItemSec">
                    About us
                </div>
            </div>
            <img src="../../assets/blackpink.jpg" style="width: 9vh;height:9vh;border-radius: 50%;margin:15px 0 0 0;" />
        </el-header>

        <el-main style="display: flex;flex-direction:column;justify-items: center;align-items:center;">

            <!-- div - 页面提示 -->
            <div>
                <h3 style="margin: 25px">个人信息</h3>
                <img src="../../assets/blackpink.jpg"
                    style="width: 20vh;height:20vh;border-radius: 50%;margin:0 0 0 0;" />
            </div>

            <div>

                <!-- form - 修改个人信息 -->
                <el-form status-icon :rules="rules" :model="ruleForm" ref="ruleForm" style="width:250px">
                    <h3>用户名</h3>
                    <el-form-item prop="name">
                        <el-input type="text" v-model="this.ruleForm.user_name"></el-input>
                    </el-form-item>
                    <h3>密码</h3>
                    <el-form-item prop="pass">
                        <el-input type="password" v-model="ruleForm.password" autocomplete="off" show-password style=""
                            placeholder="密码"></el-input>
                    </el-form-item>
                    <el-form-item prop="checkPass">
                        <el-input type="password" v-model="ruleForm.confirm_password" autocomplete="off" show-password
                            style="" placeholder="确认密码"></el-input>
                    </el-form-item>
                    <h3>手机号</h3>
                    <el-form-item prop="phone">
                        <el-input type="text" v-model="this.ruleForm.telephone"></el-input>
                    </el-form-item>
                    <h3>邮箱</h3>
                    <el-form-item prop="e_mail">
                        <el-input type="text" v-model="this.ruleForm.e_mail"></el-input>
                    </el-form-item>
                </el-form>

            </div>
            <div class="button">

                <!-- button - 提交修改或取消修改 -->
                <el-button style="width:100px" type="primary" plain @click="submitForm('ruleForm')">修改</el-button>
                <el-button style="width:100px" type="primary" plain v-on:click="cancel">取消</el-button>

            </div>
        </el-main>
    </el-container>
</template>

<script>
export default {
    name: 'SelfView',
    components: {
    },
    data() {
        //输入信息格式判断
        var validatePass = (rule, value, callback) => {
            var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
            if (this.ruleForm.password === '' || this.ruleForm.password === null) {
                callback(new Error('请输入密码'));
            } else if (!reg.test(this.ruleForm.password)) {
                callback([new Error('密码格式不正确')]);
            }
            else {
                if (this.ruleForm.confirm_password !== '') {
                    this.$refs.ruleForm.validateField('checkPass');
                }
                callback();
            }
        }
        var validatePass2 = (rule, value, callback) => {
            if (this.ruleForm.confirm_password === '') {
                callback(new Error('请再次输入密码'));
            } else if (this.ruleForm.confirm_password != this.ruleForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        }
        var checkUser_name = (rule, value, callback) => {
            if (this.ruleForm.user_name === '') {
                callback(new Error('请输入用户名'));
            } else {
                callback();
            }
        }
        var checkTelephone = (rule, value, callback) => {
            var reg = /[0-9]{11}$/;
            if (this.ruleForm.telephone === '') {
                callback(new Error('请输入手机号'));
            } else if (!reg.test(this.ruleForm.telephone)) {
                callback([new Error('手机号格式不正确')]);
            } else {
                callback();
            }
        }
        var checkE_mail = (rule, value, callback) => {
            var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if (this.ruleForm.e_mail === '') {
                callback(new Error('请输入邮箱'));
            } else if (!reg.test(this.ruleForm.e_mail)) {
                callback([new Error('邮箱格式不正确')]);
            } else {
                callback();
            }
        }

        return {
            //从前面页面传递过来的参数
            list: [],
            user_id: "",
            identity: "",
            activeItemFir: '',
            activeItemSec: '',
            ruleForm: {
                user_name: "",
                password: "",
                confirm_password: "",
                telephone: "",
                e_mail: ""
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
                ]
            }
        }

    },
    created() {
        this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
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
            this.user_id = item.data.user_id;
            this.identity = item.data.identity;
            this.ruleForm.telephone = item.data.telephone;
            this.ruleForm.e_mail = item.data.e_mail;
            this.ruleForm.user_name = item.data.user_name;
            this.ruleForm.password = item.data.password;
            this.ruleForm.confirm_password = item.data.password;
        },
        //取消修改 跳转个人中心
        cancel() {
            this.$router.replace({ path: '/info/' + this.identity })
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
        },

        //提交表单的判断
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$http
                        .post("/" + this.list.identity + this.list.user_id + "/update", {
                            user_name: this.ruleForm.user_name,
                            password: this.ruleForm.password,
                            confirm_password: this.ruleForm.confirm_password,
                            telephone: this.ruleForm.telephone,
                            e_mail: this.ruleForm.e_mail
                        })
                        .then(successResponse => {
                            if (successResponse.data.code === 200) {
                                this.$router.replace({ path: '/info/' + this.identity })
                            }
                            else if (successResponse.data.code === 500) {
                                console.log('FAIL CODE 500')
                                this.$router.replace({ path: '/info/' + this.identity })
                            }
                        })
                        .catch(failResponse => {
                        })
                    //页面传参 更新的数据
                    this.$router.push({
                        name: 'info',
                        query: {
                            user_name: this.ruleForm.user_name,
                            telephone: this.ruleForm.telephone,
                            e_mail: this.ruleForm.e_mail
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        }
    }
}
</script>

<style>
.button {
    display: flex;
    margin: 25px;
}

.text {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 25px;
    width: 60%;
}

.navBackgrond {
    background: url("../../assets/navBackground.png") no-repeat;
    background-position: center;
    background-size: 100% 100%;
}
</style>