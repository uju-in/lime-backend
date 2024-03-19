# Dockerfile

# openjdk 17 버전을 사용
FROM openjdk:17

# JAR_FILE 변수에 jar 파일명을 저장
ARG JAR_FILE=lime-api/build/libs/*.jar

# 환경변수로 시간대 설정
ENV TZ=Asia/Seoul

# jar 파일 복사
COPY ${JAR_FILE} app.jar

# jar 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]