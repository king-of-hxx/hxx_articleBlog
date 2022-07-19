<template>
  <div id="login" v-title data-title="登录 - 全栈之路">
    <div class="me-login-box me-login-box-radius">
      <h1>Login</h1>
      <el-form ref="userForm" :model="userForm" :rules="rules">
        <el-form-item prop="account">
          <el-input placeholder="userName" v-model="userForm.account"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="password" type="password" v-model="userForm.password" @keyup.enter.native="login('userForm')"></el-input>
        </el-form-item>
        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="login('userForm')">login</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      userForm: {
        account: '',
        password: ''
      },
      rules: {
        account: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { max: 10, message: '不能大于10个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { max: 10, message: '不能大于10个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login(formName) {
      let that = this

      this.$refs[formName].validate((valid) => {
        if (valid) {

          that.$store.dispatch('login', that.userForm).then(() => {
            that.$router.go(-1)
          }).catch((error) => {
            if (error !== 'error') {
              that.$message({ message: error, type: 'error', showClose: true });
            }
          })
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
#login {
  min-width: 100%;
  min-height: 100%;
  width: 100vw;
  height: 100vh;
  background: url("../assets/img/login.jpg") no-repeat;
  background-size: 100%;
}

.me-login-box {
  position: absolute;
  width: 300px;
  height: 260px;
  background-color: white;
  margin-top: 150px;
  margin-left: 280px;
  left: 50%;
  padding: 30px;
}

.me-login-box-radius {
  border-radius: 10px;
  box-shadow: 0px 0px 1px 1px rgba(161, 159, 159, 0.1);
}

.me-login-box h1 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  vertical-align: middle;
}

.me-login-design {
  text-align: center;
  font-family: "Open Sans", sans-serif;
  font-size: 18px;
}

.me-login-design-color {
  color: #5fb878 !important;
}

.me-login-button {
  text-align: center;
}

.me-login-button button {
  width: 100%;
}
</style>
