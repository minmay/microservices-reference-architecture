# docker build -t company.suffix/product/component:latest -t company.suffix/product/component:0.0.1-SNAPSHOT .
# docker run -p 8080:8080 company.suffix/product/component:latest

FROM gradle:6.9.1-jdk11 as build
LABEL vendor="Company"
LABEL app="component"

ENV APP_DIR /opt/company/product/component/
ENV GRADLE_OPTS -Dorg.gradle.daemon=false
ENV JVM_OPTIONS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

WORKDIR $APP_DIR
ADD build.gradle $APP_DIR/
ADD settings.gradle $APP_DIR/
RUN mkdir -p $APP_DIR/src/main
ADD src/main $APP_DIR/src/main
RUN gradle build

FROM azul/zulu-openjdk:11 AS deployment

EXPOSE 8080
EXPOSE 5005
ENV SERVER_PORT 8080
ENV APP_DIR /opt/company/product/component/
WORKDIR $APP_DIR

COPY --from=build $APP_DIR/build/libs/component.jar $APP_DIR/build/libs/
ADD run.sh $APP_DIR/
RUN chmod 755 $APP_DIR/run.sh

ENTRYPOINT ["/bin/bash"]
CMD ["./run.sh"]