#!/bin/bash

# -----------------------------
# Nexus Configuration
# -----------------------------
NEXUS_BASE_URL="http://192.168.0.42:8081"
REPOSITORY="Maven-AS"
GROUP="com.project"
ARTIFACT="user-activity-processor"
USERNAME="admin"
PASSWORD='Intership@2026'

# 1. Fetch Metadata (Version & Time)
echo "Fetching metadata for latest $ARTIFACT..."

METADATA=$(curl -u "$USERNAME:$PASSWORD" -s -X GET \
  "$NEXUS_BASE_URL/service/rest/v1/search/assets?repository=$REPOSITORY&maven.groupId=$GROUP&maven.artifactId=$ARTIFACT&maven.extension=jar&sort=version&direction=asc")

# Extract fields using jq
VERSION=$(echo "$METADATA" | jq -r '.items[0].maven2.version // .items[0].version')
UPLOAD_TIME=$(echo "$METADATA" | jq -r '.items[0].lastModified // .items[0].blobCreated')

if [ "$VERSION" == "null" ] || [ -z "$VERSION" ]; then
  echo "ERROR: Could not find artifact metadata."
  exit 1
fi

echo "------------------------------------------"
echo "LATEST VERSION: $VERSION"
echo "UPLOADED ON:    $UPLOAD_TIME"
echo "------------------------------------------"

# 2. Download the File
OUTPUT_FILE="Calculator-${VERSION}.jar"
echo "Downloading $OUTPUT_FILE..."

curl -u "$USERNAME:$PASSWORD" -L \
  "$NEXUS_BASE_URL/service/rest/v1/search/assets/download?repository=$REPOSITORY&maven.groupId=$GROUP&maven.artifactId=$ARTIFACT&maven.extension=jar&sort=version&direction=asc" \
  -o "$OUTPUT_FILE"

if [ $? -eq 0 ] && [ -f "$OUTPUT_FILE" ]; then
  echo "SUCCESS: Saved to $OUTPUT_FILE"
else
  echo "FAILED: Download error."
fi
