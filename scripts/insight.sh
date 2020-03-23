#!/bin/bash
module=$1
if [[ $module ]];
then
    cwd=$(dirname $0)
    gradle -b ${cwd}/../build.gradle.kts :app:dependencyInsight --dependency ${module} --configuration testCompileClasspath
fi;
