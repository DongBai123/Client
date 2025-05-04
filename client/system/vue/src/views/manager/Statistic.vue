<template>
  <div class="statistic-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>统计分析</span>
        </div>
      </template>

      <!-- 统计类型选择 -->
      <el-radio-group v-model="activeTab" @change="handleTabChange">
        <el-radio-button label="user">按用户统计</el-radio-button>
        <el-radio-button label="goods">按商品统计</el-radio-button>
        <el-radio-button label="detail">详细统计</el-radio-button>
      </el-radio-group>

      <!-- 图表显示 -->
      <div class="chart-container">
        <v-chart
            :option="chartOption"
            :loading="loading"
            style="width: 100%; height: 400px;"
            autoresize
        />
      </div>

      <!-- 统计数据表格 -->
      <el-table
          :data="tableData"
          style="width: 100%; margin-top: 20px"
          border
          v-loading="loading"
      >
        <!-- 用户名称列 -->
        <el-table-column
            v-if="activeTab === 'user' || activeTab === 'detail'"
            prop="userName"
            label="用户名称"
            min-width="120"
        />

        <!-- 商品名称列 -->
        <el-table-column
            v-if="activeTab === 'goods' || activeTab === 'detail'"
            prop="goodsName"
            label="商品名称"
            min-width="120"
        />
        <!-- 订单数量列 -->
        <el-table-column
            prop="orderCount"
            label="订单数量"
            min-width="100"
        />
        <!-- 购买数量列 -->
        <el-table-column
            prop="buyNum"
            label="购买数量"
            min-width="100"
        />

        <!-- 销售金额列 -->
        <el-table-column
            prop="sellSum"
            label="销售金额"
            min-width="120"
        >
          <template #default="scope">
            {{ formatPrice(scope.row.sellSum) }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import request from '@/utils/request'
import { ElMessage } from 'element-plus'
import { use } from "echarts/core"
import { CanvasRenderer } from "echarts/renderers"
import { BarChart, LineChart } from "echarts/charts"
import {
  GridComponent,
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from "echarts/components"
import VChart from "vue-echarts"

// 注册必须的组件
use([
  CanvasRenderer,
  BarChart,
  LineChart,
  GridComponent,
  TitleComponent,
  TooltipComponent,
  LegendComponent
])

export default {
  name: 'StatisticView',
  components: {
    VChart
  },
  data() {
    return {
      activeTab: 'user',
      tableData: [],
      loading: false,
      chartOption: {}
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        let url = ''
        switch (this.activeTab) {
          case 'user':
            url = '/statistic/byUser'
            break
          case 'goods':
            url = '/statistic/byGoods'
            break
          case 'detail':
            url = '/statistic/byUserAndGoods'
            break
        }

        const res = await request.get(url)
        console.log('响应数据：', res)

        if (res.code === '200') {
          // 处理表格数据
          this.tableData = res.data.map(item => ({
            userId: item.userId,
            userName: item.userName || '未知用户',
            goodsId: item.goodsId,
            goodsName: item.goodsName || '未知商品',
            buyNum: item.buyNum || 0,
            sellSum: item.sellSum || 0,
            orderCount: item.orderCount || 0
          }))

          // 更新图表数据
          this.updateChartOption()

        } else {
          ElMessage.error(res.msg || '获取统计数据失败')
        }
      } catch (error) {
        console.error('获取统计数据出错:', error)
        ElMessage.error('获取统计数据失败: ' + (error.message || '未知错误'))
      } finally {
        this.loading = false
      }
    },
    handleTabChange() {
      this.fetchData()
    },
    formatPrice(price) {
      return `¥ ${(Number(price) || 0).toFixed(2)}`
    },
    updateChartOption() {
      const xAxisData = this.tableData.map(item =>
          this.activeTab === 'user' ? item.userName :
              this.activeTab === 'goods' ? item.goodsName :
                  `${item.userName}-${item.goodsName}`
      )

      this.chartOption = {
        title: {
          text: '统计数据分析',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['订单数量', '购买数量', '销售金额'],
          top: 30
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: xAxisData,
          axisLabel: {
            interval: 0,
            rotate: 30
          }
        },
        yAxis: [
          {
            type: 'value',
            name: '数量',
            position: 'left'
          },
          {
            type: 'value',
            name: '金额(¥)',
            position: 'right'
          }
        ],
        series: [
          {
            name: '订单数量',
            type: 'bar',
            data: this.tableData.map(item => item.orderCount)
          },
          {
            name: '购买数量',
            type: 'bar',
            data: this.tableData.map(item => item.buyNum)
          },
          {
            name: '销售金额',
            type: 'line',
            yAxisIndex: 1,
            data: this.tableData.map(item => item.sellSum)
          }
        ]
      }
    }
  }
}
</script>

<style scoped>
.statistic-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-radio-group {
  margin-bottom: 20px;
}

.chart-container {
  margin: 20px 0;
  border: 1px solid #ebeef5;
  padding: 20px;
  border-radius: 4px;
}
</style>