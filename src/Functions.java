import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public interface Functions {

    // парсит Json строку на список строк
    static String[] parseJsonToStringArray(String json) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(json);
        String[] strings = stringBuilder.toString().replace("{", "")
                .replace("\"", "").split("},");
        return strings;
    }

    // парсит список строк в список объектов Person
    static ArrayList<Person> parseStringArrayToPersonArray(String[] strings) {
        ArrayList<Person> arrayPersons = new ArrayList<>();
        for (String string : strings) {
            String[] stringPerson = string.replace("фамилия:", "")
                    .replace("оценка:", "")
                    .replace("предмет:", "")
                    .replace("}", "")
                    .split(",");
            Person person = new Person();
            person.lastName = stringPerson[0];
            person.grade = Integer.parseInt(stringPerson[1]);
            person.lesson = stringPerson[2];
            arrayPersons.add(person);
        }
        return arrayPersons;
    }

    // создает строку и выводит в консоль
    static void showResult(ArrayList<Person> arrayPersons) {
        for (Person person : arrayPersons) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Студент ");
            stringBuilder.append(person.lastName);
            stringBuilder.append(" получил ");
            stringBuilder.append(person.grade);
            stringBuilder.append(" по предмету ");
            stringBuilder.append(person.lesson);
            stringBuilder.append(".");
            System.out.println(stringBuilder);
        }
    }

    // считывает данные c файла json
    static String readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    static Logger createLogger() {
        Logger logger = Logger.getAnonymousLogger();
        SimpleFormatter formatter = new SimpleFormatter();
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("log.txt");
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        logger.addHandler(fileHandler);
        return logger;
    }

    static void writeFile(String text, String filePath, Logger logger) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(text);
        try (FileWriter fileWriter = new FileWriter(filePath, false)) {
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public static void bubbleSort(int[] sortArr, Logger logger) {
        int iteration = 0;
        for (int i = 0; i < sortArr.length - 1; i++) {
            for (int j = 0; j < sortArr.length - i - 1; j++) {
                if (sortArr[j + 1] < sortArr[j]) {
                    int swap = sortArr[j];
                    sortArr[j] = sortArr[j + 1];
                    sortArr[j + 1] = swap;
                    rewriteFile("Итерация " + iteration + " " + Arrays.toString(sortArr), logger);
                    iteration++;
                }
            }
        }
    }

    public static void rewriteFile(String newText, Logger logger) {
        StringBuilder stringBuilder = new StringBuilder();
        String file = Functions.readFile("sortedArray.txt");
        stringBuilder.append(file);
        stringBuilder.append(newText);
        Functions.writeFile(stringBuilder.toString(), "sortedArray.txt", logger);
    }
}
