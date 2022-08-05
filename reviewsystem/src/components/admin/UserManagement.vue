<!-- template - 用户管理组件 -->

<template>
  <el-container>
    <el-header style="margin-bottom: 5px;height: 15vh;">

      <!-- div - 信息提示部分 -->
      <div style="display: flex;color:gray;">
        <span style="width:45px; margin:5px;"></span>
        <span style="width:90px; margin:5px;">账号</span>
        <span style="width:110px; margin:5px;">用户名</span>
        <span style="width:110px; margin:5px;">电话</span>
        <span style="width:170px; margin:5px;">邮箱</span>
        <span style="width:90px; margin:5px;">身份</span>
      </div>

      <!-- div - 筛选用户信息 -->
      <div style="display: flex;color:gray;">
        <span style="width:35px;margin:5px;padding: 5px;">筛选</span>
        <span style="width:100px; margin:5px;">
          <el-input v-model="inputUserId" placeholder="请输入内容"></el-input>
        </span>
        <span style="width:110px; margin:5px;">
          <el-input v-model="inputUserName" placeholder="请输入内容"></el-input>
        </span>
        <span style="width:110px; margin:5px;">
          <el-input v-model="inputTelephone" placeholder="请输入内容"></el-input>
        </span>
        <span style="width:170px; margin:5px;">
          <el-input v-model="inputEmail" placeholder="请输入内容"></el-input>
        </span>
        <span style="width:90px; margin:5px;">
          <el-select v-model="identitySelect" placeholder="请选择">
            <el-option v-for="item in filterIdentityOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </span>
        <span style=" margin:5px;margin-left: 10px;">
          <el-button @click="filterUser">筛选</el-button>
        </span>
      </div>

      <!-- div - 新增用户 -->
      <div style="display: flex;color:gray;">
        <span style="width:35px;margin:5px;padding: 5px;">新增</span>
        <el-form :inline="true" status-icon :rules="rules" :model="ruleForm" ref="ruleForm" label-width="0">
          <el-form-item style="width:100px;margin: 5px;">
            <el-input :disabled="true"></el-input>
          </el-form-item>
          <el-form-item style=" width:110px;margin: 5px;">
            <el-input v-model="ruleForm.user_name" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item style="width:110px;margin: 5px;">
            <el-input v-model="ruleForm.telephone" placeholder="请输入内容"></el-input>
          </el-form-item >
          <el-form-item style="width:170px;margin: 5px;">
            <el-input v-model="ruleForm.e_mail" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item style="width:90px;margin: 5px;">
            <el-select :disabled="true" v-model="selectNewIdentity" placeholder="请选择">
              <el-option v-for="item in addIdentityOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span style=" margin:5px;margin-left: 10px;">
          <el-button @click="addUser">新增</el-button>
        </span>
      </div>

    </el-header>
    <el-main>

      <!-- table - 显示所有的用户 -->
      <el-table :data="userInform">
        <el-table-column prop="user_id" label="账号" width="120" sortable>
        </el-table-column>
        <el-table-column prop="user_name" label="用户名" width="120">
        </el-table-column>
        <el-table-column prop="telephone" label="电话" width="120">
        </el-table-column>
        <el-table-column prop="e_mail" label="邮箱" width="180">
        </el-table-column>
        <el-table-column prop="identity" label="身份" width="100" sortable>
        </el-table-column>
        <el-table-column fixed="right" width="80">
          <template v-slot="scope">
            <el-button type="primary" plain @click="alterUser(scope.row)" style="margin:5px">修改</el-button>
          </template>
        </el-table-column>
        <el-table-column fixed="right" width="80">
          <template v-slot="scope">
            <el-button type="danger" @click="deleteUser(scope.row)" style="margin:5px">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- pagination - 显示分页信息 -->
      <el-pagination class="page" layout="total,prev, pager, next, jumper" :current-page="currentPage" :page-size="10"
                     :total="allUserInform.length" @current-change="currentChange"
                     style=" margin-left:70px;margin-top: 10px;"/>

    </el-main>
  </el-container>

  <!-- dialog - 修改用户信息 -->
  <el-dialog v-model="alterUserClick" title="修改用户信息" width="30vw">
    <AlterUser :user_infor="user_infor" @Close="Close"/>
  </el-dialog>

</template>


<script>

import AlterUser from '@/components/dialog/AlterUser.vue';
import {ElMessage, ElMessageBox} from 'element-plus';

