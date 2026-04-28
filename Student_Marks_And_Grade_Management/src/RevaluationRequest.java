import java.time.*;
//import java.time.format.DateTimeFormatter;
public class RevaluationRequest {
    private final Student student;
    private final String subjectName;
    private final String requestedBy;
    private final LocalDateTime requestedAt;
    private boolean pending;
    private String resolvedBy;
    private String resolutionComment;
    public RevaluationRequest(Student student, String subjectName, String requestedBy) {
        this.student = student;
        this.subjectName = subjectName;
        this.requestedBy = requestedBy;
        this.requestedAt = LocalDateTime.now();
        this.pending = true;
    }
    public Student getStudent() {
        return student;
    }
    public String getSubjectName() {
        return subjectName;
    }    public String getStatus() {
        return pending ? "Pending" : "Resolved";
    }
    public boolean isPending() {
        return pending;
    }
    public void resolve(String resolvedBy, String comment) {
        this.resolvedBy = resolvedBy;
        this.resolutionComment = comment;
        this.pending = false;
    }
    @Override
    public String toString() {
        String base = String.format("%s requested revaluation for %s in %s at %s", requestedBy, student.getName(), subjectName, requestedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (!pending) {
            base += String.format(" [Resolved by %s: %s]", resolvedBy, resolutionComment);
        }
        return base;
    }
}
