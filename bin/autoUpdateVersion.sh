#!/bin/bash

#调用方式
# autoUpdateVersion.sh [revision]  revision 可选，默认为 SNAPSHOT

#从版本配置文件获取版本相关信息到环境变量
source ../version.properties

#如果传入revision，则为build id， 否则为快照 SNAPSHOT
if [ -n "$1" ]; then
	revision=$1
fi

#形成完整的版本信息
version=$major.$minor.$build-$revision

echo newversion:$version

#更新当前工程的版本号
sedArgs="s/<version><!--current-->.*$/<version><!--current-->${version}<\/version>/"
sed -i ""  $sedArgs ../pom.xml
echo "current pom.xml update version to ${version} OK." 

#更新子工程的 parent 版本号
#e5-common
sedArgs="s/<version><!--parent-->.*$/<version><!--parent-->${version}<\/version>/"
sed -i ""  $sedArgs ../e5-common/pom.xml
echo "e5-common pom.xml update parent version to ${version} OK."

#e5-common-web
sedArgs="s/<version><!--parent-->.*$/<version><!--parent-->${version}<\/version>/"
sed -i ""  $sedArgs ../e5-common-web/pom.xml
echo "e5-common-web pom.xml update parent version to ${version} OK."

#uc-dao
sedArgs="s/<version><!--parent-->.*$/<version><!--parent-->${version}<\/version>/"
sed -i ""  $sedArgs ../uc-dao/pom.xml
echo "uc-dao pom.xml update parent version to ${version} OK."

#uc-service
sedArgs="s/<version><!--parent-->.*$/<version><!--parent-->${version}<\/version>/"
sed -i ""  $sedArgs ../uc-service/pom.xml
echo "uc-service pom.xml update parent version to ${version} OK."

#uc-web
sedArgs="s/<version><!--parent-->.*$/<version><!--parent-->${version}<\/version>/"
sed -i ""  $sedArgs ../uc-web/pom.xml
echo "uc-web pom.xml update parent version to ${version} OK."


#不是快照版本需要更新build
if [ "$revision" != "SNAPSHOT" ]; then
	#更新$build, 自动 + 1
	let build=$build+1

	#更新版本配置文件
	sedArgs="s/build.*$/build=${build}/"
	sed -i ""  $sedArgs ../version.properties
	echo "version.properties update OK."

	#更新提交到版本库
	#git add ../version.properties
	#git commit -m "release v${version}"
	#echo "git commit OK."
fi