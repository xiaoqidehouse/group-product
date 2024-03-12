<template xmlns="http://www.w3.org/1999/html">
  <div>

    <br style="font-size: 12px">
    <br style="font-size: 12px">
    <br style="font-size: 12px">
    <br style="font-size: 12px">
    <br style="font-size: 12px">

    <el-form :inline="true" :model="formInline" class="demo-form-inline">

<!--      <el-form-item label="储藏位置">-->

<!--        <el-select v-model="formInline.sId" @change="changeid()" clearable>-->
<!--          <el-option v-for="s in stashList" :key="s.id" :label="s.stashName" :value="s.id"></el-option>-->
<!--        </el-select>-->

<!--        <el-select v-model="formInline.reId">-->
<!--          <el-option v-for="r in remarkList" :key="r.id" :label="r.remark" :value="r.id"></el-option>-->
<!--        </el-select>-->

<!--      </el-form-item>-->


      <el-form-item label="活动区域">
        <el-select v-model="formInline.cId" placeholder="活动区域" clearable>
          <el-option v-for="c in typeList" :key="c.id"  :label="c.typeName" :value="c.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="选择查询">

        <el-button class="el-tag el-tag--info el-tag--medium el-tag--plain"    @click="nouse('0')"> 未操作 </el-button>
        <el-button class="el-tag el-tag--warning el-tag--medium el-tag--plain" @click="nouse('1')">部分完成</el-button>
        <el-button class="el-tag el-tag--success el-tag--medium el-tag--plain"   @click="nouse('2')">全部完成</el-button>
        <el-button class="el-tag el-tag--danger el-tag--medium el-tag--plain"  @click="nouse('3')">已作废</el-button>
        <el-button class="el-tag el-tag--primary el-tag--medium el-tag--plain"   @click="onSubmit">全部查询</el-button>
        <el-button class="el-tag el-tag--danger el-tag--medium el-tag--plain"   @click="userExcel">导出数据(所有)</el-button>

      </el-form-item>

    </el-form>

        <br style="font-size: 12px">
        <br style="font-size: 12px">
        <br style="font-size: 12px">
        <br style="font-size: 12px">
        <br style="font-size: 12px">
<!--    展示的表(包括flag查询的)-->
    <template>
      <el-table
        :data="tableData"
        style="width: 100%">

        <el-table-column
          prop="name"
          label="名称"
          width="180">
        </el-table-column>

        <el-table-column
          prop="stashName"
          label="位置"
          width="180">
        </el-table-column>

        <el-table-column
          prop="remark"
          label="具体位置"
          width="180">
        </el-table-column>

        <el-table-column
          prop="num"
          label="数量"
          width="180">
        </el-table-column>

        <el-table-column label="状态">
          <template v-slot="scope">
            <span v-if="scope.row.flag==0" class="el-tag el-tag--info el-tag--medium el-tag--plain"> 未操作 </span>
            <span v-if="scope.row.flag==1" class="el-tag el-tag--warning el-tag--medium el-tag--plain">部分完成</span>
            <span v-if="scope.row.flag==2" class="el-tag el-tag--success el-tag--medium el-tag--plain">全部完成</span>
            <span v-if="scope.row.flag==3" class="el-tag el-tag--danger el-tag--medium el-tag--plain">已作废</span>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button
              class="el-button el-button--text el-button--mini"
              v-if="scope.row.flag!=3"
              @click="xiang(scope.row)">移库</el-button>
            <el-button
              class="el-button el-button--text el-button--mini"
              v-if="scope.row.flag!=3"
              @click="godelinv(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>

      </el-table>
    </template>
    <!-- 回显+移库 -->
    <el-dialog title="库存转移" :visible.sync="dialogFormVisible">

        <el-form :model="updateform">

          <el-form-item label="编号" :label-width="formLabelWidth">
            <el-input v-model="updateform.id" readonly ></el-input>
          </el-form-item>

          <el-form-item label="名称" :label-width="formLabelWidth">
            <el-input v-model="updateform.name"  readonly></el-input>
          </el-form-item>

          <el-form-item label="大体位置" :label-width="formLabelWidth">
            <el-input v-model="updateform.stashName"  readonly></el-input>
          </el-form-item>

          <el-form-item label="具体位置" :label-width="formLabelWidth">
            <el-input v-model="updateform.remark"  ></el-input>
          </el-form-item>

