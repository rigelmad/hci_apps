#!/bin/bash

set -e # Exit on any errors

if [[ $# -eq 0 ]] ; then
	echo 'ERROR: Run with path to app'
	exit 0
fi

ROOT_PATH=$(cd $1 && pwd)
APP_NAME=$(echo $ROOT_PATH | sed 's/\/Users\/Rigel\/Documents\/College\/Fall_2019\/HCI\/hci_apps\///')
UNSIGNED_APK_PATH=$ROOT_PATH/platforms/android/app/build/outputs/apk/release/app-release-unsigned.apk

cd $ROOT_PATH

ionic cordova build --release android

rm -rf keys

mkdir keys

keytool -genkey -v -keystore keys/my-release-key.keystore -alias $APP_NAME-key -keyalg RSA -keysize 2048 -validity 10000

jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore keys/my-release-key.keystore $UNSIGNED_APK_PATH $APP_NAME-key

rm -rf distro

mkdir distro

/Users/Rigel/Library/Android/sdk/build-tools/29.0.2/zipalign -v 4 $UNSIGNED_APK_PATH distro/$APP_NAME.apk
