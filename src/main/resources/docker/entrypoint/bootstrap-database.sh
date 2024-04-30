#!/usr/bin/env bash

set -e

echo "Creating database tables ..."

psql -d postgres -U admin -f /var/postgres/create-tables.sql

echo "Creating database categories ..."

psql -d postgres -U admin -f /var/postgres/insert-data.sql

echo "Creating quartz schedulers ..."

psql -d postgres -U admin -f /var/postgres/create-quartz-tables.sql