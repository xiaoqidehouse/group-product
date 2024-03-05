<template>
  <div>

<!--    form表单-->
    <el-container>
      <el-main>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">

          <el-form-item label="加工人员">
            <el-input v-model="formInline.processRecord"></el-input>
          </el-form-item>

          <el-form-item label="农作物" :label-width="formLabelWidth" >
            <el-select v-model="formInline.cropId" placeholder="选择农产品" clearable>
              <el-option v-for="c in cropList" :key="c.id" :label="c.typeName" :value="c.id"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="success" @click="onSubmit" >🧐查询🧐</el-button>
            <!-- 未加工的弹出框 -->
            <el-button @click="noout()" type="primary">待处理</el-button>
            <el-button @click="goexcel()" type="success">数据导出</el-button>
            <el-button @click="resetForm('formInline')" type="warning">重置</el-button>
          </el-form-item>
        </el-form>

      </el-main>
    </el-container>

<!--   查询结果的表格-->
    <template>
      <el-table
        :data="tableData"
        style="width: 100%">

        <el-table-column
          prop="processImg"
          label="实时图片"
          width="180">
          <template v-slot="scope">
            <img :src="scope.row.processImg" style="width: 77px;height: 77px">
          </template>
        </el-table-column>

        <el-table-column
          prop="processTime"
          label="加工时间"
          width="180">
        </el-table-column>

        <el-table-column
          prop="processRecord"
          label="记录人员名称">
        </el-table-column>

        <el-table-column
          prop="typeName"
          label="加工商品名称">
        </el-table-column>

        <el-table-column
          prop="processLink"
          label="加工环节">
        </el-table-column>

        <el-table-column
          prop="processTest"
          label="加工介绍">
        </el-table-column>

        <el-table-column label="状态">
          <template v-slot="scope">
           <span v-if="scope.row.processStatus==0">未加工</span>
           <span v-if="scope.row.processStatus==1">加工完成</span>
          </template>cd
        </el-table-column>

        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row.id)">详情</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row.id)">删除</el-button>
            <el-button size="mini" v-if="scope.row.processStatus==1" @click="senddriver()" type="text">联系司机取货</el-button>
          </template>
        </el-table-column>

      </el-table>
    </template>

<!--未加工的弹出框-->
    <el-dialog title="未加工信息" :visible.sync="nodialogFormVisible">
      <el-icon-plus></el-icon-plus>
        <template>
          <el-table
            :data="nodata"
            style="width: 100%">
            <el-table-column
              prop="processImg"
              label="图片"
              width="180">
              <template v-slot="scope">
                <img :src="scope.row.processImg" style="width: 77px;height: 77px">
              </template>
            </el-table-column>

            <el-table-column
              prop="processTime"
              label="加工时间"
              width="180">
            </el-table-column>

            <el-table-column
              prop="processRecord"
              label="记录人员名称">
            </el-table-column>

            <el-table-column
              prop="typeName"
              label="加工商品名称">
            </el-table-column>

            <el-table-column
              prop="processLink"
              label="加工环节">
            </el-table-column>

            <el-table-column
              prop="processTest"
              label="加工介绍">
            </el-table-column>

            <el-table-column label="状态">
              <template v-slot="scope">
                <span v-if="scope.row.processStatus==0">未加工</span>
                <span v-if="scope.row.processStatus==1">加工完成</span>
              </template>cd
            </el-table-column>

            <el-table-column label="操作">
              <template v-slot="scope">
                <el-button
                  size="mini"
                  type="primary"
                  @click="handleEdit(scope.row.id)">详情</el-button>
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.row.id)">删除</el-button>
                <el-button
                  v-if="scope.row.processStatus==0"
                  size="mini"
                  type="success"
                  @click="profinish(scope.row.id)">一键加工完成</el-button>
              </template>
            </el-table-column>

          </el-table>
        </template>

      <div slot="footer" class="dialog-footer">
        <el-button @click="nodialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加界面 -->
    <el-button type="text" @click="savedia()">添加新的加工产品</el-button>
    <el-dialog title="添加产品" :visible.sync="dialogFormVisible">
      <el-form :model="saveForm">

        <div class="block">
          <h3><span class="demonstration">加工时间选择:</span></h3>
          <el-date-picker
            v-model="saveForm.processTime"
            type="date"
            :value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </div>

        <el-form-item label="工作人员名称" :label-width="formLabelWidth">
          <el-input v-model="saveForm.processRecord" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="农作物" :label-width="formLabelWidth">
          <el-select v-model="saveForm.cropId" placeholder="请选择活动区域">
            <el-option v-for="c in cropList" :key="c.id" :label="c.typeName" :value="c.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="加工环节" :label-width="formLabelWidth">
          <el-input v-model="saveForm.processLink" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="加工事项" :label-width="formLabelWidth">
          <el-input v-model="saveForm.processTest" autocomplete="off"></el-input>
        </el-form-item>

        <template>加工状态:
          <el-radio v-model="saveForm.processStatus" label="1">加工完成</el-radio>
          <el-radio v-model="saveForm.processStatus" label="0">未加工</el-radio>
        </template>

          <el-upload
            class="avatar-uploader"
            action="http://localhost:9777/process/picload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="openmes()">确 定</el-button>
      </div>
    </el-dialog>

    <!--修改界面-->
    <el-dialog title="详细数据" :visible.sync="updialogFormVisible" >
      <el-form :model="updateFrom" style="width: 277px;height: 277px">

        <div class="block">
          <h3><span class="demonstration">加工时间选择:</span></h3>
          <el-date-picker
            v-model="updateFrom.processTime"
            type="date"
            :value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </div>

        <el-form-item label="工作人员名称" :label-width="formLabelWidth">
          <el-input v-model="updateFrom.processRecord" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="农作物" :label-width="formLabelWidth">
          <el-select v-model="updateFrom.cropId" placeholder="请选择活动区域">
            <el-option v-for="c in cropList" :key="c.id" :label="c.typeName" :value="c.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="加工环节" :label-width="formLabelWidth">
          <el-input v-model="updateFrom.processLink" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="加工事项" :label-width="formLabelWidth">
          <el-input v-model="updateFrom.processTest" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="农作物实时图片" :label-width="formLabelWidth">
          <el-table-column
            prop="processImg"
            width="180">
              <img :src="updateFrom.processImg" style="width: 77px;height: 77px">
          </el-table-column>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateprocess()">确 定</el-button>
      </div>
    </el-dialog>



  </div>
