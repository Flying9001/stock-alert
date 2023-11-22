#!/bin/sh
# start stock alert app with docker

# 打包镜像
docker build -t stock-alert . 

# 定义项目路径
path="/opt/springboot/"

# 运行镜像
docker run -dt \
--name stock-alert \
-p 8089:8089 \
-v ${path}/stock-alert/conf:/app/conf \
-v ${path}/logs/stock-alert:/logs/stock-alert \
stock-alert

