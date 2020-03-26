#!/bin/bash

cwd=$(dirname $0)
gradle -b ${cwd}/../build.gradle.kts load-testing:runTest -Psimulation=ListSimulation 2>&1 > build/load-test.log

