import java.util.*;
public class AcademicRecord {
    private final Subject subject;
    private Integer marks;
    private Grade grade;
    private boolean absent;
    private final List<AuditEntry> auditTrail = new ArrayList<>();
    public AcademicRecord(Subject subject) {
        this.subject = subject;
        this.absent = false;
    }
    public Subject getSubject() {
        return subject;
    }
    public Integer getMarks() {
        return marks;
    }
    public int getMarksOrZero() {
        return marks == null ? 0 : marks;
    }
    public Grade getGrade() {
        return grade;
    }
    public boolean hasEntry() {
        return marks != null || absent;
    }
    public boolean isPass() {
        if (absent) {
            return false;
        }
        return grade != null && !grade.isFail();
    }
    public String getMarksDisplay() {
        if (absent) {
            return "AB";
        }
        return marks == null ? "N/A" : String.valueOf(marks);
    }
    public void enterMarks(int marks, String changedBy, String reason) {
        validateMarks(marks);
        if (hasEntry()) {
            throw new IllegalStateException("Duplicate entry for this exam record.");
        }
        this.marks = marks;
        this.absent = false;
        this.grade = Grade.fromScore(marks);
        auditTrail.add(new AuditEntry(changedBy, reason, "N/A", buildAuditValue()));
    }
    public void markAbsent(String changedBy) {
        if (hasEntry()) {
            throw new IllegalStateException("Record already exists and cannot be marked absent.");
        }
        this.absent = true;
        this.marks = null;
        this.grade = Grade.AB;
        auditTrail.add(new AuditEntry(changedBy, "Mark absent", "N/A", "AB"));
    }
    public void updateMarks(int marks, String changedBy, String reason) {
        validateMarks(marks);
        if (!hasEntry()) {
            throw new IllegalStateException("No existing record to update. Enter marks first.");
        }
        String before = buildAuditValue();
        this.marks = marks;
        this.absent = false;
        this.grade = Grade.fromScore(marks);
        auditTrail.add(new AuditEntry(changedBy, reason, before, buildAuditValue()));
    }
    public void addExtraCredit(int bonus, String changedBy) {
        if (!hasEntry() || absent) {
            throw new IllegalStateException("Cannot add extra credit unless marks are already entered.");
        }
        int newMarks = Math.min(subject.getMaxMarks(), marks + bonus);
        String before = buildAuditValue();
        this.marks = newMarks;
        this.grade = Grade.fromScore(newMarks);
        auditTrail.add(new AuditEntry(changedBy, "Extra credit", before, buildAuditValue()));
    }
    private void validateMarks(int marks) {
        if (marks < 0 || marks > subject.getMaxMarks()) {
            throw new IllegalArgumentException("Invalid marks value: " + marks + ". Must be between 0 and " + subject.getMaxMarks() + ".");
        }
    }
    private String buildAuditValue() {
        return (absent ? "AB" : marks) + " / " + subject.getMaxMarks() + " [" + (grade == null ? "N/A" : grade.getCode()) + "]";
    }
    public List<AuditEntry> getAuditTrail() {
        return Collections.unmodifiableList(auditTrail);
    }
}

