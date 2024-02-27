#!/usr/bin/env bash

set -e

echo "Creating database tables ..."

psql -d postgres -U admin -f /var/postgres/create-tables.sql

echo "Creating database categories ..."

psql -d postgres -U admin -f /var/postgres/insert-data.sql