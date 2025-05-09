#!/bin/sh
while inotifywait -r -e modify /app/src/;
do
 echo "Changes detected, running tests and compiling..."
 mvn test && mvn compile -o;
done >/dev/null 2>&1 &

mvn test && mvn spring-boot:run