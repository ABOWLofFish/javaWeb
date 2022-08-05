
<!-- template - 修改用户信息组件 -->

<template>
    <div style="display: flex;flex-direction:column;justify-items: center;align-items:center;">

        <!-- form - 修改用户信息 -->
        <el-form status-icon :rules="rules" :model="ruleForm" ref="ruleForm" :label-position="left" :label-width="60"
            style="width:16vw;">
            <el-form-item label="账号">
                <el-input type="text" :disabled="true" v-model="user_infor.user_id"></el-input>
            </el-form-item>
            <el-form-item label="用户名">
                <el-input type="text" v-model="user_infor.user_name"></el-input>
            </el-form-item>
            <el-form-item prop="phone" label="手机号">
                <el-input type="text" v-model="ruleForm.telephone"></el-input>
            </el-form-item>
            <el-form-item prop="e_mail" label="邮箱">
                <el-input type="text" v-model="ruleForm.e_mail"></el-input>
            </el-form-item>
            <el-form-item prop="identity" label="身份">
                <el-select :disabled="true" v-model="ruleForm.identity" placeholder="请选择">
                    <el-option v-for="item in identityOptions" :key="item.value" :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>

        <div class="button">
            
            <!-- button - 提交修改和取消修改 -->
            <el-button style="width:100px" type="primary" plain @click="submitForm('ruleForm')">修改</el-button>
            <el-button style="width:100px" type="primary" plain v-on:click="cancel">取消</el-button>

        </div>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus'

export default {
    name: 'SelfView',
    props: {
        user_infor: Object,
    },
    beforeUpdate() {
        this.ruleForm.telephone = this.user_infor.telephone;
        this.ruleForm.e_mail = this.user_infor.e_mail;
        this.ruleForm.identity = this.user_infor.identity;
    },
    components: {
    },
    data() {
        var checkTelephone = (rule, value, callback) => {
            var reg = /[0-9]{11}$/;
            if (this.ruleForm.telephone === '') {
                callback(new Error('请输入手机号'));
            } else if (!reg.test(this.ruleForm.telephone)) {
                callback([new Error('格式不正确')]);
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
            identityOptions: [
                {
                    value: 'Student',
                    label: '学生'
                },
                {
                    value: 'Teacher',
                    label: '老师'
                },
                {
                    value: 'Admin',
                    label: '管理员'
                }
            ],
            list: [],
            activeItemFir: '',
            activeItemSec: '',
            ruleForm: {
                telephone: '',
                e_mail: '',
                identity: ''
            },
            rules: {
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
        this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
        //初始化页面数据
        this.ruleForm.telephone = this.user_infor.telephone;
        this.ruleForm.e_mail = this.user_infor.e_mail;
        this.ruleForm.identity = this.user_infor.identity;
    },
    methods: {
        //取消修改
        cancel() {
            ElMessage({
                showClose: true,
                message: '取消修改',
            })
            this.$emit("Close")
        },
        //提交表单的判断
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    let passIdentity = ''
                    switch (this.ruleForm.identity) {
                        case '学生':
                            passIdentity = 'Student';
                            break;
                        case '老师':
                            passIdentity = 'Teacher';
                            break;
                    }
                    this.$http
                        .post("/" + this.list.identity + this.list.user_id + "/alterUser", {
                            user_id: this.user_infor.user_id,
                            user_name: this.user_infor.user_name,
                            telephone: this.ruleForm.telephone,
                            e_mail: this.ruleForm.e_mail,
                            identity: passIdentity
                        })
                        .then(successResponse => {
                            if (successResponse.data.code === 200) {
                                ElMessage({
                                    showClose: true,
                                    message: '修改成功',
                                    type: 'success',
                                })
                                this.$emit("Close")
                            }
                            else if (successResponse.data.code === 500) {
                                ElMessage({
                                    showClose: true,
                                    message: '修改失败',
                                    type: 'error',
                                })
                            }
                        })
                        .catch(failResponse => {
                        })
                }
                else {
                    return false;
                }
            });
        }
    }
}
</script>

<style scoped>
.button {
    display: flex;
    margin: 25px;
}
</style>