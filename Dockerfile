FROM gcr.io/distroless/java-debian11:11

COPY app/build/libs/app.jar /opt/app/
EXPOSE 8080
CMD ["-Dflags.use.ssl=false", "-Dflags.use.http2=true", "-jar", "/opt/app/app.jar"]

