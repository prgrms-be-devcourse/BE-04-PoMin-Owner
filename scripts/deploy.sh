#!/usr/bin/env bash

PROJECT_NAME=spring-cicd
REPOSITORY=/home/ec2-user/code
PACKAGE=$REPOSITORY/build/libs/
JAR_NAME=$(ls -tr $PACKAGE | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$PACKAGE$JAR_NAME

echo $JAR_NAME
echo $JAR_PATH

cd $REPOSITORY

CURRENT_PID=$(pgrep -f $PROJECT_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다"
else
  echo "> 실행 중인 애플리케이션 종료 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 배포 - $JAR_PATH"
sudo nohup java -jar $JAR_PATH > /dev/null 2>&1 &
