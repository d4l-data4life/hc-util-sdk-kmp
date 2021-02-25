#!/bin/sh

./gradlew --write-verification-metadata pgp,sha512 --export-keys resolveAndLockAll --write-locks
