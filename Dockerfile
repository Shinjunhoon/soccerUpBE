# Dockerfile

# Java 버전 설정: Corretto 17을 사용하므로 openjdk:17-jdk-slim을 사용합니다.
FROM openjdk:17-jdk-slim

# 앱 파일이 컨테이너 안에서 저장될 곳을 /app으로 설정합니다.
WORKDIR /app

# 당신의 JAR 파일을 컨테이너 안으로 복사합니다.
# "careercubeBackend.jar"는 IntelliJ 빌드 시 자동으로 생성되는 파일명 중 하나일 가능성이 높습니다.
# 만약 빌드 후 out/production/careercubeBackend 폴더에 다른 이름의 .jar 파일이 있다면
# 그 이름으로 바꿔주세요. 예를 들어, careercubeBackend-0.0.1-SNAPSHOT.jar 같은 형태일 수 있습니다.
# 확실하지 않다면, 빌드 후 out/production/careercubeBackend 폴더 내용을 직접 확인해보는 것이 좋습니다.
COPY app.jar app.jar

# 앱을 실행하는 명령어
ENTRYPOINT ["java","-jar","app.jar"]

# 앱이 외부에서 통신할 8080 포트를 지정합니다.
EXPOSE 8080