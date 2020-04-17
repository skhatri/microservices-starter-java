#!/bin/bash

C=$2
M=$3
T=$1

: "${C:=50}"
: "${M:=20}"
: "${T:=120}"

echo conn=$C, stream_per_conn=$M, duration=$T
cwd=$(dirname "${0}")

for engine in jetty undertow tomcat reactor-netty
do
    java -Xms512m -Xmx512m -jar "${cwd}/tmp/${engine}.jar" 2>&1 >> runtime.log &
    sleep 20
    pscount=$(pgrep -f "tmp/${engine}.jar"|wc -l)
    if [[ $pscount -ne 1 ]];
    then
        echo could not find "tmp/${engine.jar}"
        exit 1;
    fi;

    for x in {1..3};
    do
        h2load -c${C} -m${M} --duration=${T} --warm-up-time=5 https://localhost:8080/todo/search 2>&1 >> $engine.log
        sleep 20
    done;
    sleep 5 
    pkill -f "tmp/${engine}.jar"
    sleep 2;
done;

