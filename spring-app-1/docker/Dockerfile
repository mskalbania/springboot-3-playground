FROM ghcr.io/graalvm/graalvm-community:21 AS build

WORKDIR /usr/src/app

COPY . .

RUN ./gradlew nativeCompile

FROM debian:bookworm-slim

WORKDIR /app

COPY --from=build /usr/src/app/build/native/nativeCompile/spring-app-1 /app/spring-app-1

CMD ["/app/spring-app-1"]