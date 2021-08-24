FROM openjdk:11.0.6-jdk
RUN mkdir -p /opt/app
COPY app/build/libs/app.jar /opt/app/
EXPOSE 8080
CMD ["java", "-Dflags.use.ssl=false", "-Dflags.use.http2=true", "-jar", "/opt/app/app.jar"]
