package kazusato.myapp.tools.db.model;

import java.time.LocalDateTime;

public class ReadingHistory {

    public ReadingHistory() {
    }

    public ReadingHistory(String key, String user, String title, LocalDateTime completeDateTime) {
        this.key = key;
        this.user = user;
        this.title = title;
        this.completeDateTime = completeDateTime;
    }

    private String key;

    private String user;

    private String title;

    private LocalDateTime completeDateTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCompleteDateTime() {
        return completeDateTime;
    }

    public void setCompleteDateTime(LocalDateTime completeDateTime) {
        this.completeDateTime = completeDateTime;
    }

    @Override
    public String toString() {
        return "ReadingHistory{" +
                "key='" + key + '\'' +
                ", user='" + user + '\'' +
                ", title='" + title + '\'' +
                ", completeDateTime=" + completeDateTime +
                '}';
    }
}