<!--          <el-form-item label="具体位置" :label-width="formLabelWidth">-->
<!--            <el-input v-if="updateform.flag===0" v-model="未操作"></el-input>-->
<!--            <el-input v-if="updateform.flag===1" v-model="部分操作"></el-input>-->
<!--            <el-input v-if="updateform.flag==2" v-model="未操作"></el-input>-->
<!--            <el-input v-if="updateform.flag==3" v-model="未操作"></el-input>-->
<!--          </el-form-item>-->

          <el-form-item label="选择要移走的数量" :label-width="formLabelWidth">
            <template>
              <el-input-number v-model="updateform.num" @change="handleChange" :min="0"></el-input-number>
            </template>
          </el-form-item>

          <span style="font-size: 17px;color: #56e8d7">请选择要移到哪个位置</span>

          <el-form-item label="请选择并点击确定">

            <el-select v-model="updateform.sId" @change="changeid()" clearable>
              <el-option v-for="s in stashList" :key="s.id" :label="s.stashName" :value="s.id"></el-option>
            </el-select>

          </el-form-item>

          <el-form-item label="具体位置">

            <el-select v-model="updateform.reId">
              <el-option v-for="r in remarkList" :key="r.id" :label="r.remark" :value="r.id"></el-option>
            </el-select>

          </el-form-item>




        </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateinv()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script >
import {
  allstaustype,
  delinv,
  listall,
  seleflag,
  twochange,
  updateinvupdate, userExcel
} from "@/api/inventory/inventory";
import {
  croptype,
} from "@/api/process/process";
export default {

  data(){
    return{
      formInline:{},
      stashList:{},
      remarkList:{},
      tableData:{},
      dialogFormVisible:false,
      updateform:{},
      typeList:{},
      firstList:{},
      allfrom:{}
    }
  },
  methods:{
    //导出数据
    userExcel(){
      userExcel().then(()=>{
        this.$message.success("导出成功")
      })
    },
    savegoods(){

    },
    // 删除(逻辑)
    godelinv(id){

        delinv(id).then(()=>{

          alert("删除成功")
          this.onSubmit();

        })

    },
    //(flag)查询
    nouse(flag){
      seleflag(flag).then(r=>{

        this.tableData=r.data;

      })
    },
    //修改
    updateinv(){

        updateinvupdate(this.updateform).then(r=>{
          if (r.code==200){

            this.dialogFormVisible=false;
            this.onSubmit();

          }else {

            this.$message.error(r.msg)

          }

        })
    },
    // 详情
    xiang(row){

      this.dialogFormVisible=true;
      this.updateform=row;
      // localStorage.setItem("row",JSON.stringify(row));
      // this.$router.push("/inventory/huixian")

    },
    //第一级下拉框
    allstashList(){
      allstaustype().then(res=>{

        this.stashList=res.data;

      })
    },
    //根据id找到第二级下拉框
    changeid(){

      twochange(this.updateform.sId).then(r=>{

        this.updateform.stashParentid={};
        this.remarkList=r.data;


      })
    },
    //查询
    onSubmit(){
      listall().then(r =>{

        this.tableData=r.data;
        this.updateform=r.data;
      })
    },
    //农作物下拉框
    typel(){

      croptype().then(r=>{

        this.typeList=r.data;

      })

    }
  },
  created() {

    this.allstashList();
    this.onSubmit();
    this.typel();
    this.invetype();
  }

}
</script>

<style scoped lang="scss">

</style>
