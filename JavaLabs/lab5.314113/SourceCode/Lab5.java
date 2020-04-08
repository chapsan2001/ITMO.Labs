import MyExceptions.*;
import com.alibaba.fastjson.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Главный класс. Реализует систему управления коллекцией объектов.
 * @author Чапкий Александр Юрьевич, 285677, группа R3141
 * @version 1.0
 */
public class Lab5 {
    /** Поле сдвига. Необходимо для ведения истории команд.*/
    public static int shift = 13;
    /** Массив, содержащий в себе последние 14 команд.*/
    public static String[] hist = new String[14];
    /** Коллекция, которой управляет данное приложение.*/
    public static Hashtable<Integer, MusicBand> collection = new Hashtable<Integer, MusicBand>();
    /** Поле, содержащее дату инициализации коллекции.*/
    public static LocalDate initDate = LocalDate.now();
    /**
     * Метод, отвечающий за заполнение коллекции из файла.
     * @param path Содержит в себе путь к файлу, из которого необходимо заполнить коллекцию.
     * @throws IOException Исключение выбрасывается в случае ошибки чтения файла, например, когда файла не существует.
     * @throws ArrayIndexOutOfBoundsException Исключение выбрасывается в случае, если не указан аргумент командной строки при запуске программы.
     */
    public static void completeCollection(String path) throws IOException, ArrayIndexOutOfBoundsException{
        Path p = Paths.get(path);
        Scanner reader = new Scanner(p);
        int i = 1;
        while (reader.hasNextLine()){
            String jsonOb = reader.nextLine();
            try{
                MusicBand tmp = JSON.parseObject(jsonOb, MusicBand.class);
                tmp.setId(i);
                tmp.setCreationDate(LocalDate.now());
                collection.put(i,tmp);
            } catch (JSONException e){
                System.out.println("Ошибка чтения данных, строка №"+i);
                System.out.println("");
            }
            i++;
        }
    }

    /**
     * Основной метод. Точка входа в программу. Отвечает за обработку команд и их аргументов, вводимых пользователем.
     * @param args Массив аргументов командной строки.
     */
    public static void main(String[] args) {
        try {
            completeCollection(args[0]);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла.");
            System.out.println("");
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Файл не указан.");
            System.out.println("");
        }
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("Введите команду:");
            System.out.print("> ");
            String cmd = sc.next();
            String arg = null;
            if (shift == -1) {
                for (int i = 13; i>0; i--)
                {
                    hist[i]=hist[i-1];
                }
                hist[0] = cmd;
            } else {
                hist[shift] = cmd;
                shift--;
            }
            switch (cmd) {
                case "help": Command.help(); break;
                case "info": Command.info(); break;
                case "show": try {
                    Command.show();
                } catch (EmptyCollectionException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                }
                    break;
                case "insert_key": arg = sc.next(); try{
                    Command.insertKey(arg);
                } catch (ElementAlreadyExistsException | CoordinatesOutOfBoundsException | ZeroException | EmptyStringException | NoSuchGenreException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода данных.");
                    System.out.println("");
                }
                break;
                case "update_id": arg = sc.next(); try{
                    Command.updateId(arg);
                } catch (NoSuchElementException | CoordinatesOutOfBoundsException | ZeroException | EmptyStringException | NoSuchGenreException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода данных.");
                    System.out.println("");
                }
                    break;
                case "remove_key": arg = sc.next(); try{
                    Command.removeKey(arg);
                } catch (NoSuchElementException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода данных.");
                    System.out.println("");
                }
                    break;
                case "clear": try {
                    Command.clear();
                } catch (EmptyCollectionException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                }
                    break;
                case "save": try {
                    Command.save();
                } catch (UnableToCreateFileException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                } catch (IOException e){
                    System.out.println("Ошибка записи данных.");
                    System.out.println("");
                }
                    break;
                case "execute_script": arg = sc.next(); try {
                    Command.executeScript(arg);
                } catch (IOException e) {
                    System.out.println("Ошибка чтения файла.");
                    System.out.println("");
                } catch (ScriptLoopException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                }
                    break;
                case "exit": Command.exit(); break;
                case "history": Command.history(); break;
                case "replace_if_lower": arg = sc.next(); try{
                    Command.replaceIfLower(arg);
                } catch (NoSuchElementException | CoordinatesOutOfBoundsException | ZeroException | EmptyStringException | NoSuchGenreException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода данных.");
                    System.out.println("");
                }
                    break;
                case "remove_lower_key": arg = sc.next(); try {
                    Command.removeLowerKey(arg);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода данных.");
                    System.out.println("");
                }
                    break;
                case "remove_any_by_number_of_participants": arg = sc.next(); try{
                    Command.removeAnyByNumberOfParticipants(arg);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода данных.");
                    System.out.println("");
                }
                    break;
                case "filter_starts_with_name": arg = sc.next(); Command.filterStartsWithName(arg); break;
                case "print_field_descending_number_of_participants": try{
                    Command.printFieldDescendingNumberOfParticipants();
                } catch (EmptyCollectionException e) {
                    System.out.println(e.getMessage());
                    System.out.println("");
                }
                    break;
                default: System.out.println("Неизвестная команда. Для справки введите команду help."); System.out.println(""); break;
            }
        }
    }
}
