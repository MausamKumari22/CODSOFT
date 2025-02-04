import java.util.ArrayList;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolledStudents;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    public String getCourseDetails() {
        return courseCode + " - " + title + " | Slots Available: " + getAvailableSlots();
    }
}

class Student {
    private String studentID;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public void registerCourse(Course course) {
        if (course.enrollStudent()) {
            registeredCourses.add(course);
            System.out.println("Successfully registered for: " + course.getTitle());
        } else {
            System.out.println("Course is full.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            System.out.println("Successfully dropped: " + course.getTitle());
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses for " + name + ":");
        for (Course course : registeredCourses) {
            System.out.println(course.getCourseDetails());
        }
    }
}

class RegistrationSystem {
    private ArrayList<Course> courses;
    private ArrayList<Student> students;

    public RegistrationSystem() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayAvailableCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println(course.getCourseDetails());
        }
    }

    public Student getStudentByID(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    public Course getCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Student Course Registration System ---");
            System.out.println("1. Display Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    displayAvailableCourses();
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentID = scanner.nextLine();
                    Student student = getStudentByID(studentID);
                    if (student == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    displayAvailableCourses();
                    System.out.print("Enter Course Code to Register: ");
                    String courseCode = scanner.nextLine();
                    Course courseToRegister = getCourseByCode(courseCode);
                    if (courseToRegister == null) {
                        System.out.println("Invalid course code.");
                    } else {
                        student.registerCourse(courseToRegister);
                    }
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    studentID = scanner.nextLine();
                    student = getStudentByID(studentID);
                    if (student == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    student.displayRegisteredCourses();
                    System.out.print("Enter Course Code to Drop: ");
                    String courseToDropCode = scanner.nextLine();
                    Course courseToDrop = getCourseByCode(courseToDropCode);
                    if (courseToDrop == null) {
                        System.out.println("Invalid course code.");
                    } else {
                        student.dropCourse(courseToDrop);
                    }
                    break;
                case 4:
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RegistrationSystem system = new RegistrationSystem();

        // Adding sample courses
        system.addCourse(new Course("CS101", "Intro to Programming", "Learn basic programming concepts.", 30,
                "Mon-Wed 10:00 AM"));
        system.addCourse(
                new Course("CS102", "Data Structures", "Learn data structures and algorithms.", 25, "Tue-Thu 2:00 PM"));

        // Adding sample students
        system.addStudent(new Student("S001", "Alice"));
        system.addStudent(new Student("S002", "Bob"));

        // Start the system
        system.start();
    }
}