FROM maven:3.8.1-openjdk-17-slim AS builder
WORKDIR /workspace/app

COPY . ./

RUN chmod +x mvnw
RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=builder ${DEPENDENCY}/org /app/org

ENTRYPOINT ["java","-cp","app:app/lib/*", "com.test.absensi.AbsensiApplication"]