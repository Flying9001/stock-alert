### 网易股票接口使用说明  


​    
​    

### 1 接口地址  

网易实时股价接口地址  

```
https://api.money.126.net/data/feed/
```



### 2 交易所编码  

| 交易所     | 编码 |
| ---------- | ---- |
| 上海交易所 | 0    |
| 深圳交易所 | 1    |

股票区分: 6 开头的股票为上海交易所的，其他基本为深圳交易所的  



### 3 使用示例  

查询单条  

```
https://api.money.126.net/data/feed/0600009
```

返回数据  

```
_ntes_quote_callback({"0600009":{"code": "0600009", "percent": -0.010588, "high": 53.48, "askvol3": 2400, "askvol2": 900, "askvol5": 2699, "askvol4": 100, "price": 52.33, "open": 52.75, "bid5": 52.27, "bid4": 52.28, "bid3": 52.29, "bid2": 52.31, "bid1": 52.32, "low": 52.19, "updown": -0.56, "type": "SH", "symbol": "600009", "status": 0, "ask4": 52.36, "bidvol3": 5800, "bidvol2": 4900, "bidvol1": 8800, "update": "2022/01/24 15:58:49", "bidvol5": 300, "bidvol4": 200, "yestclose": 52.89, "askvol1": 2400, "ask5": 52.39, "volume": 24352039, "ask1": 52.33, "name": "\u4e0a\u6d77\u673a\u573a", "ask3": 52.35, "ask2": 52.34, "arrow": "\u2193", "time": "2022/01/24 15:58:48", "turnover": 1284470779} });
```

批量查询  

```
https://api.money.126.net/data/feed/0600009,1002475
```

返回数据  

```
_ntes_quote_callback({"0600009":{"code": "0600009", "percent": -0.010588, "high": 53.48, "askvol3": 2400, "askvol2": 900, "askvol5": 2699, "askvol4": 100, "price": 52.33, "open": 52.75, "bid5": 52.27, "bid4": 52.28, "bid3": 52.29, "bid2": 52.31, "bid1": 52.32, "low": 52.19, "updown": -0.56, "type": "SH", "symbol": "600009", "status": 0, "ask4": 52.36, "bidvol3": 5800, "bidvol2": 4900, "bidvol1": 8800, "update": "2022/01/24 15:59:22", "bidvol5": 300, "bidvol4": 200, "yestclose": 52.89, "askvol1": 2400, "ask5": 52.39, "volume": 24352039, "ask1": 52.33, "name": "\u4e0a\u6d77\u673a\u573a", "ask3": 52.35, "ask2": 52.34, "arrow": "\u2193", "time": "2022/01/24 15:59:21", "turnover": 1284470779},"1002475":{"code": "1002475", "percent": 0.044501, "high": 50.18, "askvol3": 3600, "askvol2": 5100, "askvol5": 900, "askvol4": 7300, "price": 49.29, "open": 46.85, "bid5": 49.25, "bid4": 49.26, "bid3": 49.27, "bid2": 49.28, "bid1": 49.29, "low": 46.7, "updown": 2.1, "type": "SZ", "bidvol1": 118767, "status": 0, "bidvol3": 79000, "bidvol2": 49600, "symbol": "002475", "update": "2022/01/24 15:59:22", "bidvol5": 6600, "bidvol4": 3800, "volume": 74583662, "askvol1": 38400, "ask5": 49.34, "ask4": 49.33, "ask1": 49.3, "name": "\u7acb\u8baf\u7cbe\u5bc6", "ask3": 49.32, "ask2": 49.31, "arrow": "\u2191", "time": "2022/01/24 15:59:18", "yestclose": 47.19, "turnover": 3657342613.94} });
```



### 4 返回参数说明  

网易股票接口返回的数据可整理为 json 格式，从 json 对象中获取所需数据即可  

常见实用参数  

| 参数说明                | 参数名    |
| ----------------------- | --------- |
| 股票名称                | name      |
| 股票开盘价              | open      |
| 上一交易日股票收盘价    | yestclose |
| 当前股价                | price     |
| 涨跌额                  | updown    |
| 涨跌幅                  | percent   |
| 今日股票最高价          | high      |
| 今日股票最低价          | low       |
| 股票成交数(单位:**股**) | volume    |
| 成交金额(单位:**元**)   | turnover  |
| 股价日期                | time      |
| 股价时间                | time      |



### 5 注意事项  

目前(2022年1月)，网易实时股价接口未添加防护，可以直接复制链接到浏览器查询。为增强接口稳定性，推荐在请求时模拟用户页面访问，在请求头中添加网易财经的 `Referer`，网易总不会把自己的网站设置为黑名单。  

网易财经地址:  

```
https://money.163.com/
```