</template>
<script>
import {
  croptype,
  delprocess,
  listall,
  nolist,
  proupByid,
  saveprocess,
  updatepro,
  updateprocess
} from "@/api/process/process";

export default {
  data(){
    return{
      tableData:{},
      saveForm:{},
      dialogFormVisible:false,
      cropList:{},
      imageUrl:'',
      formInline:{},
      updialogFormVisible:false,
      updateFrom:{},
      nodata:{},
      nodialogFormVisible:false,
      formLabelWidth: '120px'
    }
  },
  methods:{
    // 一键发货
    senddriver(){
      alert("消息已发送,预计5分钟内送达,注意查收")
      window.location.href='http://localhost:8080/pro/process/sendpro'
    },
    // 表单导出
    goexcel(){
      window.open("http://localhost:8080/pro/process/goexcel")
    },
    // 根据id修改加工的状态
    profinish(id){
      proupByid(id).then(r => {
        this.onSubmit();
        this.nodialogFormVisible=false;
      })
    },
    // 弹出框打开
    noout(){
      this.nodialogFormVisible=true;
      this.nosum();
    },
    // 未加工的数据统计
    nosum(){
      nolist().then(r => {
        this.nodata = r.data;
      });
    },
    //删除
    handleDelete(id){
      delprocess(id).then(r => {
        alert(r.msg)
        this.onSubmit();
      })
    },
    //minio的照片要求
    beforeAvatarUpload(file) {

      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    },
    //修改
    updateprocess(){
      updatepro(this.updateFrom).then(r => {

       if (r.code === 200){

         alert(r.$msg);
         this.updialogFormVisible=false;
         this.onSubmit();

       }else {

         alert("修改失败")

       }

      });
    },
    //修改回显
    handleEdit(id){
      updateprocess(id).then(r => {
        this.updialogFormVisible=true;
        this.updateFrom=r.data;
      })
    },
    //图片上传
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.saveForm.processImg= res.data;
    },
    //查询
    onSubmit() {
      listall(this.formInline).then(r => {

        this.tableData = r.data;

      });
    },
    //添加弹出框
    savedia() {

        this.dialogFormVisible=true;

    },
    //下拉框
    cropTypelist(){

      croptype().then(r => {

        this.cropList = r.data;

      });
    },
    //添加
    openmes(){
      saveprocess(this.saveForm).then(r => {

        if (r.code == 200){
          this.$message.success(r.msg);
          this.dialogFormVisible=false;
          this.saveForm={};
          this.onSubmit();
        }else{
            this.$message.error(r.msg);
          }

      });
    },
    //重置
    resetForm(formInline) {
      this.formInline={}
      this.onSubmit()
    }

  },
  created() {

    this.onSubmit();
    this.cropTypelist();

  }
}
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.el-main {
  background-color: #6dd4dc;
  color: #b45050;
  text-align: center;
  line-height: 17px;
}
</style>
