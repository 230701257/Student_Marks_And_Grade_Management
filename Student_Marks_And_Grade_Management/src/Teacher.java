public class Teacher {
private final String name;
private final String password;
public Teacher(String name, String password) {
    this.name = name;
    this.password = password;
}
public String getName() {
    return name;
}
public boolean authenticate(String password) {
    return this.password.equals(password);
}
public void enterMarks(Student student, String subjectName, int marks) {
    AcademicRecord record = student.getRecord(subjectName);
    if (record == null) {
        System.out.println("Error: " + student.getName() + " is not enrolled in " + subjectName + ".");
        return;
    }

    if (record.hasEntry()) {
        record.updateMarks(marks, name, "Teacher update");
        System.out.println("Updated marks for " + student.getName() + " in " + subjectName + " to " + marks + ".");
    } else {
        record.enterMarks(marks, name, "Teacher entry");
        System.out.println("Entered marks for " + student.getName() + " in " + subjectName + " = " + marks + ".");
    }
}
public void markAbsent(Student student, String subjectName) {
    AcademicRecord record = student.getRecord(subjectName);
    if (record == null) {
        System.out.println("Error: " + student.getName() + " is not enrolled in " + subjectName + ".");
        return;
    }
    if (record.hasEntry()) {
        System.out.println("Cannot mark absent because the student record already exists.");
        return;
    }
    record.markAbsent(name);
    System.out.println("Marked absent for " + student.getName() + " in " + subjectName + ".");
}
public void handleRevaluationRequest(RevaluationRequest request, int newMarks) {
    Student student = request.getStudent();
    String subjectName = request.getSubjectName();
    AcademicRecord record = student.getRecord(subjectName);
    if (record == null || !record.hasEntry()) {
        System.out.println("Cannot process revaluation because no existing marks are available.");
        return;
    }
    record.updateMarks(newMarks, name, "Re-evaluation");
    request.resolve(name, "Marks updated to " + newMarks);
    System.out.println("Revaluation request completed for " + student.getName() + " in " + subjectName + ".");
}
}

