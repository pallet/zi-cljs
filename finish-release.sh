#!/bin/bash

# finish the release after updating release notes

if [[ $# -lt 1 ]]; then
  echo "usage: $(basename $0) new-version" >&2
  exit 1
fi

version=$1

echo "finish release of $version"

echo -n "commiting release notes and readme.  enter to continue:" && read x \
&& git add ReleaseNotes.md README.md \
&& git commit -m "Updated release notes and readme for $version" \
&& echo -n "Peform release.  enter to continue:" && read x \
&& mvn release:clean \
&& mvn release:prepare \
&& mvn release:perform \
&& mvn nexus:staging-close \
&& mvn nexus:staging-promote \
&& git flow release finish -n $version

