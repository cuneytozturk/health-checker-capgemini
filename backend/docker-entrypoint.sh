#!/bin/sh
mvn test -o || exit 1
while inotifywait -r -e modify /app/src/; 
do 
  echo "The code changes..."
  mvn test -o && mvn compile -o
done >/dev/null 2>&1 &

mvn spring-boot:run