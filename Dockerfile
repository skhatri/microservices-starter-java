FROM kubesailmaker/microservices-java:1.4.6-alpine 
COPY app/build/libs/app.jar /opt/app/app.jar
CMD ["java", "-Dflags.use.ssl=false", "-Dflags.use.http2=true", "-jar", "/opt/app/app.jar"]
