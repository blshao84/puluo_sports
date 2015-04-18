#!/bin/sh
echo 'stopping nodejs'
kill -9 `cat run.pid`
echo 'removing pid'
rm -rf run.pid
LOG='crypto-server.'`date +%s`'.log'
echo "rename log to $LOG"
mv ./log/crypto-server.log ./log/$LOG
