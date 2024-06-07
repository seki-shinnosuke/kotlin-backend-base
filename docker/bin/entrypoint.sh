#!/bin/sh

exec java -XshowSettings:vm $JAVA_OPTS -jar /app/app.jar -Duser.language=ja -Duser.country=JP -Duser.timezone=Asia/Tokyo $@