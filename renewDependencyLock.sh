#!/bin/sh

if [ "$1" = "clean" ]
then
  rm gradle.lockfile
  rm gradle/verification-keyring.gpg
  rm gradle/verification-metadata.xml
  rm util/gradle.lockfile
fi

./gradlew --write-verification-metadata pgp,sha256,sha512 --export-keys resolveAndLockAll --write-locks
