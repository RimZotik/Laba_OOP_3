package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final GenericDAO<Teacher, Long> teacherDAO = new GenericDAO<>(Teacher.class);
    private static final GenericDAO<Course, Long> courseDAO = new GenericDAO<>(Course.class);
    private static final GenericDAO<Student, Long> studentDAO = new GenericDAO<>(Student.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Добавить учителя");
            System.out.println("2. Добавить курс");
            System.out.println("3. Добавить студента");
            System.out.println("4. Изменить учителя");
            System.out.println("5. Изменить курс");
            System.out.println("6. Изменить студента");
            System.out.println("7. Удалить учителя");
            System.out.println("8. Удалить курс");
            System.out.println("9. Удалить студента");
            System.out.println("10. Показать всех учителей");
            System.out.println("11. Показать все курсы");
            System.out.println("12. Показать всех студентов");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTeacher(scanner);
                    break;
                case 2:
                    addCourse(scanner);
                    break;
                case 3:
                    addStudent(scanner);
                    break;
                case 4:
                    updateTeacher(scanner);
                    break;
                case 5:
                    updateCourse(scanner);
                    break;
                case 6:
                    updateStudent(scanner);
                    break;
                case 7:
                    deleteTeacher(scanner);
                    break;
                case 8:
                    deleteCourse(scanner);
                    break;
                case 9:
                    deleteStudent(scanner);
                    break;
                case 10:
                    showAllTeachers();
                    break;
                case 11:
                    showAllCourses();
                    break;
                case 12:
                    showAllStudents();
                    break;
                case 0:
                    System.out.println("Выход...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void addTeacher(Scanner scanner) {
        System.out.print("Введите имя учителя: ");
        String name = scanner.nextLine();
        Teacher teacher = new Teacher(name);
        teacherDAO.save(teacher);
        System.out.println("Учитель добавлен: " + name);
    }

    private static void addCourse(Scanner scanner) {
        List<Teacher> teachers = teacherDAO.findAll();
        System.out.println("Выберите учителя для курса:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getId() + ". " + teacher.getName());
        }
        System.out.print("Введите ID учителя: ");
        Long teacherId = scanner.nextLong();
        scanner.nextLine();

        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Учитель не найден.");
            return;
        }

        System.out.print("Введите название курса: ");
        String title = scanner.nextLine();

        Course course = new Course(title, teacher);
        courseDAO.save(course);
        System.out.println("Курс добавлен: " + title);
    }

    private static void addStudent(Scanner scanner) {
        List<Teacher> teachers = teacherDAO.findAll();
        System.out.println("Выберите учителя для студента:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getId() + ". " + teacher.getName());
        }
        System.out.print("Введите ID учителя: ");
        Long teacherId = scanner.nextLong();
        scanner.nextLine();

        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Учитель не найден.");
            return;
        }

        List<Course> courses = courseDAO.findAll();
        System.out.println("Выберите курс для студента:");
        for (Course course : courses) {
            System.out.println(course.getId() + ". " + course.getTitle());
        }
        System.out.print("Введите ID курса: ");
        Long courseId = scanner.nextLong();
        scanner.nextLine();

        Course course = courseDAO.findById(courseId);
        if (course == null) {
            System.out.println("Курс не найден.");
            return;
        }

        System.out.print("Введите имя студента: ");
        String name = scanner.nextLine();

        Student student = new Student(name, teacher, course);
        studentDAO.save(student);
        System.out.println("Студент добавлен: " + name);
    }

    private static void updateTeacher(Scanner scanner) {
        showAllTeachers();  // Выводим список всех учителей перед изменением
        System.out.print("Введите ID учителя для изменения: ");
        Long teacherId = scanner.nextLong();
        scanner.nextLine();
        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Учитель не найден.");
            return;
        }

        System.out.print("Введите новое имя учителя: ");
        String newName = scanner.nextLine();
        teacher.setName(newName);
        teacherDAO.update(teacher);
        System.out.println("Учитель обновлен: " + newName);
    }


    private static void updateCourse(Scanner scanner) {
        showAllCourses();
        System.out.print("Введите ID курса для изменения: ");
        Long courseId = scanner.nextLong();
        scanner.nextLine();
        Course course = courseDAO.findById(courseId);
        if (course == null) {
            System.out.println("Курс не найден.");
            return;
        }

        List<Teacher> teachers = teacherDAO.findAll();
        System.out.println("Выберите нового учителя для курса:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getId() + ". " + teacher.getName());
        }
        System.out.print("Введите ID нового учителя: ");
        Long teacherId = scanner.nextLong();
        scanner.nextLine();

        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Учитель не найден.");
            return;
        }

        System.out.print("Введите новое название курса: ");
        String newTitle = scanner.nextLine();

        course.setTitle(newTitle);
        course.setTeacher(teacher);
        courseDAO.update(course);
        System.out.println("Курс обновлен: " + newTitle);
    }

    private static void updateStudent(Scanner scanner) {
        showAllStudents();
        System.out.print("Введите ID студента для изменения: ");
        Long studentId = scanner.nextLong();
        scanner.nextLine();
        Student student = studentDAO.findById(studentId);
        if (student == null) {
            System.out.println("Студент не найден.");
            return;
        }

        System.out.print("Введите новое имя студента: ");
        String newName = scanner.nextLine();
        student.setName(newName);

        List<Course> courses = courseDAO.findAll();
        System.out.println("Выберите новый курс для студента:");
        for (Course course : courses) {
            System.out.println(course.getId() + ". " + course.getTitle());
        }
        System.out.print("Введите ID нового курса: ");
        Long courseId = scanner.nextLong();
        scanner.nextLine();
        Course course = courseDAO.findById(courseId);
        if (course == null) {
            System.out.println("Курс не найден.");
            return;
        }
        student.setCourse(course);

        List<Teacher> teachers = teacherDAO.findAll();
        System.out.println("Выберите нового учителя для студента:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getId() + ". " + teacher.getName());
        }
        System.out.print("Введите ID нового учителя: ");
        Long teacherId = scanner.nextLong();
        scanner.nextLine();
        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Учитель не найден.");
            return;
        }
        student.setTeacher(teacher);

        studentDAO.update(student);
        System.out.println("Студент обновлен: " + newName);
    }

    private static void deleteTeacher(Scanner scanner) {
        System.out.print("Введите ID учителя для удаления: ");
        Long teacherId = scanner.nextLong();
        scanner.nextLine();
        Teacher teacher = teacherDAO.findById(teacherId);
        if (teacher == null) {
            System.out.println("Учитель не найден.");
            return;
        }

        if (!teacher.getCourses().isEmpty() || !teacher.getStudents().isEmpty()) {
            System.out.println("Невозможно удалить учителя, так как на него есть курсы или студенты.");
            return;
        }

        teacherDAO.delete(teacher);
        System.out.println("Учитель удален.");
    }

    private static void deleteCourse(Scanner scanner) {
        showAllCourses();  // Выводим список всех курсов перед удалением
        System.out.print("Введите ID курса для удаления: ");
        Long courseId = scanner.nextLong();
        scanner.nextLine();
        Course course = courseDAO.findById(courseId);
        if (course == null) {
            System.out.println("Курс не найден.");
            return;
        }

        // Проверяем, есть ли студенты, привязанные к курсу
        if (!course.getStudents().isEmpty()) {
            System.out.println("Невозможно удалить курс, так как на нем есть студенты.");
            return;
        }

        courseDAO.delete(course);
        System.out.println("Курс удален.");
    }


    private static void deleteStudent(Scanner scanner) {
        showAllStudents(); // Выводим список всех студентов перед удалением
        System.out.print("Введите ID студента для удаления: ");
        Long studentId = scanner.nextLong();
        scanner.nextLine();
        Student student = studentDAO.findById(studentId);
        if (student == null) {
            System.out.println("Студент не найден.");
            return;
        }

        studentDAO.delete(student);
        System.out.println("Студент удален.");
    }

    private static void showAllTeachers() {
        List<Teacher> teachers = teacherDAO.findAll();
        System.out.println("Все учителя:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getId() + ". " + teacher.getName());
        }
    }

    private static void showAllCourses() {
        List<Course> courses = courseDAO.findAll();
        System.out.println("Все курсы:");
        for (Course course : courses) {
            System.out.println(course.getId() + ". " + course.getTitle());
        }
    }

    private static void showAllStudents() {
        List<Student> students = studentDAO.findAll();
        System.out.println("Все студенты:");
        for (Student student : students) {
            System.out.println(student.getId() + ". " + student.getName());
        }
    }

}
