package kazusato.myapp.tools.db.client;

import com.microsoft.azure.documentdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class DocumentClientWrapper {

    private static final String ENDPOINT_URL_KEY = "kazusato.myapp.cosmosdb.url";

    private static final String AUTH_KEY_KEY = "kazusato.myapp.cosmosdb.authkey";

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentClientWrapper.class);

    private DocumentClient client;

    private Optional<String> currentDatabase = Optional.empty();

    private Optional<String> currentCollection = Optional.empty();

    public DocumentClientWrapper() {
        String endpointUrl = getEnvVarValue(ENDPOINT_URL_KEY);
        String authKey = getEnvVarValue(AUTH_KEY_KEY);

        client = new DocumentClient(endpointUrl, authKey, new ConnectionPolicy(), ConsistencyLevel.Session);
    }

    public void readDatabase(String databaseName) {
        try {
            currentDatabase = Optional.ofNullable(databaseName);
            client.readDatabase(String.format("/dbs/%s", currentDatabase.orElseThrow(
                    () -> new RuntimeException("Database name cannot be null."))), null);
            LOGGER.info(String.format("Reading database [%s] succeeded.", databaseName));
        } catch (DocumentClientException e) {
            throw new RuntimeException(String.format("Reading database [%s] failed.", databaseName), e);
        }
    }

    public void readCollection(String collectionName) {
        try {
            currentCollection = Optional.ofNullable(collectionName);
            client.readCollection(String.format("/dbs/%s/colls/%s",
                    currentDatabase.orElseThrow(() -> new IllegalStateException("Call readDatabase first.")),
                    currentCollection.orElseThrow(() -> new RuntimeException("Collection name cannot be null.")
                    )), null);
            LOGGER.info(String.format("Reading collection [%s] succeeded.", collectionName));
        } catch (DocumentClientException e) {
            throw new RuntimeException(String.format("Reading collection [%s] failed.", collectionName), e);
        }
    }

    public <T> void createDocument(T data, String key) {
        if (data == null) {
            throw new RuntimeException(String.format("Data for document is null [DB=%s, Collection=%s].",
                    currentDatabase, currentCollection));
        }

        if (key == null) {
            throw new RuntimeException(String.format("Document key cannot be null for [%s]",  data.toString()));
        } else if (key.isEmpty()) {
            throw new RuntimeException(String.format("Document key cannot be empty for [%s]",  data.toString()));
        }

        try {
            client.createDocument(String.format("/dbs/%s/colls/%s",
                    currentDatabase.orElseThrow(() -> new IllegalStateException("Call readDatabase first.")),
                    currentCollection.orElseThrow(() -> new IllegalStateException("Call readCollection first")),
                    key), data, new RequestOptions(), true);
            LOGGER.info(String.format("Document for [%s: %s] is created.",
                    data.getClass().getCanonicalName(), key));
        } catch (DocumentClientException e) {
            throw new RuntimeException(String.format("Creating document for [%s: %s] failed.",
                    data.getClass().getCanonicalName(), key), e);
        }
    }

    public void printAllData() {
        FeedOptions queryOptions = new FeedOptions();
        queryOptions.setPageSize(-1);
        queryOptions.setEnableCrossPartitionQuery(true);

        String collectionLink = String.format("/dbs/%s/colls/%s",
                currentDatabase.orElseThrow(() -> new IllegalStateException("Call readDatabase first.")),
                currentCollection.orElseThrow(() -> new IllegalStateException("Call readCollection first")));

        FeedResponse<Document> queryResults = client.queryDocuments(collectionLink,
                "select * from book_info", queryOptions);

        queryResults.getQueryIterable().forEach(d -> System.out.println(d));
    }

    private String getEnvVarValue(String key) {
        String envVarValue = System.getenv(key);
        if (envVarValue == null) {
            throw new RuntimeException (String.format("Environment variable [%s] is not set.", key));
        } else if (envVarValue.isEmpty()) {
            throw new RuntimeException(String.format("Environment variable [%s] is empty.", key));
        }
        return envVarValue;
    }

}
