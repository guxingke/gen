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

echo "-- step0 package"

if [ ! -f target/gen.jar ]; then
    echo 'something bad'
    echo "-- step1 failed"
    exit -1
fi
echo "-- step0 success"


echo "-- step1 bin deploy local"
cp target/gen.jar /usr/local/bin/
cp bin/gen.sh /usr/local/bin/gg
echo "-- step1 success"


echo "-- step2 resource deploy local"
if [ -d $HOME/.gen ]; then
 echo "$HOME/.gen existed, check it , bak it, and remove it, rerun install.sh"
 exit -2
fi

mkdir $HOME/.gen
mkdir $HOME/.gen/config
mkdir $HOME/.gen/scaffold

cp -r scaffold/* $HOME/.gen/scaffold

echo "scaffold_dir = [\"$HOME/.gen/scaffold/\"]" > $HOME/.gen/config/gen.toml
echo "-- step2 success"

popd