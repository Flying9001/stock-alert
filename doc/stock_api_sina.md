### 新浪股票接口使用说明  


​    
​    

### 1 接口地址  

新浪实时股价接口地址  

```
https://hq.sinajs.cn/list=
```



### 2 交易所编码  

| 交易所     | 编码 |
| ---------- | ---- |
| 上海交易所 | sh   |
| 深圳交易所 | sz   |

股票区分: 6 开头的股票为上海交易所的，其他基本为深圳交易所的  



### 3 使用示例  

查询单条  

```
https://hq.sinajs.cn/list=sh600009
```

返回数据  

```
var hq_str_sh600009="上海机场,52.750,52.890,52.250,53.480,52.190,52.330,52.330,24061339,1269258448.000,269900,52.330,14400,0.000,0,0.000,0,0.000,0,0.000,269900,52.330,0,0.000,0,0.000,0,0.000,0,0.000,2022-01-24,14:59:57,00,";
```

批量查询  

```
https://hq.sinajs.cn/list=sh601006,sz002475
```

返回数据  

```
var hq_str_sh600009="上海机场,52.750,52.890,52.330,53.480,52.190,52.320,52.330,24352039,1284470779.000,8800,52.320,4900,52.310,5800,52.290,200,52.280,300,52.270,2400,52.330,900,52.340,2400,52.350,100,52.360,2699,52.390,2022-01-24,15:00:00,00,";
var hq_str_sz002475="立讯精密,46.850,47.190,49.290,50.180,46.700,49.290,49.300,74583662,3657342613.940,118767,49.290,49600,49.280,79000,49.270,3800,49.260,6600,49.250,38400,49.300,5100,49.310,3600,49.320,7300,49.330,900,49.340,2022-01-24,15:00:03,00";
```



### 4 返回参数说明  

将返回的股票数据按照 `,` 分割，将得到一个数组，可从数组中获取所需要的数据  

常见实用参数  

| 参数说明                | 参数位置(数组下标) |
| ----------------------- | ------------------ |
| 股票名称                | 0                  |
| 股票开盘价              | 1                  |
| 上一交易日股票收盘价    | 2                  |
| 当前股价                | 3                  |
| 涨跌额                  |                    |
| 涨跌幅                  |                    |
| 今日股票最高价          | 4                  |
| 今日股票最低价          | 5                  |
| 股票成交数(单位:**股**) | 8                  |
| 成交金额(单位:**元**)   | 9                  |
| 股价日期                | 30                 |
| 股价时间                | 31                 |

新浪股票接口不包括涨跌额和涨跌幅参数，涨跌额计算公式: `当前股价` 减去 `昨日收盘价`  

涨跌幅计算公式: `涨跌额` 除以 `昨日收盘价`  

### 5 注意事项  

目前(2022年1月)，新浪实时股价接口已经添加了防护，直接复制链接到浏览器查询将会被拒绝访问。解决办法: 模拟用户页面访问，在请求头中添加新浪财经的 `Referer`，新浪总不会把自己的网站设置为黑名单。  

新浪财经地址:  

```
https://finance.sina.com.cn/
```

