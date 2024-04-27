#!/bin/sh

exec java -jar /app/app.jar $JAVA_OPTS -Duser.language=ja -Duser.country=JP -Duser.timezone=Asia/Tokyo $@