#!/bin/sh

./gradlew --write-verification-metadata pgp,sha256,sha512 --export-keys resolveAndLockAll --write-locks
