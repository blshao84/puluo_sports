#!/bin/sh
PROD_BIN="/data/mall/mall.prod"
rm -rf $PROD_BIN
echo $PROD_BIN" is deleted"
ln -s $1 $PROD_BIN
echo $PROD_BIN" is pointing to "$1

