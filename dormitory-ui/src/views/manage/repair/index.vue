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
      <el-form-item label="楼栋" prop="buildingId">
        <el-input
          v-model="queryParams.buildingId"
          placeholder="请输入楼栋"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="宿舍" prop="dormitoryId">
        <el-input
          v-model="queryParams.dormitoryId"
          placeholder="请输入宿舍"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="床位" prop="bedId">
        <el-input
          v-model="queryParams.bedId"
          placeholder="请输入床位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报修类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择报修类型" clearable>
          <el-option
            v-for="dict in dict.type.repair_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="异常状态" prop="abnormalStatus">
        <el-select v-model="queryParams.abnormalStatus" placeholder="请选择异常状态" clearable>
          <el-option
            v-for="dict in dict.type.abnormal_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报修状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择报修状态" clearable>
          <el-option
            v-for="dict in dict.type.repair_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报修位置" prop="repairAddress">
        <el-select v-model="queryParams.repairAddress" placeholder="请选择报修位置" clearable>
          <el-option
            v-for="dict in dict.type.repair_address"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
                        v-model="queryParams.createTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:repair:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:repair:edit']"
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
          v-hasPermi="['manage:repair:remove']"
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
          v-hasPermi="['manage:repair:import']"
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
          v-hasPermi="['manage:repair:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="repairList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="楼栋" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="buildingName"/>
      <el-table-column label="宿舍" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="dormitoryName"/>
      <el-table-column label="床位" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="bedNum"/>
      <el-table-column label="报修类型" align="center" v-if="columns[4].visible" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.repair_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="异常状态" align="center" v-if="columns[5].visible" prop="abnormalStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.abnormal_status" :value="scope.row.abnormalStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="报修状态" align="center" v-if="columns[6].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.repair_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="报修位置" align="center" v-if="columns[7].visible" prop="repairAddress">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.repair_status" :value="scope.row.repairAddress"/>
        </template>
      </el-table-column>
      <el-table-column label="发现时间" align="center" v-if="columns[8].visible" prop="repairTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.repairTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="问题照片" align="center" v-if="columns[9].visible" prop="repairImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.repairImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="问题内容" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="repairContent"/>
      <el-table-column label="联系人" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="contactUser"/>
      <el-table-column label="联系电话" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="contactPhone"/>
      <el-table-column label="完成时间" align="center" v-if="columns[13].visible" prop="completedTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.completedTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理费用" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="dealWithCost"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="remark"/>
      <el-table-column label="处理人" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="dealWithName"/>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible"
                       prop="userName"/>
      <el-table-column label="创建时间" align="center" v-if="columns[18].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" v-if="columns[19].visible" prop="updateTime" width="180">
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
            v-hasPermi="['manage:repair:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:repair:remove']"
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

    <!-- 添加或修改报修记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="楼栋" prop="buildingId">
          <el-select
            v-model="form.buildingId"
            filterable
            remote
            reserve-keyword
            :remote-method="remoteBuildingList"
            :loading="buildingLoading"
            placeholder="请选择楼栋名称"
          >
            <el-option
              v-for="item in buildingList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="宿舍" prop="dormitoryId">
          <el-select
            v-model="form.dormitoryId"
            filterable
            remote
            reserve-keyword
            :remote-method="remoteDormitoryList"
            :loading="dormitoryLoading"
            placeholder="请选择宿舍名称"
          >
            <el-option
              v-for="item in dormitoryList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="床位" prop="bedId">
          <el-select
            v-model="form.bedId"
            filterable
            remote
            reserve-keyword
            :remote-method="remoteBedList"
            :loading="bedLoading"
            placeholder="请选择床位名称"
          >
            <el-option
              v-for="item in bedList"
              :key="item.id"
              :label="item.bedNum"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报修类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择报修类型">
            <el-option
              v-for="dict in dict.type.repair_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="异常状态" prop="abnormalStatus">
          <el-radio-group v-model="form.abnormalStatus">
            <el-radio
              v-for="dict in dict.type.abnormal_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
<!--        <el-form-item label="报修状态" prop="status">-->
<!--          <el-radio-group v-model="form.status">-->
<!--            <el-radio-->
<!--              v-for="dict in dict.type.repair_status"-->
<!--              :key="dict.value"-->
<!--              :label="dict.value"-->
<!--            >{{ dict.label }}-->
<!--            </el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->
        <el-form-item label="报修位置" prop="repairAddress">
          <el-select v-model="form.repairAddress" placeholder="请选择报修位置">
            <el-option
              v-for="dict in dict.type.repair_address"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发现时间" prop="repairTime">
          <el-date-picker clearable
                          v-model="form.repairTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择发现时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="问题照片" prop="repairImage">
          <image-upload v-model="form.repairImage"/>
        </el-form-item>
        <el-form-item label="问题内容">
          <el-input type="textarea" v-model="form.repairContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="联系人" prop="contactUser">
          <el-input v-model="form.contactUser" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话"/>
        </el-form-item>
