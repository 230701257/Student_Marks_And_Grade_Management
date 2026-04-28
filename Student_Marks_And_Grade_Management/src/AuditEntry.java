import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditEntry {
    private final LocalDateTime timestamp;
    private final String changedBy;
    private final String action;
    private final String beforeValue;
    private final String afterValue;
    public AuditEntry(String changedBy, String action, String beforeValue, String afterValue) {
        this.timestamp = LocalDateTime.now();
        this.changedBy = changedBy;
        this.action = action;
        this.beforeValue = beforeValue;
        this.afterValue = afterValue;
    }
    @Override
    public String toString() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | " + changedBy + " | " + action + " | " + beforeValue + " -> " + afterValue;
    }
}
