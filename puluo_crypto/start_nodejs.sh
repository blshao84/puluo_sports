#!/bin/sh
nohup node crypto-server.js > ./log/crypto-server.log 2>&1 & echo $! > run.pid

