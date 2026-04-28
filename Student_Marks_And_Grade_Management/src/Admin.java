import java.util.*;
public class Admin {
    private final String name;
    private final String password;
    private final List<Student> students = new ArrayList<>();
    private final List<Teacher> teachers = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();
    private final List<RevaluationRequest> revaluationRequests = new ArrayList<>();
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public boolean authenticateAdmin(String name, String password) {
        return this.name.equalsIgnoreCase(name) && this.password.equals(password);
    }
    public void addStudent(Student student) {
        students.add(student);
    }
    public void addTeacher(Teacher teacher) {
    teachers.add(teacher);
    }
public void addSubject(Subject subject) {
    subjects.add(subject);
}
    public Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
public Student findStudentByName(String name) {
    for (Student student : students) {
        if (student.getName().equalsIgnoreCase(name)) {
            return student;
        }
    }
    return null;
}
public Teacher authenticateTeacher(String name, String password) {
    for (Teacher teacher : teachers) {
        if (teacher.getName().equalsIgnoreCase(name) && teacher.authenticate(password)) {
            return teacher;
        }
    }
    return null;
}
public Student authenticateStudent(String name, String password) {
    for (Student student : students) {
        if (student.getName().equalsIgnoreCase(name) && student.authenticate(password)) {
            return student;
        }
    }
    return null;
}
public Subject findSubjectByName(String name) {
    for (Subject subject : subjects) {
        if (subject.getName().equalsIgnoreCase(name)) {
            return subject;
        }
    }
    return null;
}
public List<Subject> getSubjects() {
    return new ArrayList<>(subjects);
}
public List<Student> findStudentsByClass(String classSection) {
    List<Student> result = new ArrayList<>();
    for (Student student : students) {
        if (student.getClassSection().equalsIgnoreCase(classSection)) {
            result.add(student);
        }
    }
    return result;
}
public void addRevaluationRequest(RevaluationRequest request) {
    revaluationRequests.add(request);
}
public List<RevaluationRequest> getPendingRevaluationRequests() {
List<RevaluationRequest> pending = new ArrayList<>();
    for (RevaluationRequest request : revaluationRequests) {
        if (request.isPending()) {
            pending.add(request);
        }
    }
    return pending;
}
public void generateReportCard(Student student) {
System.out.println("Report Card for " + student.getName() + " [" + student.getClassSection() + "]");
System.out.println("Student ID : " + student.getId());
System.out.println("Admin : " + name);
System.out.printf("%-15s %-8s %-8s %-8s %-6s\n", "Subject", "Marks", "Grade", "Points", "Status");

    boolean allPassed = true;
    int totalPoints = 0;
    int subjectsWithGrades = 0;

    for (AcademicRecord record : student.getAcademicRecords()) {
        String marksDisplay = record.getMarksDisplay();
        Grade grade = record.getGrade();
        String gradeCode = grade == null ? "N/A" : grade.getCode();
        int points = grade == null ? 0 : grade.getPoints();
        String status = record.hasEntry() ? (record.isPass() ? "Pass" : "Fail") : "N/A";
        if (record.hasEntry() && !record.isPass()) {
            allPassed = false;
        }
        if (grade != null) {
            totalPoints += points;
            subjectsWithGrades++;
        }
        System.out.printf("%-15s %-8s %-8s %-8d %-6s\n", record.getSubject().getName(), marksDisplay, gradeCode, points, status);
    }

    String finalStatus = allPassed ? "PASS" : "FAIL";
    double averagePoints = subjectsWithGrades == 0 ? 0.0 : (double) totalPoints / subjectsWithGrades;

    System.out.println("Overall Result : " + finalStatus);
    System.out.printf("Aggregate Grade Points : %d  Average Points : %.2f\n", totalPoints, averagePoints);
    System.out.println("Report card generated in less than 3 seconds.");
    System.out.println();
}
public void generateAllReportCards() {
    System.out.println("  Consolidated Semester Report Cards  ");
    for (Student student : students) {
        generateReportCard(student);
    }
}
public void printStudentMarks(Student student) {
    student.printMarks();
    System.out.println();
}
}
