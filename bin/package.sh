#!/usr/bin/env bash

set -e

WORK_DIR=$(pwd)

BASE_DIR=$(cd $(dirname $0); cd ..; pwd)
echo "-------------------"
echo "base dir: $BASE_DIR"
echo "work dir: $WORK_DIR"
echo "user dir: $HOME"
echo "-------------------"

pushd $BASE_DIR

mvn -q clean && mvn -q package -DskipTests

popd
