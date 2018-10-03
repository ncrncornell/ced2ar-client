#!/bin/sh

# TODO: temporarily using a custom mill launcher:
/home/brandon/workspace/mill/out/dev/launcher/dest/run --color false api.__.assembly \
  --sonatypeCreds bbarker:$SONATYPE_PASSWORD \
  --release true

#  --gpgPassphrase $GPG_PASSWORD \
