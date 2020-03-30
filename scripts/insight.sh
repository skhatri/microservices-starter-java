#!/bin/bash
module=$1
project=$2
if [[ -z ${project} ]];
then
  project="app"
fi;

if [[ $module ]];
then
    cwd=$(dirname $0)
    gradle -b ${cwd}/../build.gradle.kts :${project}:dependencyInsight --dependency ${module} --configuration testCompileClasspath
fi;
