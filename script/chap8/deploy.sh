#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=spring-blog-web

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"
git pull

echo "> Build..."
./gradlew build -x test

echo "> Move to $REPOSITORY"
cd $REPOSITORY

echo "> Copy build output"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
echo "> Current App PID: $CURRENT_PID"

if [ -z "$CURRENT_PID"]; then
  echo "> Current app is not running now"
else
  echo "> Killing $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploying new app"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar \
  -Dspring.config.location=classpath:/application.yaml,/home/ec2-uesr/app/application-oauth.yaml,/home/ec2-uesr/app/application-real.yaml,/home/ec2-uesr/app/application-real-db.yaml \
  -Dspring.profiles.active=real \
  $REPOSITORY/$JAR_NAME 2>&1 &