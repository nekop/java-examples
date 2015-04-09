#!/bin/sh

TARGET_URL="http://localhost/100kb.dat"
CONCURRENCY=200
NUM_REQUESTS=10000
NIO_THREADS=20

echo "ab"
ab -c $CONCURRENCY -n $NUM_REQUESTS $TARGET_URL
echo "standard"
sh run.sh -c $CONCURRENCY -n $NUM_REQUESTS -w $NUM_REQUESTS $TARGET_URL
echo "netty-oio"
sh run.sh -j netty-oio -c $CONCURRENCY -n $NUM_REQUESTS -w $NUM_REQUESTS $TARGET_URL
echo "netty-nio"
sh run.sh -j netty-nio -t $NIO_THREADS -c $CONCURRENCY -n $NUM_REQUESTS -w $NUM_REQUESTS $TARGET_URL
