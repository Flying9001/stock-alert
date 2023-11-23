#!/bin/sh
# shutdown stock alert docker app

# 应用名称
appName="stock-alert-app"

# 获取容器 id
cid=$(docker container ls -a | grep ${appName} | awk '{ print $1 }')

if [[ -z "${cid}" ]]
then 
  echo "no ${appName} running!"
else
  echo "${appName} container id: ${cid}"
  # 停止容器
  echo "stop container ${appName}"
  docker container stop ${cid}

  # 删除容器
  echo "remove container ${appName}"
  docker container rm ${cid}
 
  # 删除镜像
  echo "stop container ${appName}"
  docker image rm ${appName}

  echo "${appName} stopped successfully!"
fi