export default {
  name: 'UserManagement',
  components: {
    AlterUser
  },
  data() {
    return {
      list: [],
      alterUserClick: false,
      deleteUserClick: false,
      addIdentityOptions: [],
      filterIdentityOptions: [
        {
          value: 'Student',
          label: '学生'
        },
        {
          value: 'Teacher',
          label: '导师'
        }
      ],
      userInform: [],
      user_infor: [],
      ruleForm: {
        user_name: "",
        telephone: "",
        e_mail: ""
      },
      identitySelect: '',
      inputUserId: '',
      inputUserName: '',
      inputEmail: '',
      inputTelephone: '',
      selectNewIdentity: '导师',
      currentPage: 1,
      allUserInform: [],
    }
  },

  created() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
    this.getData();
  },
  //页面数据更新后 重新获取数据
  beforeUpdate() {
    this.list = JSON.parse(sessionStorage.getItem("userInfo")); //会话存储 获取该用户信息
    this.getData();
  },
  methods: {
    //获取所有用户信息
    getData() {
      this.$http.get("/" + this.list.identity + this.list.user_id + "/")
          .then(successResponse => {
            console.log(successResponse.data)
            if (successResponse.data.code === 200) {
              this.setData(successResponse.data);
            } else if (successResponse.data.code === 500) {
              console.log("FAIL CODE 500")
            }
          })
          .catch(failResponse => {
          })
    },
    //展示所有用户
    setData(item) {
      this.allUserInform = []
      for (let i = 0; i < item.data.length; i++) {
        let inform = {};
        inform.user_id = item.data[i].user_id;
        inform.user_name = item.data[i].user_name;
        inform.telephone = item.data[i].telephone;
        inform.e_mail = item.data[i].e_mail;
        inform.identity = item.data[i].identity;
        this.allUserInform.push(inform);
      }
      this.pageValuePass()
    },
    //用户分页展示
    pageValuePass() {
      this.userInform = []
      let pageSize = (this.allUserInform.length > this.currentPage * 10) ? 10 : (this.allUserInform.length - (this.currentPage - 1) * 10)
      for (let i = 0; i < pageSize; i++)
        this.userInform.push(this.allUserInform[(this.currentPage - 1) * 10 + i])
    },
    //当前页的用户展示
    currentChange(item) {
      this.currentPage = item
      this.pageValuePass()
    },
    //关闭弹窗
    Close() {
      this.alterUserClick = false;
      setTimeout(() => {
        this.$router.go(0)
      }, 500)
    },
    //增加用户
    addUser() {
      //未全部填写则弹窗提示警告不予通过
      if (this.ruleForm.user_name === '' || this.ruleForm.telephone === '' || this.ruleForm.e_mail === '') {
        this.$message.error('请填写完整后再提交!');
        return;
      }
      else if(this.check_Telephone(this.ruleForm.telephone)||this.check_Email(this.ruleForm.e_mail)){
        this.$message.error('格式错误!');
      }
      else {
        this.commitLoading = true
        this.$http
          .post("/" + this.list.identity + this.list.user_id + "/addUser", {
            user_name: this.ruleForm.user_name,
            e_mail: this.ruleForm.e_mail,
            telephone: this.ruleForm.telephone,
            identity: this.selectNewIdentity
          })
          .then(successResponse => {
            this.commitLoading = false
            if (successResponse.data.code === 200) {
              // 成功返回
              this.$message({
                message: '创建成功',
                type: 'success'
              });
              //清空所有数据
              setTimeout(() => {
                this.$router.go(0);
              }, 500)
              //刷新
            } else if (successResponse.data.code === 500) {
              // meg数据异常 || bean验证错误 || 异常抛出信息
              this.$message.error(successResponse.data.code, " Something not samrt happened.");

            }
          })
          .catch(failResponse => {
            this.commitLoading = false
            this.$message.error(failResponse.data.code, " Something not samrt happened.");
          })
      }
    },
    //确认新增电话格式
    check_Telephone(item){
      var reg = /[0-9]{11}$/;
      if (!reg.test(item)) {
        return true;
      } else {
        return false;
      }
    },
    //确认新增邮箱格式
    check_Email(item){
      var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (!reg.test(item)) {
        return true;
      } else {
        return false;
      }
    },
    //筛选用户
    filterUser() {
      this.$http
          .post("/" + this.list.identity + this.list.user_id + "/filterUser", {
            user_id: this.inputUserId,
            user_name: this.inputUserName,
            telephone: this.inputTelephone,
            e_mail: this.inputEmail,
            identity: this.identitySelect
          })
          .then(successResponse => {
            if (successResponse.data.code === 200) {
              this.inputUserId = '';
              this.inputUserName = '';
              this.inputTelephone = '';
              this.inputEmail = '';
              this.identitySelect = '';
              this.setData(successResponse.data);
              this.$message({
                message: '筛选成功',
                type: 'success'
              });
            } else if (successResponse.data.code === 500) {
              console.log('FAIL CODE 500')
            }
          })
          .catch(failResponse => {
          })
    },
    //修改用户
    alterUser(item) {
      this.user_infor = item;
      this.alterUserClick = true;
    },
    //删除用户
    deleteUser(item) {
      ElMessageBox.confirm(
          '确定删除该用户吗',
          'Warning',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            if (item.identity == '学生')
              item.identity = 'Student';
            else if (item.identity == '老师')
              item.identity = 'Teacher';
            this.$http
                .post("/" + this.list.identity + this.list.user_id + "/deleteUser", {
                  user_id: item.user_id,
                  identity: item.identity
                })
                .then(successResponse => {
                  if (successResponse.data.code === 200) {
                    ElMessage({
                      type: 'success',
                      message: '删除成功',
                    })
                    setTimeout(() => {
                      this.$router.go(0);
                    }, 500)
                  } else if (successResponse.data.code === 500) {
                    console.log('FAIL CODE 500')
                  }
                })
                .catch(failResponse => {
                })

          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消删除',
            })
          })
    }
  }
}
</script>