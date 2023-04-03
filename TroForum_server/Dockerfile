# springBoot和Docker整合
# VERSION 0.0.1
# Author: Tro
# 安装jdk8
FROM amazoncorretto:8

#维护者
MAINTAINER trotroisgoodboy<trotroisgoodboy@gmail.com>

#上传jar
ADD  ./build/libs/TroForum_server-0.0.1-SNAPSHOT.jar /TroForum_server.jar

#新建文件夹
RUN mkdir /upload
RUN mkdir /upload/avatar

#映射端口号
EXPOSE 8080

# 运行jar包
ENTRYPOINT ["java","-jar", "/TroForum_server.jar"]