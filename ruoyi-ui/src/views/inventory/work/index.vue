

<template>
  <div>

    <el-table-column style="margin-left: 555px">

      <span v-if="roleName!=null"><br>当前登录用户为:<h1 style="font-size: 37px">{{roleName}}</h1></span>
      <el-button @click="clearroleName()" type="danger">退出登录</el-button>
      <el-button v-if="roleName=='wangwu'" @click="historypro()" type="primary">查看历史记录</el-button>

    </el-table-column>



      <el-table
        :data="workData"
        style="width: 100%">

        <el-table-column
          prop="task.id"
          label="流程id"
          width="180">
        </el-table-column>

        <el-table-column
          prop="name"
          label="出险人姓名"
          width="180">
        </el-table-column>

        <el-table-column
          prop="num"
          label="证件号码"
          width="180">
        </el-table-column>

        <el-table-column
          prop="sex"
          label="性别"
          width="180">
        </el-table-column>

        <el-table-column
          prop="tel"
          label="电话"
          width="180">
        </el-table-column>

        <el-table-column
          prop="cxdate"
          label="出险时间"
          width="180">
        </el-table-column>

        <el-table-column
          prop="address"
          label="出险地址"
          width="180">
        </el-table-column>

        <el-table-column
          prop="task.reason"
          label="出险原因"
          width="180">
        </el-table-column>

        <el-table-column label="操作">

          <template v-slot="scope">

            <el-button v-if="roleName=='zhangsan'" @click="agreeone(scope.row)">同意(部门业务员)</el-button>
            <el-button v-if="roleName=='lisi'" @click="agreetwo(scope.row)">同意(主任)</el-button>
            <el-button v-if="roleName=='wangwu'" @click="agreethree(scope.row)">同意(财务总监)</el-button>

          </template>

        </el-table-column>
      </el-table>



    <el-dialog title="查看历史流程" :visible.sync="finddialogFormVisible">

      <template>
        <el-table
          :data="queryData"
          style="width: 100%">
          <el-table-column
            prop="id"
            label="流程id"
            width="180">
          </el-table-column>
          <el-table-column
            prop="processInstanceId"
            label="流程实例ID"
            width="180">
          </el-table-column>
          <el-table-column
            prop="assignee"
            label="审批人"
            width="180">
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            width="180">
          </el-table-column>
          <el-table-column
            prop="endTime"
            label="结束时间"
            width="180">
          </el-table-column>
        </el-table>
      </template>


      <div slot="footer" class="dialog-footer">
        <el-button @click="finddialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-button type="text" @click="addDialogFormVisible()">☞请假入口</el-button>

    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">

      <el-form :model="saveform" :rules="rules" ref="saveform">

        <el-form-item label="名称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="saveform.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="身份证" :label-width="formLabelWidth" prop="num">
          <el-input v-model="saveform.num" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="性别" :label-width="formLabelWidth" prop="num">
          <template>
            <el-radio v-model="saveform.sex" label="男">男</el-radio>
            <el-radio v-model="saveform.sex" label="女">女</el-radio>
          </template>
        </el-form-item>

        <el-form-item label="请假时间" :label-width="formLabelWidth">
          <template>
            <div class="block">
              <el-date-picker
                v-model="saveform.cxdate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="出险时间">
              </el-date-picker>
            </div>
          </template>
        </el-form-item>

        <el-form-item label="电话" :label-width="formLabelWidth" prop="tel">
          <el-input v-model="saveform.tel" autocomplete="off"></el-input>
        </el-form-item>


        <el-form-item label="地址" :label-width="formLabelWidth" prop="address">
          <el-input v-model="saveform.address" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="原因" :label-width="formLabelWidth" prop="reason">
          <el-input v-model="saveform.reason" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savemes('savemes')">确 定</el-button>
      </div>

    </el-dialog>

  </div>
</template>
<script>
import {queryhis, saveprocess, worklist} from "@/api/inventory/inventory";

export default {

  data(){
    return {
      workData:{},
      roleName:localStorage.getItem("roleName"),
      saveform:{},
      dialogFormVisible:false,
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个汉字', trigger: 'blur' }
        ],
        num: [
          { required: true, message: '请输入正确的身份证号码', trigger: 'blur' },
          { min: 1,max: 18, message: '长度在18 个字符', trigger: 'blur' }
        ],
        tel: [
          { required: true, message: '请输入电话号码', trigger: 'blur' },
          { min: 1,max:11, message: '长度在11个字符', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入出险人地址', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        reason: [
          { required: true, message: '请输入请假原因', trigger: 'blur' },
          { min: 0, max: 5, message: '最长5个字符', trigger: 'blur' }
        ],
      },
      formLabelWidth:'150px',
      queryData:{},
      finddialogFormVisible:false

    }
  },
  methods:{
    historypro(){
      queryhis().then(r=>{

        this.finddialogFormVisible=true;
        this.queryData=r.data;

      })
    },
    clearroleName(){
      localStorage.clear();
      window.location.href='http://localhost/inventory/login'
    },
    agreethree(row){
      window.location.href='http://localhost:8080/act/act/agreethree?taskId='+row.task.id;
    },
    agreetwo(row){
      window.location.href='http://localhost:8080/act/act/agreetwo?taskId='+row.task.id+"&to=10001";
    },
    agreeone(row){
      window.location.href='http://localhost:8080/act/act/agreeone?taskId='+row.task.id;
    },
    addDialogFormVisible(){
      this.dialogFormVisible=true
    },
//根据用户查询审批列表
    listall(){
      worklist(this.roleName).then(r=>{

        this.workData=r;
        console.log("数据" + r)

      })
    },
    savemes(){
      saveprocess(this.saveform).then(()=>{

        this.$message.success("申请成功,请等待结果")
        this.dialogFormVisible=false;
        this.listall();

      })
    }

  },
  created() {

    this.listall();

  }

}
</script>
<style scoped lang="scss">

</style>
