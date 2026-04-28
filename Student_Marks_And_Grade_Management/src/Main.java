import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    Admin admin = new Admin("Admin", "admin123");
    Teacher teacher = new Teacher("sharma", "teach123");
    admin.addTeacher(teacher);
    Subject math = new Subject("Mathematics", 100);
    Subject science = new Subject("Science", 100);
    Subject english = new Subject("English", 100);
    admin.addSubject(math);
    admin.addSubject(science);
    admin.addSubject(english);
    printHeader();
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.println("Select role:");
        System.out.println("1. Student");
        System.out.println("2. Teacher");
        System.out.println("3. Admin");
        System.out.println("4. Exit");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> studentMenu(scanner, admin);
            case "2" -> teacherMenu(scanner, admin);
            case "3" -> adminMenu(scanner, admin);
            case "4" -> {
                System.out.println("Exiting system. Goodbye!");
                scanner.close();
                return;
            }
            default -> System.out.println("Invalid choice. Please select 1-4.");
        }
        }
    }
    private static void printHeader() {
    System.out.println("Student Academic Management System");
    System.out.println("Record marks, compute grades, manage pass/fail, and generate reports.");
    System.out.println();
    }
    private static void studentMenu(Scanner scanner, Admin admin) {
        System.out.println("  Student Portal ");
        System.out.println("1. Login");
        System.out.println("2. Register new student");
        System.out.println("3. Back");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();
        Student student;
        if (choice.equals("2")) {
            student = registerStudent(scanner, admin);
            if (student == null) 
                return;
        } else if (choice.equals("1")) {
            student = loginStudent(scanner, admin);
            if (student == null) 
                return;
        } else
            return;
        while (true) {
            System.out.println("  Student Actions  ");
            System.out.println("1. Display marks");
            System.out.println("2. View report card");
            System.out.println("3. Request revaluation");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            String action = scanner.nextLine().trim();
            switch (action) {
                case "1" -> admin.printStudentMarks(student);
                case "2" -> admin.generateReportCard(student);
                case "3" -> requestRevaluation(scanner, admin, student);
                case "4" -> {
                    System.out.println("Student logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }
private static Student loginStudent(Scanner scanner, Admin admin) {
    System.out.print("Enter student name: ");
    String name = scanner.nextLine().trim();
    System.out.print("Enter password: ");
    String password = scanner.nextLine().trim();
    Student student = admin.authenticateStudent(name, password);
    if (student == null) {
        System.out.println("Login failed. Check name and password.");
    } else {
        System.out.println("Welcome, " + student.getName() + "!");
    }
    return student;
}
private static Student registerStudent(Scanner scanner, Admin admin) {
    System.out.print("Enter new student name: ");
    String name = scanner.nextLine().trim();
    System.out.print("Enter class/section: ");
    String classSection = scanner.nextLine().trim();
    System.out.print("Enter password: ");
    String password = scanner.nextLine().trim();
    Student existing = admin.findStudentByName(name);
    if (existing != null) {
        System.out.println("A student with that name already exists.");
        return null;
    }
    Student student = new Student(name, classSection, password);
    // Enroll student in all available subjects
    for (Subject subject : admin.getSubjects())
        student.enrollSubject(subject);
    admin.addStudent(student);
    System.out.println("Student registered successfully. Enrolled in all available subjects.");
    return student;
}
private static void requestRevaluation(Scanner scanner, Admin admin, Student student) {
    System.out.println("Enter subject name for revaluation:");
    String subjectName = scanner.nextLine().trim();
    AcademicRecord record = student.getRecord(subjectName);
    if (record == null) {
        System.out.println("You are not enrolled in " + subjectName + ".");
        return;
    }
    if (!record.hasEntry()) {
        System.out.println("No marks exist yet for " + subjectName + ".");
        return;
    }
    RevaluationRequest request = new RevaluationRequest(student, subjectName, student.getName());
    admin.addRevaluationRequest(request);
    System.out.println("Revaluation request submitted for " + subjectName + ".");
}
private static void teacherMenu(Scanner scanner, Admin admin) {
    System.out.println("--- Teacher Portal ---");
    System.out.print("Enter teacher name: ");
    String name = scanner.nextLine().trim();
    System.out.print("Enter password: ");
    String password = scanner.nextLine().trim();
    Teacher teacher = admin.authenticateTeacher(name, password);
    if (teacher == null) {
        System.out.println("Teacher login failed.");
        return;
    }
    System.out.println("Welcome, " + teacher.getName() + "!");
    while (true) {
    System.out.println("  Teacher Actions  ");
    System.out.println("1. View pending revaluation requests");
    System.out.println("2. Assign marks by class and subject");
    System.out.println("3. Logout");
    System.out.print("Enter choice: ");
    String action = scanner.nextLine().trim();
    switch (action) {
        case "1" -> processRevaluationRequests(scanner, admin, teacher);
        case "2" -> assignMarksByClass(scanner, admin, teacher);
        case "3" -> {
            System.out.println("Teacher logged out.");
            return;
        }
        default -> System.out.println("Invalid choice. Please select 1-3.");
    }
    }
}
    private static void processRevaluationRequests(Scanner scanner, Admin admin, Teacher teacher) {
        List<RevaluationRequest> pending = admin.getPendingRevaluationRequests();
        if (pending.isEmpty()) {
            System.out.println("No pending revaluation requests.");
            return;
        }
        System.out.println("Pending Revaluation Requests:");
        for (int i = 0; i < pending.size(); i++) {
            RevaluationRequest request = pending.get(i);
            System.out.printf("%d. %s - %s (%s)\n", i + 1, request.getStudent().getName(), request.getSubjectName(), request.getStatus());
        }
        System.out.print("Select request number to resolve or 0 to cancel: ");
        String input = scanner.nextLine().trim();
        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        if (index <= 0 || index > pending.size()) {
            System.out.println("Cancelled.");
            return;
        }
        RevaluationRequest request = pending.get(index - 1);
        System.out.print("Enter new marks for " + request.getStudent().getName() + " in " + request.getSubjectName() + ": ");
        String marksInput = scanner.nextLine().trim();
        try {
            int marks = Integer.parseInt(marksInput);
            teacher.handleRevaluationRequest(request, marks);
        } catch (NumberFormatException e) {
            System.out.println("Invalid marks value.");
        }
    }
    private static void assignMarksByClass(Scanner scanner, Admin admin, Teacher teacher) {
        System.out.print("Enter class/section: ");
        String classSection = scanner.nextLine().trim();
        List<Student> studentsInClass = admin.findStudentsByClass(classSection);
        if (studentsInClass.isEmpty()) {
            System.out.println("No students found in class " + classSection + ".");
            return;
        }
        System.out.print("Enter subject name: ");
        String subjectName = scanner.nextLine().trim();
        Subject subject = admin.findSubjectByName(subjectName);
        if (subject == null) {
            System.out.println("Subject not found: " + subjectName + ".");
            return;
        }
        for (Student student : studentsInClass) {
            if (!student.isEnrolledIn(subjectName)) {
                System.out.println(student.getName() + " is not enrolled in " + subjectName + ". Skipping.");
                continue;
            }
            System.out.print("Enter marks for " + student.getName() + " or AB for absent: ");
            String marksInput = scanner.nextLine().trim();
            if (marksInput.equalsIgnoreCase("AB")) {
                teacher.markAbsent(student, subjectName);
            } else {
                try {
                    int marks = Integer.parseInt(marksInput);
                    teacher.enterMarks(student, subjectName, marks);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid marks input for " + student.getName() + ". Skipping.");
                }
            }
        }
    }
    private static void adminMenu(Scanner scanner, Admin admin) {
        System.out.println("  Admin Portal  ");
        System.out.print("Enter admin name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        if (!admin.authenticateAdmin(name, password)) {
            System.out.println("Admin login failed.");
            return;
        }
        System.out.println("Welcome, " + name + "!");
        while (true) {
        System.out.println("  Admin Actions ");
        System.out.println("1. Generate report card for student");
        System.out.println("2. Generate all report cards");
        System.out.println("3. View pending revaluation requests");
        System.out.println("4. Logout");
        System.out.print("Enter choice: ");
        String action = scanner.nextLine().trim();
            switch (action) {
                case "1" -> generateStudentReport(scanner, admin);
                case "2" -> admin.generateAllReportCards();
                case "3" -> printPendingRevaluationRequests(admin);
                case "4" -> {
                    System.out.println("Admin logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }
private static void generateStudentReport(Scanner scanner, Admin admin) {
    System.out.print("Enter student name or ID: ");
    String input = scanner.nextLine().trim();
    Student student = null;
    try {
        int id = Integer.parseInt(input);
        student = admin.findStudentById(id);
    } catch (NumberFormatException ignored) {
        student = admin.findStudentByName(input);
    }
    if (student == null) {
        System.out.println("Student not found.");
        return;
    }
    admin.generateReportCard(student);
}
    private static void printPendingRevaluationRequests(Admin admin) {
        List<RevaluationRequest> pending = admin.getPendingRevaluationRequests();
        if (pending.isEmpty()) {
            System.out.println("No pending revaluation requests.");
            return;
        }
        System.out.println("Pending revaluation requests:");
        for (RevaluationRequest request : pending) {
            System.out.println(request);
        }
    }
}
