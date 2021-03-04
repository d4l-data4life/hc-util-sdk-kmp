#!/bin/sh

./gradlew --write-verification-metadata pgp,sha256 --export-keys resolveAndLockAll --write-locks
