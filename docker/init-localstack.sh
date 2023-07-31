#!/bin/sh -e

awslocal s3 mb s3://absensi-app
awslocal ses verify-email-identity --email-address test@absensi.id