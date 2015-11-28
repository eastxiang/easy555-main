#!/bin/bash

#调用方式
# autoIncMinorVersion.sh
# 自动增加minor
# xiangdong 2015.11.20

#找到该脚本的绝对路径，防止不到此目录执行脚本，导致相对路径出错
basepath=$(cd `dirname $0`; pwd)
cd $basepath

#从版本配置文件获取版本相关信息到环境变量
source ../version.properties

#更新$minor, 自动 + 1
let minor=$minor+1
 

#更新version.minor
sedArgs="s/minor=.*$/minor=${minor}/"
sed -i ""  $sedArgs ../version.properties
echo "minor inc ${minor} OK."

#自动归零 release
sedArgs="s/release=.*$/release=0/"
sed -i ""  $sedArgs ../version.properties
echo "release update 0 OK."

#自动归零 regression
sedArgs="s/regression=.*$/regression=0/"
sed -i ""  $sedArgs ../version.properties
echo "regression update 0 OK."

#执行生成版本号
./autoUpdateVersion.sh

git add -u
git commit -m "version-${major}.${minor}.0"
echo "git commit OK."