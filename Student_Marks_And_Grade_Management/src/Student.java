import java.util.*;
public class Student {
private static int nextId = 1;
private final int id;
private final String name;
private final String classSection;
private final String password;
private final Map<String, AcademicRecord> records = new LinkedHashMap<>();
public Student(String name, String classSection, String password) {
    this.id = nextId++;
    this.name = name;
    this.classSection = classSection;
    this.password = password;
}
public int getId() {
    return id;
}
public String getName() {
    return name;
}
public String getClassSection() {
    return classSection;
}
public boolean authenticate(String password) {
    return this.password.equals(password);
}
public void enrollSubject(Subject subject) {
    records.putIfAbsent(subject.getName(), new AcademicRecord(subject));
}
public AcademicRecord getRecord(String subjectName) {
    return records.get(subjectName);
}
public Collection<AcademicRecord> getAcademicRecords() {
    return records.values();
}
public boolean isEnrolledIn(String subjectName) {
    return records.containsKey(subjectName);
}
public void printMarks() {
    System.out.printf("Marks for %s (%s)\n", name, classSection);
    System.out.printf("%-15s %-8s %-8s %-10s\n", "Subject", "Marks", "Grade", "Status");
    for (AcademicRecord record : records.values()) {
        String marksDisplay = record.getMarksDisplay();
        Grade grade = record.getGrade();
        String gradeCode = grade == null ? "N/A" : grade.getCode();
        String status = record.hasEntry() ? (record.isPass() ? "Pass" : "Fail") : "N/A";
        System.out.printf("%-15s %-8s %-8s %-10s\n", record.getSubject().getName(), marksDisplay, gradeCode, status);
    }
}
}
