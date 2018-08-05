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

echo "-- step0 rm bin delploy local"
rm /usr/local/bin/gen.jar
rm /usr/local/bin/gg
echo "-- step0 success"

echo "-- step2 rm resource deploy local"
rm -rf $HOME/.gen
echo "-- step2 success"

popd
