FROM kubesailmaker/microservices-java:ubi-java11-1.3
COPY app/build/libs/app.jar /opt/app/app.jar
CMD ["java", "-Dflags.use.ssl=false", "-Dflags.use.http2=true", "-jar", "/opt/app/app.jar"]
