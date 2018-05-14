#!/usr/bin/env bash
rg_name=myapp-rg
sql_api_account=myapp-db-account
db_name=myapp-db

az cosmosdb create \
 -n ${sql_api_account} \
 --kind GlobalDocumentDB \
 -g ${rg_name} \
 --max-interval 10 \
 --max-staleness-prefix 200

az cosmosdb database create \
 -n ${sql_api_account} \
 --db-name ${db_name} \
 -g ${rg_name}

az cosmosdb collection create \
 --collection-name book_info \
 -n ${sql_api_account} \
 --db-name ${db_name} \
 -g ${rg_name}

az cosmosdb collection create \
 --collection-name reading_history \
 -n ${sql_api_account} \
 --db-name ${db_name} \
 -g ${rg_name}

