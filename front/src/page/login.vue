<template>
  <div class="login-box">
    <div class="login-page-container">
      <el-form
        ref="ruleForm2"
        :model="ruleForm2"
        :rules="rules2"
        label-position="left"
        label-width="0px"
        class="demo-ruleForm login-container"
      >
        <h3 class="title">系统登录</h3>
        <el-form-item prop="account">
          <el-input v-model="ruleForm2.account" type="text" placeholder="随便输"/>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input v-model="ruleForm2.checkPass" type="password" placeholder="随便输"/>
        </el-form-item>
        <el-checkbox v-model="checked" click="remberuser" checked class="remember">记住密码</el-checkbox>
        <el-form-item style="width:100%;">
          <el-button :loading="logining" type="primary" style="width:100%;" @click="login">登录</el-button>

        </el-form-item>
        <el-form-item style="width:100%;">
          <el-button type="primary" style="width:100%;" @click="signin">注册</el-button>
          <!--router-link to="/register">register</router-link-->
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
// import * as commonApi from 'api/common'
import * as types from '../store/mutation-types'
export default {
  props: {},
  data() {
    return {
      logining: false,
      ruleForm2: {
        account: '',
        checkPass: ''
      },
      rules2: {
        account: [
          {
            required: true,
            message: '请输入登录账号',
            trigger: 'blur'
          }
        ],
        checkPass: [
          {
            required: true,
            message: '请输入登录密码',
            trigger: 'blur'
          }
        ]
      },
      checked: true
    }
  },
  created() {
    this.ruleForm2.checkPass = ''
    if (localStorage.getItem('userName')) { // 记住密码操作
      this.ruleForm2.account = localStorage.getItem('userName')
      this.ruleForm2.checkPass = localStorage.getItem('password')
    }
  },
  methods: {
    signin(){
      const params = {
        userName: 'regholder',
        password: 'regholder'
      }
      sessionStorage.setItem('user',JSON.stringify(params))
      this.$router.push({path : '/register' })
    },
    login() {
      this.$refs.ruleForm2.validate(valid => {
        if (valid) {
          this.logining = true
          // 模拟登录
          setTimeout(() => {
            const params = {
              userName: this.ruleForm2.account,
              password: this.ruleForm2.checkPass
            }
            this.$axios(
              {
                method:'post',
                url:this.$global.baseUrl+'/admin/login',
                params: {
                  username:params.userName,
                  password:params.password
                }
              }
            ).then((response)=>{
              console.log(response);
              if(response.data.toString()!=="登陆成功"){
                this.$message(response.data.toString())
                this.logining = false
                //this.sucess = false;
              }else {
                this.$message("成功登陆!")
                //this.sucess = true;
                sessionStorage.setItem('user', JSON.stringify(params)) // session存储用户信息
                this.logining = false
                this.$router.push({ path: '/menu1/sub1' }) // 去主页
                //this.$router.push({path:'/login'})
              }
            }).catch((error)=>console.log(error))

          }, 1000)
          // const params = {
          //   userName: this.ruleForm2.account,
          //   password: this.ruleForm2.checkPass
          // };
          // commonApi.loginUserNo(params).then(res => {
          //     let { data } = res;
          //     this.logining = false;
          //     if (data.success === true) {
          //       this.$router.push({ path: "/declare/ordermanage" });  // 去主页
          //       this.setTreeData(data.data); // 状态存储菜单节点
          //       this.setToken(data.value); // 状态存储token
          //       sessionStorage.setItem("user", JSON.stringify(params)); // session存储用户信息
          //       sessionStorage.setItem("token", data.value); // session存储token
          //       // 记住密码操作
          //       if (this.checked) {
          //         localStorage.setItem('userName', params.userName)
          //         localStorage.setItem('password', params.password)
          //       } else {
          //         localStorage.clear();
          //       }
          //     } else {
          //       this.$message.error(data.message);
          //     }
          //   })
          //   .catch(() => {
          //     this.$message.error("对不起,连接服务器异常,请稍后再试!");
          //   });
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    ...mapMutations({
      setUsername: types.SET_USERNAME,
      setPassword: types.SET_PASSWORD,
      setTreeData: types.SET_TREEDTA,
      setToken: types.SET_TOKEN
    })
  }
}
</script>

<style lang="scss">
@import "../assets/css/them.scss";
.login-box {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  background-color: $globalBgColor;
}
.login-container {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  -moz-border-radius: 5px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

label.el-checkbox.remember {
  margin: 0px 0px 35px 0px;
}
</style>
