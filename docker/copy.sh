#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/quartz.sql ./mysql/db
cp ../sql/yunque_1.sql ./mysql/db
cp ../sql/yunque_2.sql ./mysql/db
cp ../sql/xy_config.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../yunque-ui/main/dist/** ./nginx/html/main
cp -r ../yunque-ui/administrator/dist/** ./nginx/html/administrator


# copy jar
echo "begin copy yunque-gateway "
cp ../yunque-gateway/target/yunque-gateway.jar ./yunque/gateway/jar

echo "begin copy yunque-auth "
cp ../yunque-auth/target/yunque-auth.jar ./yunque/auth/jar

echo "begin copy yunque-visual "
cp ../yunque-visual/yunque-monitor/target/yunque-visual-monitor.jar  ./yunque/visual/monitor/jar

echo "begin copy yunque-modules-system "
cp ../yunque-modules/yunque-system/target/yunque-modules-system.jar ./yunque/modules/system/jar

echo "begin copy yunque-modules-tenant "
cp ../yunque-modules/yunque-tenant/target/yunque-modules-tenant.jar ./yunque/modules/tenant/jar

echo "begin copy yunque-modules-file "
cp ../yunque-modules/yunque-file/target/yunque-modules-file.jar ./yunque/modules/file/jar

echo "begin copy yunque-modules-job "
cp ../yunque-modules/yunque-job/target/yunque-modules-job.jar ./yunque/modules/job/jar

echo "begin copy yunque-modules-gen "
cp ../yunque-modules/yunque-gen/target/yunque-modules-gen.jar ./yunque/modules/gen/jar