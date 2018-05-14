package kazusato.myapp.tools.db;

import kazusato.myapp.tools.db.client.DocumentClientWrapper;
import kazusato.myapp.tools.db.model.BookInfo;

public class InsertTestData {

    public static void main(String[] args) {
        new InsertTestData().insertTestData();
    }

    private void insertTestData() {
        DocumentClientWrapper client = new DocumentClientWrapper();
        client.readDatabase("myapp-db");
        client.readCollection("book_info");

        BookInfo book = new BookInfo("1", "吾輩は猫である", "夏目漱石", "岩波書店", 756);
        client.createDocument(book, book.getId());

        book = new BookInfo("2", "走れメロス", "太宰治", "新潮社", 432);
        client.createDocument(book, book.getId());

        book = new BookInfo("3", "蜘蛛の糸・杜子春", "芥川龍之介", "新潮社", 346);
        client.createDocument(book, book.getId());

        book = new BookInfo("4", "山椒大夫・高瀬舟", "森鴎外", "新潮社", 529);
        client.createDocument(book, book.getId());

        book = new BookInfo("5", "雪国", "川端康成", "新潮社", 389);
        client.createDocument(book, book.getId());
    }

}
