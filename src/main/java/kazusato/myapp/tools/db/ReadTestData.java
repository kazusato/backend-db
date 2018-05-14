package kazusato.myapp.tools.db;

import kazusato.myapp.tools.db.client.DocumentClientWrapper;

public class ReadTestData {

    public static void main(String[] args) {
        new ReadTestData().readTestData();
    }

    private void readTestData() {
        DocumentClientWrapper client = new DocumentClientWrapper();
        client.readDatabase("myapp-db");
        client.readCollection("book_info");

        client.printAllData();
    }

}
