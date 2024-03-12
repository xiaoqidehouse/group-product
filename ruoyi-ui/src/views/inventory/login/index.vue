

<template>
    <div style="margin-left: 350px">
      <h1 style="font-size: 33px">================</h1><br>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">

        <el-form-item label="账号">
          <el-input v-model="formInline.name"></el-input>
        </el-form-item><br>

        <el-form-item label="密码">
          <el-input v-model="formInline.pwd"></el-input>
        </el-form-item><br>

        <el-form-item>
          <el-button type="primary" @click="onSubmit">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>
<script>
import {loginfrom} from "@/api/inventory/inventory";

export  default {

  data(){
    return {
      formInline:{}
    }
  },
  methods:{
    onSubmit(){
      loginfrom(this.formInline).then(r=>{

        if (r.code==200){

          localStorage.setItem("roleName",r.data.roleName);
          this.$router.push("/inventory/work")

        }else {
          this.$message.error(r.msg)
        }

      })
    },
  },
  created() {
  }

}
</script>
<style scoped lang="scss">

</style>
