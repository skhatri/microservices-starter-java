#!/bin/bash

server="undertow"
if [[ $# -gt 0 ]];
then
  server=$1
fi;
cwd=$(dirname $0)
echo running ${server}
gradle -b ${cwd}/../build.gradle.kts runApp -Pserver.type=${server}
