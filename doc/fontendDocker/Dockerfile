#基础 nginx镜像
FROM nginx:1.13.7
MAINTAINER GongChao  <gongchao_vendor@sensetime.com>
#把刚才生成dist文件夹下的文件copy到nginx下面去
COPY dist/  /usr/share/nginx/html/
#添加我们自己的配置 default.conf 在下面
COPY nginx.conf /etc/nginx/nginx.conf
#添加vim编辑器后期在容器中修改该nginx.conf配置
RUN mv /etc/apt/sources.list /etc/apt/sources.list.bak && \
    echo "deb http://mirrors.163.com/debian/ jessie main non-free contrib" >/etc/apt/sources.list && \
    echo "deb http://mirrors.163.com/debian/ jessie-proposed-updates main non-free contrib" >>/etc/apt/sources.list && \
    echo "deb-src http://mirrors.163.com/debian/ jessie main non-free contrib" >>/etc/apt/sources.list && \
    echo "deb-src http://mirrors.163.com/debian/ jessie-proposed-updates main non-free contrib" >>/etc/apt/sources.list
RUN apt-get update && apt-get install vim -y
#防止容器中的中文乱码
ENV LANG C.UTF-8
