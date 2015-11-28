#!/bin/bash

#调用方式
# autoRelease.sh
# 自动发布版本
# 1. 版本号release自动增加，并commit；
# 2. 建立release分支
# 请在develop分支中执行
# xiangdong 2015.11.20

#找到该脚本的绝对路径，防止不到此目录执行脚本，导致相对路径出错
basepath=$(cd `dirname $0`; pwd)
cd $basepath

#切换develop分支
git checkout develop

#从版本配置文件获取版本相关信息到环境变量
source ../version.properties

let release=$release+1 

#更新version.release
sedArgs="s/release=.*$/release=${release}/"
sed -i ""  $sedArgs ../version.properties
echo "release inc ${release} OK."

#新的版本
new_version=$major.$minor.$release
echo "new version is ${new_version}"
git add -u
git commit -m "release ${new_version}"
echo "git commit OK."

git flow release start $new_version
echo "release branch ${new_version} is OK"