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
      <!--      <el-form-item label="楼栋" prop="buildingId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.buildingId"-->
      <!--          placeholder="请输入楼栋"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="宿舍" prop="dormitoryId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.dormitoryId"-->
      <!--          placeholder="请输入宿舍"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="床位状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择床位状态" clearable>
          <el-option
            v-for="dict in dict.type.dormitory_status"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:dormitoryBed:add']"
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
          v-hasPermi="['manage:dormitoryBed:edit']"
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
          v-hasPermi="['manage:dormitoryBed:remove']"
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
          v-hasPermi="['manage:dormitoryBed:import']"
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
          v-hasPermi="['manage:dormitoryBed:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dormitoryBedList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="楼栋" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="buildingName"/>
      <el-table-column label="宿舍" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="dormitoryName"/>
      <el-table-column label="床位状态" align="center" v-if="columns[3].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dormitory_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="床位编号" align="center" v-if="columns[4].visible" prop="bedNum"/>
      <el-table-column label="所属人" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="belongUserName"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="remark"/>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="userName"/>
      <el-table-column label="创建时间" align="center" v-if="columns[8].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" v-if="columns[9].visible" prop="updateTime" width="180">
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
            v-hasPermi="['manage:dormitoryBed:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleAllot(scope.row)"
            v-hasPermi="['manage:dormitoryBed:edit']"
          >分配
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleApply(scope.row)"
            v-hasPermi="['manage:dormitoryApply:add']"
          >申请
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:dormitoryBed:remove']"
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

    <!-- 添加或修改宿舍床位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="楼栋" prop="buildingId">-->
        <!--          <el-input v-model="form.buildingId" placeholder="请输入楼栋"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="床位编号" prop="bedNum">
          <el-input v-model="form.bedNum" placeholder="请输入床位编号"/>
        </el-form-item>
        <el-form-item label="床位状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.dormitory_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!--        <el-form-item label="所属人" prop="belongUserId">-->
        <!--          <el-select-->
        <!--            v-model="form.belongUserId"-->
        <!--            filterable-->
        <!--            remote-->
        <!--            reserve-keyword-->
        <!--            :remote-method="remoteUserList"-->
        <!--            :loading="userListLoading"-->
        <!--            placeholder="请选择用户名称"-->
        <!--          >-->
        <!--            <el-option-->
        <!--              v-for="item in userList"-->
        <!--              :key="item.userId"-->
        <!--              :label="item.userName"-->
        <!--              :value="item.userId"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>    <!-- 添加或修改宿舍床位对话框 -->
    <el-dialog :title="title" :visible.sync="openAllot" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属人" prop="belongUserId">
          <el-select
            v-model="form.belongUserId"
            filterable
            remote
            clearable
            reserve-keyword
            :remote-method="remoteUserList"
            :loading="userListLoading"
            placeholder="请选择用户名称"
          >
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormAllot">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 宿舍床位导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的宿舍床位数据
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

    <!-- 添加或修改宿舍申请对话框 -->
    <el-dialog :title="title" :visible.sync="openApply" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="申请理由" prop="applyContent">
          <el-input v-model="form.applyContent" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormApply">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addDormitoryBed,
  allotDormitoryBed,
  delDormitoryBed,
  getDormitoryBed,
  listDormitoryBed,
  updateDormitoryBed
} from "@/api/manage/dormitoryBed";
import {getToken} from "@/utils/auth";
import {listUserByRole} from "@/api/system/user";
import {addDormitoryApply} from "@/api/manage/dormitoryApply";

export default {
  name: "DormitoryBed",
  dicts: ['dormitory_status'],
  data() {
    return {
      //打开申请
      openApply: false,
      //打开
      openAllot: false,
      //宿舍编号
      dormitoryId: '',
      //用户查询
      userList: [],
      userListLoading: false,
      userListQuery: {
        pageNum: 1,
        pageSize: 100,
        roleId: 2
      },
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: true},
        {key: 1, label: '楼栋', visible: true},
        {key: 2, label: '宿舍', visible: true},
        {key: 3, label: '床位状态', visible: true},
        {key: 4, label: '床位编号', visible: true},
        {key: 5, label: '所属人', visible: true},
        {key: 6, label: '备注', visible: true},
        {key: 7, label: '创建人', visible: true},
        {key: 8, label: '创建时间', visible: true},
        {key: 9, label: '更新时间', visible: true},
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
      // 宿舍床位表格数据
      bedList: [],
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
        buildingId: null,
        dormitoryId: null,
        status: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/dormitoryBed/export',
      // 宿舍床位导入参数
      upload: {
        // 是否显示弹出层（宿舍床位导入）
        open: false,
        // 弹出层标题（宿舍床位导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的宿舍床位数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/manage/dormitoryBed/importData",
        // 下载模板的地址
        templateUrl: 'manage/dormitoryBed/importTemplate'
      },
      // 表单校验
      rules: {
        buildingId: [
          {required: true, message: "楼栋不能为空", trigger: "blur"}
        ],
        dormitoryId: [
          {required: true, message: "宿舍不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "床位状态不能为空", trigger: "change"}
        ],
        bedNum: [
          {required: true, message: "床位编号不能为空", trigger: "blur"}
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
  watch: {
    // 监听路由
    $route(to, from) {
      this.dormitoryId = to.query.dormitoryId;
      this.queryParams.dormitoryId = this.dormitoryId;
      this.getList();
    }
  },
  created() {
    this.dormitoryId = this.$route.query.dormitoryId;
    this.queryParams.dormitoryId = this.dormitoryId;
    this.getList();
    this.getUserList();
  },
  methods: {
    submitFormApply() {
      addDormitoryApply(this.form).then(response => {
        this.$modal.msgSuccess("申请成功");
        this.openApply = false;
        this.getList();
      })
    },
    handleApply(row) {
      this.reset();
      const id = row.id || this.ids
      getDormitoryBed(id).then(response => {
        this.form = response.data;
        this.form.bedId = id;
        this.form.id = null
        this.openApply = true;
        this.title = "申请宿舍床位";
      });
    },
    handleAllot(row) {
      this.reset();
      const id = row.id || this.ids
      getDormitoryBed(id).then(response => {
        this.form = response.data;
        this.openAllot = true;
        this.title = "分配宿舍床位";
      });
    },
    submitFormAllot() {
      allotDormitoryBed(this.form).then(res => {
        this.$modal.msgSuccess("分配成功");
        this.openAllot = false;
        this.getList();
      })
    },
    getUserList() {
      this.userListLoading = true;
      listUserByRole(this.userListQuery).then(response => {
        this.userList = response.rows;
        this.userListLoading = false;
      });
    },
    remoteUserList(query) {
      this.userListQuery.userName = query;
      this.getUserList();
    },
    /** 查询宿舍床位列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listDormitoryBed(this.queryParams).then(response => {
        this.dormitoryBedList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openAllot = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        buildingId: null,
        dormitoryId: this.dormitoryId,
        status: null,
        bedNum: null,
        belongUserId: null,
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
      this.queryParams.dormitoryId = this.dormitoryId;
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
      this.title = "添加宿舍床位";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDormitoryBed(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改宿舍床位";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDormitoryBed(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDormitoryBed(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除宿舍床位编号为"' + ids + '"的数据项？').then(function () {
        return delDormitoryBed(ids);
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
      }, `dormitoryBed_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "宿舍床位导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(this.upload.templateUrl, {}, `dormitoryBed_template_${new Date().getTime()}.xlsx`)
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
