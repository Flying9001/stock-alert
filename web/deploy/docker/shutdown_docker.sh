#!/bin/sh
# shutdown stock alert docker app

# 获取容器 id
cid=$(docker container ls -a | grep stock-alert | awk '{ print $1 }')

if [[ -z "${cid}" ]]
then 
  echo "no stock-alert app running!"
else
  echo "stock-alert container id: ${cid}"
  # 停止容器
  docker container stop ${cid}

  # 删除容器
  docker container rm ${cid}
 
  # 删除镜像
  docker image rm stock-alert

  echo "stock-alert stopped successfully!"
fi

