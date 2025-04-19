<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.invoiceNum" style="width: 300px; margin-right: 10px" placeholder="发票编号查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="data.tableData" stripe>
        <el-table-column label="发票编号" prop="invoiceNum"></el-table-column>
        <el-table-column label="金额" prop="amount"></el-table-column>
        <el-table-column label="发票类型" prop="invoiceType"></el-table-column>
        <el-table-column label="发票状态" prop="state"></el-table-column>
        <el-table-column label="申请人" prop="userName"></el-table-column>
        <el-table-column label="电话" prop="phone"></el-table-column>
        <el-table-column label="申请时间" prop="time"></el-table-column>
        <el-table-column label="操作" header-align="center" width="160">
          <template #default="scope">
            <el-button v-if="data.user.role === 'USER'&& scope.row.state === '待申请'" type="primary" @click="changeState(scope.row,'待审核')">申请</el-button>
            <el-button v-if="data.user.role === 'ADMIN'&& scope.row.state === '待审核'" type="primary" @click="changeState(scope.row,'已通过')">通过</el-button>
            <el-button v-if="data.user.role === 'USER'" type="danger" @click="handleDelete(scope.row.id)">撤销</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card" v-if="data.total">
      <el-pagination background layout="prev, pager, next" v-model:page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog name="发票信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="金额" prop="amount">
          <el-input v-model="data.form.amount" placeholder="请输入发票金额" autocomplete="off" />
        </el-form-item>
        <el-form-item label="发票类型" prop="invoiceType">
          <el-input v-model="data.form.invoiceType" placeholder="请输入发票类型" autocomplete="off" />
        </el-form-item>
        <el-form-item label="申请人" prop="invoiceType">
          <el-input v-model="data.form.userName" placeholder="请输入申请人姓名" autocomplete="off" />
        </el-form-item>
        <el-form-item label="申请人电话" prop="phone">
          <el-input v-model="data.form.phone" placeholder="请输入申请人电话" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";

// 文件上传的接口地址
const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
  form: {},
  tableData: [],
  name: null
})

// 分页查询
const load = () => {
  request.get('/invoice/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      invoiceNum: data.invoiceNum
    }
  }).then(res => {
    data.tableData = res.data?.list
    data.total = res.data?.total
  })
}

// 新增
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

// 编辑
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

// 新增保存
const add = () => {
  request.post('/invoice/add', data.form).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 编辑保存
const update = () => {
  request.put('/invoice/update', data.form).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//改变状态
const changeState = (row,state) => {
  row.state=state
  request.put('/invoice/update', row).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('操作成功')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 弹窗保存
const save = () => {
  // data.form有id就是更新，没有就是新增
  data.form.id ? update() : add()
}

// 删除
const handleDelete = (id) => {
  ElMessageBox.confirm('撤销后数据无法恢复，您确定撤销吗?', '撤销确认', { type: 'warning' }).then(res => {
    request.delete('/invoice/delete/' + id).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {})
}

// 重置
const reset = () => {
  data.invoiceNum=null
  load()
}


// 处理文件上传的钩子
const handleImgSuccess = (res) => {
  data.form.avatar = res.data  // res.data就是文件上传返回的文件路径，获取到路径后赋值表单的属性
}

load()
</script>