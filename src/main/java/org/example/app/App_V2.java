package org.example.app;

import org.example.models.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.exit;

public class App_V2 {

    public static void main(String[] args) {

        String[] options = {"1- Просмотреть весь список курсов",
                "2- Добавить курс",
                "3- Изменить курс",
                "4- Удалить курс",
                "0- Выйти",
        };

        Scanner scanner = new Scanner(System.in);
        int option = 1;

        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {

            while (option!=0){
                printMenu(options);
                try {
                    option = scanner.nextInt();
                    switch (option){
                        case 1: viewTheCourses(sessionFactory.getCurrentSession()); break;
                        case 2: addCourse(sessionFactory.getCurrentSession()); break;
                        case 3: changeCourse(sessionFactory.getCurrentSession()); break;
                        case 4: deleteCourse(sessionFactory.getCurrentSession()); break;
                        case 0: exit(0);
                    }
                }
                catch (Exception ex){
                    System.out.println("Пожалуйста, введите число от 1 до 4 или 0");
                    scanner.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Вывод списка меню
     * @param options список меню состоящий из строк
     */
    public static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Выберите пункт меню : ");
    }

    //region Options

    /**
     * Просмотр всего списка курсов из базы данных
     * @param session объект Сессии
     */
    private static void viewTheCourses(Session session) {
        System.out.println("Вы выбрали пункт меню №1 "); //TODO предложить отмену выбранной операции
        System.out.println("Весь список курсов: ");

        // Начало транзакции для чтения данных из БД
        session.beginTransaction();

        String sql = "FROM Course ";

        List<Course> courses = session.createQuery(sql).list();

        for (Iterator<Course> it = courses.iterator(); it.hasNext();) {
            Course course = (Course) it.next();
            System.out.println(course);
        }
        // Закрытие сессии с сохранением
        session.getTransaction().commit();
    }

    /**
     * Добавление нового курса в БД
     * @param session объект Сессии
     */
    private static void addCourse(Session session) {
        System.out.println("Вы выбрали пункт меню №2 "); //TODO предложить отмену выбранной операции

        //TODO Обработать валидность получаемых данных
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название курса: ");
        String title = scanner.next();

        System.out.println("Введите продолжительность курса: ");
        int duration = scanner.nextInt();

        // Начало транзакции для создания и добавления объекта в таблицу
        session.beginTransaction();

        // Создание объекта
        Course course = new Course(title, duration);
        session.save(course);
        System.out.println("Курс успешно добавлен в таблицу");

        // Закрытие сессии с сохранением
        session.getTransaction().commit();
    }

    /**
     * Изменение курса
     * @param session объект Сессии
     */
    private static void changeCourse(Session session) {
        // TODO обернуть в цикл обработки исключений от неверного ввода.

        System.out.println("Вы выбрали пункт меню №3 ");
        System.out.println("Введите id курса, который хотите изменить: "); //TODO предложить отмену выбранной операции
        Scanner scanner = new Scanner(System.in);
        int courseID = scanner.nextInt();

        // Начало транзакции для создания и добавления объекта в таблицу
        session.beginTransaction();
        // TODO проверка наличия ID в списке курсов
        Course retrievedCourse = session.get(Course.class, courseID);

        // TODO проверка введенного значения
        System.out.println("Введите новое название курса: ");
        String newTitle = scanner.next();

        System.out.println("Введите продолжительность курса: ");
        int newDuration = scanner.nextInt();

        // Обновление объекта
        retrievedCourse.updateTitle(newTitle);
        retrievedCourse.updateDuration(newDuration);
        session.update(retrievedCourse);
        System.out.println("Обновление курса с ID="+courseID + "прошло успешно");

        // Закрытие сессии с сохранением
        session.getTransaction().commit();
    }

    /**
     * Удаление курса по ID
     * @param session объект Сессии
     */
    private static void deleteCourse(Session session) {
        // TODO обернуть в цикл обработки исключений от неверного ввода.

        System.out.println("Вы выбрали пункт меню №4 ");
        System.out.println("Введите id курса, который хотите удалить: "); //TODO предложить отмену выбранной операции
        Scanner scanner = new Scanner(System.in);
        int courseID = scanner.nextInt();

        // Начало транзакции для создания и добавления объекта в таблицу
        session.beginTransaction();

        Course deleteddCourse = session.get(Course.class, courseID);

        session.delete(deleteddCourse);

        // Закрытие сессии с сохранением
        session.getTransaction().commit();
    }
    //endregion

}
