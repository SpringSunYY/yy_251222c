<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修" prop="repairId">
        <el-input
          v-model="queryParams.repairId"
          placeholder="请输入报修"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择工单状态" clearable>
          <el-option
            v-for="dict in dict.type.work_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['manage:repairWorkOrder:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:repairWorkOrder:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:repairWorkOrder:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['manage:repairWorkOrder:import']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:repairWorkOrder:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="repairWorkOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="报修" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="repairId"/>
      <el-table-column label="工单状态" align="center" v-if="columns[2].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.work_order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="维修工" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="dealWithName"/>
      <el-table-column label="处理时间" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="dealWithTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dealWithTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理费用" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="dealWithCost"/>
      <el-table-column label="处理内容" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="dealWithContent"/>
      <el-table-column label="处理照片" align="center" v-if="columns[7].visible" prop="dealWithImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.dealWithImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="completedTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.completedTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="remark"/>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="userName"/>
      <el-table-column label="创建时间" align="center" v-if="columns[11].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" v-if="columns[12].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:repairWorkOrder:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:repairWorkOrder:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改维修工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="报修" prop="repairId">-->
        <!--          <el-input v-model="form.repairId" placeholder="请输入报修"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="工单状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.work_order_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!--        <el-form-item label="维修工" prop="dealWithId">-->
        <!--          <el-input v-model="form.dealWithId" placeholder="请输入维修工"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="处理费用" prop="dealWithCost">
          <el-input-number :min="0" v-model="form.dealWithCost" placeholder="请输入处理费用"/>
        </el-form-item>
        <el-form-item label="处理内容">
          <el-input type="textarea" v-model="form.dealWithContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="处理照片" prop="dealWithImage">
          <image-upload v-model="form.dealWithImage"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 维修工单导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的维修工单数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline"
                   @click="importTemplate">下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRepairWorkOrder,
  getRepairWorkOrder,
  delRepairWorkOrder,
  addRepairWorkOrder,
  updateRepairWorkOrder,
  importRepairWorkOrder,
  importTemplateRepairWorkOrder
} from "@/api/manage/repairWorkOrder";
import {getToken} from "@/utils/auth";

export default {
  name: "RepairWorkOrder",
  dicts: ['work_order_status'],
  data() {
    return {
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: true},
        {key: 1, label: '报修', visible: true},
        {key: 2, label: '工单状态', visible: true},
        {key: 3, label: '维修工', visible: true},
        {key: 4, label: '处理时间', visible: true},
        {key: 5, label: '处理费用', visible: true},
        {key: 6, label: '处理内容', visible: true},
        {key: 7, label: '处理照片', visible: true},
        {key: 8, label: '完成时间', visible: true},
        {key: 9, label: '备注', visible: true},
        {key: 10, label: '创建人', visible: true},
        {key: 11, label: '创建时间', visible: true},
        {key: 12, label: '更新时间', visible: false},
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 维修工单表格数据
      repairWorkOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        repairId: null,
        status: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/repairWorkOrder/export',
      // 维修工单导入参数
      upload: {
        // 是否显示弹出层（维修工单导入）
        open: false,
        // 弹出层标题（维修工单导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的维修工单数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/manage/repairWorkOrder/importData",
        // 下载模板的地址
        templateUrl: 'manage/repairWorkOrder/importTemplate'
      },
      // 表单校验
      rules: {
        repairId: [
          {required: true, message: "报修不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "工单状态不能为空", trigger: "change"}
        ],
        dealWithId: [
          {required: true, message: "维修工不能为空", trigger: "blur"}
        ],
        remark: [
          {required: false, message: "备注不能为空", trigger: "blur"}
        ],
        userId: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询维修工单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listRepairWorkOrder(this.queryParams).then(response => {
        this.repairWorkOrderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        repairId: null,
        status: null,
        dealWithId: null,
        dealWithTime: null,
        dealWithCost: null,
        dealWithContent: null,
        dealWithImage: null,
        completedTime: null,
        remark: null,
        userId: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加维修工单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRepairWorkOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改维修工单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRepairWorkOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRepairWorkOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除维修工单编号为"' + ids + '"的数据项？').then(function () {
        return delRepairWorkOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(this.exportUrl, {
        ...this.queryParams
      }, `repairWorkOrder_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "维修工单导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(this.upload.templateUrl, {}, `repairWorkOrder_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