<!--        <el-form-item label="完成时间" prop="completedTime">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.completedTime"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择完成时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="处理费用" prop="dealWithCost">-->
<!--          <el-input v-model="form.dealWithCost" placeholder="请输入处理费用"/>-->
<!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
<!--        <el-form-item label="处理人" prop="dealWithId">-->
<!--          <el-input v-model="form.dealWithId" placeholder="请输入处理人"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="创建人" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入创建人"/>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 报修记录导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的报修记录数据
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
  listRepair,
  getRepair,
  delRepair,
  addRepair,
  updateRepair,
  importRepair,
  importTemplateRepair
} from "@/api/manage/repair";
import {getToken} from "@/utils/auth";
import {listBuilding} from "@/api/manage/building";
import {listDormitory} from "@/api/manage/dormitory";
import {listDormitoryBed} from "@/api/manage/dormitoryBed";

export default {
  name: "Repair",
  dicts: ['repair_type', 'repair_status', 'abnormal_status', 'repair_address'],
  data() {
    return {
      //楼栋
      buildingList: [],
      buildingQuery: {
        pageNum: 1,
        pageSize: 100
      },
      buildingLoading: false,
      //宿舍
      dormitoryList: [],
      dormitoryQuery: {
        pageNum: 1,
        pageSize: 100
      },
      dormitoryLoading: false,
      //床位
      bedList: [],
      bedQuery: {
        pageNum: 1,
        pageSize: 100
      },
      bedLoading: false,
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: false},
        {key: 1, label: '楼栋', visible: true},
        {key: 2, label: '宿舍', visible: true},
        {key: 3, label: '床位', visible: true},
        {key: 4, label: '报修类型', visible: true},
        {key: 5, label: '异常状态', visible: true},
        {key: 6, label: '报修状态', visible: true},
        {key: 7, label: '报修位置', visible: true},
        {key: 8, label: '发现时间', visible: true},
        {key: 9, label: '问题照片', visible: true},
        {key: 10, label: '问题内容', visible: true},
        {key: 11, label: '联系人', visible: true},
        {key: 12, label: '联系电话', visible: true},
        {key: 13, label: '完成时间', visible: false},
        {key: 14, label: '处理费用', visible: false},
        {key: 15, label: '备注', visible: false},
        {key: 16, label: '处理人', visible: false},
        {key: 17, label: '创建人', visible: false},
        {key: 18, label: '创建时间', visible: true},
        {key: 19, label: '更新时间', visible: false},
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
      // 报修记录表格数据
      repairList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        buildingId: null,
        dormitoryId: null,
        bedId: null,
        type: null,
        abnormalStatus: null,
        status: null,
        repairAddress: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/repair/export',
      // 报修记录导入参数
      upload: {
        // 是否显示弹出层（报修记录导入）
        open: false,
        // 弹出层标题（报修记录导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的报修记录数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/manage/repair/importData",
        // 下载模板的地址
        templateUrl: 'manage/repair/importTemplate'
      },
      // 表单校验
      rules: {
        buildingId: [
          {required: true, message: "楼栋不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "报修类型不能为空", trigger: "change"}
        ],
        abnormalStatus: [
          {required: true, message: "异常状态不能为空", trigger: "change"}
        ],
        status: [
          {required: true, message: "报修状态不能为空", trigger: "change"}
        ],
        repairAddress: [
          {required: true, message: "报修位置不能为空", trigger: "blur"}
        ],
        repairTime: [
          {required: true, message: "发现时间不能为空", trigger: "blur"}
        ],
        repairContent: [
          {required: true, message: "问题内容不能为空", trigger: "blur"}
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
    this.getBuildingList();
    this.getDormitoryList();
    this.getDormitoryBedList();
  },
  methods: {
    //获取楼栋信息
    getBuildingList() {
      this.buildingLoading = true;
      listBuilding(this.buildingQuery).then(response => {
        this.buildingList = response.rows;
        this.buildingLoading = false;
      });
    },
    remoteBuildingList(queryString) {
      this.buildingQuery.name = queryString;
      this.getBuildingList()
    },
    //获取宿舍信息
    getDormitoryList() {
      this.dormitoryLoading = true;
      listDormitory(this.dormitoryQuery).then(response => {
        this.dormitoryList = response.rows;
        this.dormitoryLoading = false;
      });
    },
    remoteDormitoryList(queryString) {
      this.dormitoryQuery.name = queryString;
      this.getDormitoryList()
    },
    //获取床位信息
    getDormitoryBedList() {
      this.bedLoading = true;
      listDormitoryBed(this.bedQuery).then(response => {
        this.bedList = response.rows;
        this.bedLoading = false;
      });
    },
    remoteBedList(queryString) {
      this.bedQuery.name = queryString;
      this.getDormitoryBedList()
    },
    /** 查询报修记录列表 */
    getList() {
      this.loading = true;
      listRepair(this.queryParams).then(response => {
        this.repairList = response.rows;
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
        buildingId: null,
        dormitoryId: null,
        bedId: null,
        type: null,
        abnormalStatus: null,
        status: null,
        repairAddress: null,
        repairTime: null,
        repairImage: null,
        repairContent: null,
        contactUser: null,
        contactPhone: null,
        completedTime: null,
        dealWithCost: null,
        remark: null,
        dealWithId: null,
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
      this.title = "添加报修记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRepair(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报修记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRepair(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRepair(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除报修记录编号为"' + ids + '"的数据项？').then(function () {
        return delRepair(ids);
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
      }, `repair_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "报修记录导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(this.upload.templateUrl, {}, `repair_template_${new Date().getTime()}.xlsx`)
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
