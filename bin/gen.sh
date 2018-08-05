#!/usr/bin/env bash

set -e

WORK_DIR=$(pwd)

BASE_DIR=$(cd $(dirname $0); pwd)
echo "-------------------"
echo "base dir: $BASE_DIR"
echo "work dir: $WORK_DIR"
echo "-------------------"

if [ $# -gt 0 ] && [ "init" == $1 ]; 
then 
    if [ "/" != "${3:0:1}" ]
    then
       tdir="$WORK_DIR/$3" 
       java -jar $BASE_DIR/gen.jar $1 $2 $tdir
       exit 0
    fi
fi

java -jar $BASE_DIR/gen.jar $@
