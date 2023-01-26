import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Program {

    public static void main(String[] args) {
        System.out.println("Здравствуйте!");
        startProgram();
    }

    static void startProgram() {
        System.out.println("Введите номер программы (1-4), либо введите \"Q\" для выхода.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Программа № ");
        String program = scanner.nextLine();
        if (program.equalsIgnoreCase("q")) {
            System.out.println("До свидания!");
        } else if (program.chars().allMatch(Character::isDigit)) {
            switch (program) {
                case "1" -> ex0();
                case "2" -> {
                    System.out.println("Выберите записать данные в файл или сделать лог файл с ошибкой (1-2).");
                    System.out.print("Вариант № ");
                    String number = scanner.nextLine();
                    if (number.chars().allMatch(Character::isDigit)) {
                        switch (number) {
                            case "1" -> ex1("text.txt");
                            case "2" -> ex1("a/text.txt");
                        }
                    }
                }
                case "3" -> ex2();
                case "4" -> ex3();
                default -> {
                    System.out.println("Введен некорректный номер, пожалуйста, попробуйте еще раз.");
                    startProgram();
                }
            }
        } else {
            System.out.println("Ввод некорректен, пожалуйста, попробуйте еще раз.");
            startProgram();
        }
    }

    // Задача 1. Дана json строка {{"фамилия":"Иванов","оценка":"5","предмет":"Математика"},
    // {"фамилия":"Петрова","оценка":"4","предмет":"Информатика"},{"фамилия":"Краснов","оценка":"5","предмет":"Физика"}}
    // Задача написать метод(ы), который распарсить строку и выдаст ответ вида: Студент Иванов получил 5 по предмету
    // Математика. Студент Петрова получил 4 по предмету Информатика. Студент Краснов получил 5 по предмету Физика.
    // Используйте StringBuilder для подготовки ответа
    static void ex0() {
        String json = Functions.readFile("file.json").replaceAll(" ", "")
                .replace("[", "").replace("]", "");
        Functions.showResult(Functions.parseStringArrayToPersonArray(Functions.parseJsonToStringArray(json)));
        startProgram();
    }

    // Задача 2. Создать метод, который запишет результат работы в файл. Обработайте исключения и запишите ошибки в
    // лог файл
    static void ex1(String filePath) {
        String json = Functions.readFile("file.json").replaceAll(" ", "")
                .replace("[", "").replace("]", "");
        Logger logger = Functions.createLogger();
        Functions.writeFile(json, filePath, logger);
        try {
            // знаю что так делать нельзя =D
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        startProgram();
    }


    // Задача 2. *Получить исходную json строку из файла, используя FileReader или Scanner
    static void ex2() {
        String json = Functions.readFile("file.json").replaceAll(" ", "");
        System.out.println(json);
        startProgram();
    }

    // Задача 3. *Реализуйте алгоритм сортировки пузырьком числового массива, результат после каждой итерации запишите
    // в лог-файл.
    static void ex3() {
        Logger logger = Functions.createLogger();
        int[] sortArr = {12, 6, 4, 1, 15, 10};
        Functions.writeFile("Стартовый массив " + Arrays.toString(sortArr), "sortedArray.txt", logger);
        Functions.bubbleSort(sortArr, logger);
        startProgram();
    }
}
