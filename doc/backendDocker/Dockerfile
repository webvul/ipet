#base mirror
FROM anapsix/alpine-java:8_server-jre
#create by
MAINTAINER gongchao <gongchao_vendor@sensetime.com>
#set workdir
WORKDIR /ipet
#拷贝文件到容器
ADD ipet-server-1.0.jar /ipet/ipet.jar
# 暂定
ENV JAVA_OPTS="-server -Xms1024m -Xmx1024m"
#防止容器中的中文乱码
ENV LANG C.UTF-8
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ipet/ipet.jar" ]