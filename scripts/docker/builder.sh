#!/bin/bash
set -e
cwd=$(dirname "${0}")
if [[ ! -d $cwd/tmp ]];
then
  mkdir -p "${cwd}"/tmp
else
  rm -rf "${cwd}"/tmp/*
fi;

app_jar_file=${cwd}/../../app/build/libs/app.jar
for engine in tomcat reactor-netty jetty undertow
do
  ./gradlew clean build -x test -Pserver.type=$engine
  if [[ ! -f "${app_jar_file}" ]];
  then
    echo "${app_jar_file} does not exist"
    exit 1;
  fi;
  cp "${app_jar_file}" "${cwd}/tmp/${engine}.jar"
done;

cat <<EOF | docker build --no-cache -t starter-java:latest "${cwd}" -f -
FROM openjdk:11.0.6-jdk
RUN mkdir -p /opt/app
COPY ./tmp/ /opt/app/
EXPOSE 8080
ENV ENGINE "reactor-netty"
RUN echo "java -Xms512m -Xmx512m -jar /opt/app/\${ENGINE}.jar"| tee /opt/app/startup.sh
RUN chmod a+x /opt/app/startup.sh
CMD ["sh", "-c", "/opt/app/startup.sh"]

EOF
