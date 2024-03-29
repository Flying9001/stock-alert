### 腾讯股票接口使用说明  


​    
​    

### 1 接口地址  

腾讯实时股价接口地址  

```
https://sqt.gtimg.cn/q=
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
https://sqt.gtimg.cn/q=sh600009
```

返回数据  

```
v_sh600009="1~上海机场~600009~52.33~52.89~52.75~243520~107651~135870~52.32~88~52.31~49~52.29~58~52.28~2~52.27~3~52.33~24~52.34~9~52.35~24~52.36~1~52.39~27~~20220124153812~-0.56~-1.06~53.48~52.19~52.33/243520/1284470779~243520~128447~2.23~-56.64~~53.48~52.19~2.44~572.22~1008.38~3.61~58.18~47.60~1.22~115~52.75~-60.45~-79.61~~~0.71~128447.0779~0.0000~0~ ~GP-A~12.08~8.34~0.00~-6.37~-3.20~81.42~36.60~4.68~17.41~-0.89~1093476406~1926958438~40.35~6.60~1093476406";
```

批量查询  

```
https://sqt.gtimg.cn/q=sh600009,sz002475
```

返回数据  

```
v_sh600009="1~上海机场~600009~52.33~52.89~52.75~243520~107651~135870~52.32~88~52.31~49~52.29~58~52.28~2~52.27~3~52.33~24~52.34~9~52.35~24~52.36~1~52.39~27~~20220124153912~-0.56~-1.06~53.48~52.19~52.33/243520/1284470779~243520~128447~2.23~-56.64~~53.48~52.19~2.44~572.22~1008.38~3.61~58.18~47.60~1.22~115~52.75~-60.45~-79.61~~~0.71~128447.0779~0.0000~0~
~GP-A~12.08~8.34~0.00~-6.37~-3.20~81.42~36.60~4.68~17.41~-0.89~1093476406~1926958438~40.35~6.60~1093476406";
v_sz002475="51~立讯精密~002475~49.29~47.19~46.85~745837~450603~295234~49.29~1188~49.28~496~49.27~790~49.26~38~49.25~66~49.30~384~49.31~51~49.32~36~49.33~73~49.34~9~~20220124153903~2.10~4.45~50.18~46.70~49.29/745837/3657342614~745837~365734~1.06~48.17~~50.18~46.70~7.37~3482.42~3485.45~10.79~51.91~42.47~1.47~2025~49.04~55.74~48.24~~~1.41~365734.2614~0.0000~0~
~GP-A~0.18~5.10~0.22~22.10~7.34~56.44~31.79~1.42~-0.50~27.56~7065170000~7071322500~64.68~34.67~7065170000";
```



### 4 返回参数说明  

将返回的股票数据按照 `~` 分割，将得到一个数组，可从数组中获取所需要的数据  

常见实用参数  

| 参数说明                | 参数位置(数组下标) |
| ----------------------- | ------------------ |
| 股票名称                | 1                  |
| 股票开盘价              | 5                  |
| 上一交易日股票收盘价    | 4                  |
| 当前股价                | 3                  |
| 涨跌额                  | 31                 |
| 涨跌幅(%)               | 32                 |
| 今日股票最高价          | 33                 |
| 今日股票最低价          | 34                 |
| 股票成交数(单位:**手**) | 6                  |
| 成交金额(单位:**万元**) | 37                 |
| 股价日期                | 30                 |
| 股价时间                | 30                 |



### 5 注意事项  

目前(2022年1月)，腾讯实时股价接口未添加防护，可以直接复制链接到浏览器查询。为增强接口稳定性，推荐在请求时模拟用户页面访问，在请求头中添加腾讯财经的 `Referer`，腾讯总不会把自己的网站设置为黑名单。  

腾讯财经地址:  

```
https://stockapp.finance.qq.com/
```

