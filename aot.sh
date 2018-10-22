#!/usr/bin/env bash

native-image -cp ./target/gen.jar -H:Name=target/gen -H:ReflectionConfigurationFiles=./target/cli-reflect.json -H:+ReportUnsupportedElementsAtRuntime --no-server com.gxk.gen.Gen
