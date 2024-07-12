//Q5.. STUDENT COURSE REGISTRATION SYSTEM
// Course Database: Store course information, including course code, title,description, capacity, and schedule
// Student Database: Store student information, including student ID, name, and registered courses.
// Course Listing: Display available courses with details and available slots.
// Student Registration: Allow students to register for courses from the available options
// Course Removal: Enable students to drop courses they have registered for.

import java.util.ArrayList;
import java.util.List;

// Student class
class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
        course.addStudent(this);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
        course.removeStudent(this);
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }
}

// Course class
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private List<Student> studentsEnrolled;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.studentsEnrolled = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public void addStudent(Student student) {
        if (studentsEnrolled.size() < capacity) {
            studentsEnrolled.add(student);
        } else {
            System.out.println("Course capacity reached for " + title);
        }
    }

    public void removeStudent(Student student) {
        studentsEnrolled.remove(student);
    }

    public int getAvailableSlots() {
        return capacity - studentsEnrolled.size();
    }

    public List<Student> getStudents() {
        return studentsEnrolled;
    }

    public String getCourseDetails() {
        return String.format("Course Code: %s, Title: %s, Description: %s, Capacity: %d, Schedule: %s, Available Slots: %d",
                courseCode, title, description, capacity, schedule, getAvailableSlots());
    }
}

// RegistrationSystem class
class RegistrationSystem {
    private List<Student> students;
    private List<Course> courses;

    public RegistrationSystem() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public void registerStudent(String id, String name) {
        students.add(new Student(id, name));
    }

    public void registerCourse(String courseCode, String title, String description, int capacity, String schedule) {
        courses.add(new Course(courseCode, title, description, capacity, schedule));
    }

    public void enrollStudentInCourse(String studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);
        if (student != null && course != null && course.getAvailableSlots() > 0) {
            student.registerCourse(course);
        } else {
            System.out.println("Enrollment failed. Either student or course not found, or course is full.");
        }
    }

    public void dropStudentFromCourse(String studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);
        if (student != null && course != null) {
            student.dropCourse(course);
        }
    }

    public List<Course> listAvailableCourses() {
        List<Course> availableCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getAvailableSlots() > 0) {
                availableCourses.add(course);
            }
        }
        return availableCourses;
    }

    private Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private Course findCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(code)) {
                return course;
            }
        }
        return null;
    }
}

// Main class to test the system
public class Main {
    public static void main(String[] args) {
        RegistrationSystem system = new RegistrationSystem();
        
        // Register students
        system.registerStudent("1", "Alice");
        system.registerStudent("2", "Bob");
        
        // Register courses
        system.registerCourse("CS101", "Intro to Computer Science", "Basic concepts of computer science", 2, "MWF 10-11 AM");
        system.registerCourse("MATH101", "Calculus I", "Introduction to calculus", 2, "TTh 9-10:30 AM");
        
        // Enroll students in courses
        system.enrollStudentInCourse("1", "CS101");
        system.enrollStudentInCourse("2", "MATH101");
        system.enrollStudentInCourse("1", "MATH101");
        
        // List available courses
        System.out.println("Available Courses:");
        for (Course course : system.listAvailableCourses()) {
            System.out.println(course.getCourseDetails());
        }
        
        // Drop a student from a course
        system.dropStudentFromCourse("1", "MATH101");
        
        // List available courses again
        System.out.println("\nAfter dropping a student from MATH101:");
        for (Course course : system.listAvailableCourses()) {
            System.out.println(course.getCourseDetails());
        }
    }
}
