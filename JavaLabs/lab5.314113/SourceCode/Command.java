import MyExceptions.*;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Данный класс реализует методы консольных команд данного приложения.
 */
public class Command {
    private static File ef;
    private static boolean execFlag;
    private static Scanner sc;
    private static int skipCount;

    /**
     * Данный метод вызывается при вводе команды help и показывает все доступные команды.
     */
    public static void help() {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        System.out.println("Данная программа запускается с аргументом, представляющим из себя путь к файлу, из которого нужно заполнить коллекцию.");
        System.out.println("В случае, если аргумент не указан, коллекция будет пустой.");
        System.out.println("ВНИМАНИЕ!!! Программа не работает с именами файлов или путями к ним, содержащими пробелы.");
        System.out.println("");
        System.out.println(">>>ДОСТУПНЫЕ КОМАНДЫ<<<");
        System.out.println("help: вывести справку по доступным командам");
        System.out.println("info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        System.out.println("show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.println("insert_key {element}: добавить новый элемент с заданным ключом");
        System.out.println("update_id {element}: обновить значение элемента коллекции, id которого равен заданному");
        System.out.println("remove_key {key}: удалить элемент из коллекции по его ключу");
        System.out.println("clear: очистить коллекцию");
        System.out.println("save: сохранить коллекцию в файл");
        System.out.println("execute_script {file_name}: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.println("exit: завершить программу без сохранения в файл (Комбинация клавиш CTRL+D так же завершает выполнение программы)");
        System.out.println("history: вывести последние 14 команд (без их аргументов)");
        System.out.println("replace_if_lower {element}: заменить значение по ключу, если новое значение меньше старого");
        System.out.println("remove_lower_key {key}: удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        System.out.println("remove_any_by_number_of_participants {numberOfParticipants}: удалить из коллекции один элемент, значение поля numberOfParticipants которого эквивалентно заданному");
        System.out.println("filter_starts_with_name {name}: вывести элементы, значение поля name которых начинается с заданной подстроки");
        System.out.println("print_field_descending_number_of_participants {numberOfParticipants}: вывести значения поля numberOfParticipants в порядке убывания");
        System.out.println("");
        System.out.println("Сделано на Уралмаше с любовью для Купчино.");
        System.out.println("2020 (с) chapsan2001");
        System.out.println("");
    }

    /**
     * Данный метод вызывается при вводе команды info и выводит информацию о коллекции.
     */
    public static void info() {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        System.out.println("Информация о коллекции:");
        System.out.println("Тип - Hashtable");
        System.out.println("Количество элементов: " + Lab5.collection.size());
        System.out.println("Дата инициализации: " + Lab5.initDate);
        System.out.println("");
    }

    /**
     * Данный метод вызывается при вводе команды show и показывает все элементы коллекции в строковом представлении.
     *
     * @throws EmptyCollectionException Исключение выбрасывается в случае, если команда вызывается при пустой коллекции.
     */
    public static void show() throws EmptyCollectionException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        if (Lab5.collection.size() != 0) {
            Lab5.collection.forEach((k, v) -> System.out.println(v.toString()));
        } else {
            throw new EmptyCollectionException();
        }
    }

    /**
     * Данный метод вызывается при вводе команды insert_key и добавляет в коллекцию новый элемент с введенным в качестве аргумента ключом.
     *
     * @param arg Аргумент команды, ключ, который нужно присвоить новому элементу коллекции.
     * @throws NumberFormatException           Исключение выбрасывается в случае, если число окажется слишком длинным или если вместо числа будут введены буквы.
     * @throws ElementAlreadyExistsException   Исключение выбрасывается в случае, если элемент с указанным в качестве аргумента ключом уже существует.
     * @throws EmptyStringException            Исключение выбрасывается в случае, если введена пустая строка там, где ее быть не должно.
     * @throws ZeroException                   Исключение выбрасывается в случае, если получено значение 0 там, где его быть не должно.
     * @throws CoordinatesOutOfBoundsException Исключение выбрасывается, если хотя бы одна из координат выходит за допустимые границы.
     * @throws NoSuchGenreException            Исключение выбрасывается в случае, если введенного пользователем жанра не существует.
     * @throws FileNotFoundException           Исключение выбрасывается в случае, если программа не может найти файл, из которого нужно считать элемент коллекции.
     */
    public static void insertKey(String arg) throws NumberFormatException, ElementAlreadyExistsException, EmptyStringException, ZeroException, CoordinatesOutOfBoundsException, NoSuchGenreException, FileNotFoundException {
        int key = Integer.parseInt(arg);
        System.out.println("");
        if (Lab5.collection.get(key) != null) {
            throw new ElementAlreadyExistsException();
        } else {
            if (key <= 0) {
                throw new ZeroException();
            } else {
                MusicBand tmp = new MusicBand();
                tmp.setCoordinates(new Coordinates());
                tmp.setBestAlbum(new Album());
                tmp.setId(key);
                tmp.setCreationDate(LocalDate.now());
                String tmpStr;
                Scanner er;
                if (execFlag) {
                    er = new Scanner(ef);
                    skipCount++;
                    for (int i = 0; i < skipCount; i++) {
                        String skip = er.nextLine();
                    }
                } else {
                    er = new Scanner(System.in);
                }
                System.out.println("Пожалуйста, введите данные группы, которую вы хотите добавить.");
                System.out.print("Название: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Название: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        tmp.setName(tmpStr);
                    }
                } else {
                    tmp.setName(tmpStr);
                }
                System.out.print("Координата X: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= -492) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Координата X: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) <= -492) {
                            throw new CoordinatesOutOfBoundsException();
                        } else {
                            tmp.getCoordinates().setX(Long.parseLong(tmpStr));
                        }
                    }
                } else {
                    tmp.getCoordinates().setX(Long.parseLong(tmpStr));
                }
                System.out.print("Координата Y: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) > 19) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Координата Y: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) > 19) {
                            throw new CoordinatesOutOfBoundsException();
                        } else {
                            tmp.getCoordinates().setY(Float.parseFloat(tmpStr));
                        }
                    }
                } else {
                    tmp.getCoordinates().setY(Float.parseFloat(tmpStr));
                }
                System.out.print("Количество участников: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Количество участников: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) <= 0) {
                            throw new ZeroException();
                        } else {
                            tmp.setNumberOfParticipants(Long.parseLong(tmpStr));
                        }
                    }
                } else {
                    tmp.setNumberOfParticipants(Long.parseLong(tmpStr));
                }
                System.out.println(Arrays.toString(MusicGenre.values()));
                System.out.print("Жанр: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    tmp.setGenre(null);
                } else {
                    try {
                        tmp.setGenre(MusicGenre.valueOf(tmpStr));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.println(Arrays.toString(MusicGenre.values()));
                        System.out.println("Жанр: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                            if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                                tmp.setGenre(null);
                            } else {
                                try {
                                    tmp.setGenre(MusicGenre.valueOf(tmpStr));
                                } catch (IllegalArgumentException ex) {
                                    throw new NoSuchGenreException();
                                }
                            }
                        }
                    }
                }
                System.out.print("Название лучшего альбома: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    tmp.setBestAlbum(null);
                } else {
                    tmp.getBestAlbum().setName(tmpStr);
                    System.out.print("Количество треков в лучшем альбоме: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.print("Количество треков в альбоме: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                        }
                        if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                            throw new EmptyStringException();
                        } else {
                            if (Integer.parseInt(tmpStr) <= 0) {
                                throw new ZeroException();
                            } else {
                                tmp.getBestAlbum().setTracks(Integer.parseInt(tmpStr));
                            }
                        }
                    } else {
                        tmp.getBestAlbum().setTracks(Integer.parseInt(tmpStr));
                    }
                    System.out.print("Продажи лучшего альбома: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.print("Продажи лучшего альбома: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                        }
                        if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                            throw new EmptyStringException();
                        } else {
                            if (Integer.parseInt(tmpStr) <= 0) {
                                throw new ZeroException();
                            } else {
                                tmp.getBestAlbum().setSales(Long.parseLong(tmpStr));
                            }
                        }
                    } else {
                        tmp.getBestAlbum().setSales(Long.parseLong(tmpStr));
                    }
                }
                Lab5.collection.put(key, tmp);
                System.out.println("");
            }
        }
    }

    /**
     * Данный метод вызывается при вводе команды update_id и обновляет значение элемента коллекции, номер которого задан в качестве аргумента.
     *
     * @param arg Аргумент команды, ключ, принадлежащий элементу, который необходимо обновить.
     * @throws NumberFormatException           Исключение выбрасывается в случае, если число окажется слишком длинным или если вместо числа будут введены буквы.
     * @throws NoSuchElementException          Исключение выбрасывается в случае, если элемента с указанным в качестве аргумента ключом не существует.
     * @throws EmptyStringException            Исключение выбрасывается в случае, если введена пустая строка там, где ее быть не должно.
     * @throws ZeroException                   Исключение выбрасывается в случае, если получено значение 0 там, где его быть не должно.
     * @throws CoordinatesOutOfBoundsException Исключение выбрасывается, если хотя бы одна из координат выходит за допустимые границы.
     * @throws NoSuchGenreException            Исключение выбрасывается в случае, если введенного пользователем жанра не существует.
     * @throws FileNotFoundException           Исключение выбрасывается в случае, если программа не может найти файл, из которого нужно считать элемент коллекции.
     */
    public static void updateId(String arg) throws NumberFormatException, NoSuchElementException, EmptyStringException, ZeroException, CoordinatesOutOfBoundsException, NoSuchGenreException, FileNotFoundException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        int key = Integer.parseInt(arg);
        System.out.println("");
        if (Lab5.collection.get(key) == null) {
            throw new NoSuchElementException();
        } else {
            if (key <= 0) {
                throw new ZeroException();
            } else {
                MusicBand tmp = new MusicBand();
                tmp.setCoordinates(new Coordinates());
                tmp.setBestAlbum(new Album());
                tmp.setId(key);
                tmp.setCreationDate(LocalDate.now());
                String tmpStr;
                Scanner er;
                if (execFlag) {
                    er = new Scanner(ef);
                    skipCount++;
                    for (int i = 0; i < skipCount; i++) {
                        String skip = er.nextLine();
                    }
                } else {
                    er = new Scanner(System.in);
                }
                System.out.println("Пожалуйста, введите данные группы, которую вы хотите вписать на это место.");
                System.out.print("Название: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Название: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        tmp.setName(tmpStr);
                    }
                } else {
                    tmp.setName(tmpStr);
                }
                System.out.print("Координата X: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= -492) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Координата X: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) <= -492) {
                            throw new CoordinatesOutOfBoundsException();
                        } else {
                            tmp.getCoordinates().setX(Long.parseLong(tmpStr));
                        }
                    }
                } else {
                    tmp.getCoordinates().setX(Long.parseLong(tmpStr));
                }
                System.out.print("Координата Y: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) > 19) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Координата Y: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) > 19) {
                            throw new CoordinatesOutOfBoundsException();
                        } else {
                            tmp.getCoordinates().setY(Float.parseFloat(tmpStr));
                        }
                    }
                } else {
                    tmp.getCoordinates().setY(Float.parseFloat(tmpStr));
                }
                System.out.print("Количество участников: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Количество участников: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) <= 0) {
                            throw new ZeroException();
                        } else {
                            tmp.setNumberOfParticipants(Long.parseLong(tmpStr));
                        }
                    }
                } else {
                    tmp.setNumberOfParticipants(Long.parseLong(tmpStr));
                }
                System.out.println(Arrays.toString(MusicGenre.values()));
                System.out.print("Жанр: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    tmp.setGenre(null);
                } else {
                    try {
                        tmp.setGenre(MusicGenre.valueOf(tmpStr));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.println(Arrays.toString(MusicGenre.values()));
                        System.out.println("Жанр: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                            if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                                tmp.setGenre(null);
                            } else {
                                try {
                                    tmp.setGenre(MusicGenre.valueOf(tmpStr));
                                } catch (IllegalArgumentException ex) {
                                    throw new NoSuchGenreException();
                                }
                            }
                        }
                    }
                }
                System.out.print("Название лучшего альбома: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    tmp.setBestAlbum(null);
                } else {
                    tmp.getBestAlbum().setName(tmpStr);
                    System.out.print("Количество треков в лучшем альбоме: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.print("Количество треков в альбоме: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                        }
                        if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                            throw new EmptyStringException();
                        } else {
                            if (Integer.parseInt(tmpStr) <= 0) {
                                throw new ZeroException();
                            } else {
                                tmp.getBestAlbum().setTracks(Integer.parseInt(tmpStr));
                            }
                        }
                    } else {
                        tmp.getBestAlbum().setTracks(Integer.parseInt(tmpStr));
                    }
                    System.out.print("Продажи лучшего альбома: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.print("Продажи лучшего альбома: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                        }
                        if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                            throw new EmptyStringException();
                        } else {
                            if (Integer.parseInt(tmpStr) <= 0) {
                                throw new ZeroException();
                            } else {
                                tmp.getBestAlbum().setSales(Long.parseLong(tmpStr));
                            }
                        }
                    } else {
                        tmp.getBestAlbum().setSales(Long.parseLong(tmpStr));
                    }
                }
                Lab5.collection.put(key, tmp);
                System.out.println("");
            }
        }
    }

    /**
     * Данный метод вызывается при вводе команды remove_key и удаляет из коллекции элемент, номер которого задан в качестве аргумента.
     *
     * @param arg Аргумент команды, ключ, принадлежащий элементу, который необходимо удалить.
     * @throws NumberFormatException  Исключение выбрасывается в случае, если число окажется слишком длинным или если вместо числа будут введены буквы.
     * @throws NoSuchElementException Исключение выбрасывается в случае, если элемента с указанным в качестве аргумента ключом не существует.
     */
    public static void removeKey(String arg) throws NumberFormatException, NoSuchElementException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        int key = Integer.parseInt(arg);
        System.out.println("");
        if (Lab5.collection.get(key) == null) {
            throw new NoSuchElementException();
        } else {
            Lab5.collection.remove(key);
            System.out.println("Элемент №" + key + " успешно удален.");
            System.out.println("");
        }
    }

    /**
     * Данный метод вызывается при вводе команды clear и очищает коллекцию.
     *
     * @throws EmptyCollectionException Исключение выбрасывается в случае, если команда вызывается при пустой коллекции.
     */
    public static void clear() throws EmptyCollectionException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        if (Lab5.collection.size() == 0) {
            throw new EmptyCollectionException();
        } else {
            Lab5.collection.clear();
            System.out.println("Коллекция успешно очищена.");
            System.out.println("");
        }
    }

    /**
     * Данный метод вызывается при вводе команды save и сохраняет коллекцию в файл.
     *
     * @throws IOException                 Исключение выбрасывается в случае невозможности записать данные в файл, например, нет прав доступа.
     * @throws UnableToCreateFileException Исключение выбрасывается в случае невозможности создать файл для сохранения, например, нет прав доступа.
     */
    public static void save() throws IOException, UnableToCreateFileException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        File sf = new File("collectionOutput.json");
        AtomicBoolean saveFlag = new AtomicBoolean(true);
        if (!sf.exists()) {
            if (sf.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(sf);
                     BufferedOutputStream save = new BufferedOutputStream(fos)) {
                    Lab5.collection.forEach((k, v) -> {
                        byte[] buffer = (JSON.toJSONString(v) + System.lineSeparator()).getBytes();
                        try {
                            save.write(buffer, 0, buffer.length);
                        } catch (IOException e) {
                            System.out.println("Ошибка записи данных.");
                            saveFlag.set(false);
                        }
                    });
                } catch (IOException e) {
                    System.out.println("Ошибка записи данных.");
                    saveFlag.set(false);
                }
                System.out.println("Сохранение успешно произведено.");
            } else {
                throw new UnableToCreateFileException();
            }
        } else {
            System.out.println("При сохранении данной коллекции будет удалена уже существующая в файле collectionOutput.json коллекция.");
            System.out.println("Вы действительно хотите продолжить? [Y/N]");
            Scanner yesOrNot = new Scanner(System.in);
            String yn = "";
            while (!yn.equals("Y") && !yn.equals("y") && !yn.equals("N") && !yn.equals("n")) {
                yn = yesOrNot.nextLine();
                if (!yn.equals("Y") && !yn.equals("y") && !yn.equals("N") && !yn.equals("n")) {
                    System.out.println("[Y/N]");
                } else {
                    if (yn.equals("n") || yn.equals("N")) {
                        System.out.println("Сохранение отменено пользователем.");
                        System.out.println("");
                    } else {
                        try (FileOutputStream fos = new FileOutputStream(sf);
                             BufferedOutputStream save = new BufferedOutputStream(fos)) {
                            Lab5.collection.forEach((k, v) -> {
                                byte[] buffer = (JSON.toJSONString(v) + System.lineSeparator()).getBytes();
                                try {
                                    save.write(buffer, 0, buffer.length);
                                } catch (IOException e) {
                                    System.out.println("Ошибка записи данных.");
                                    System.out.println("");
                                    saveFlag.set(false);
                                }
                            });
                        } catch (IOException e) {
                            System.out.println("Ошибка записи данных.");
                            System.out.println("");
                            saveFlag.set(false);
                        }
                        if (saveFlag.get()) {
                            System.out.println("Сохранение успешно произведено.");
                            System.out.println("");
                        }
                    }
                }
            }
        }
    }

    /**
     * Данный метод вызывается при вводе команды execute_script и выполняет скрипт из указанного в качестве аргумента файла.
     *
     * @param path Аргумент команды, путь к файлу, из которого нужно считать скрипт.
     * @throws IOException         Исключение выбрасывается в случае ошибки чтения файла, например, когда файла не существует.
     * @throws ScriptLoopException Исключение выбрасывается в случае, если скрипт содержит команду для выполнения самого себя.
     */
    public static void executeScript(String path) throws IOException, ScriptLoopException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        execFlag = true;
        System.out.println("");
        ef = new File(path);
        sc = new Scanner(ef);
        String cmd = "";
        while (sc.hasNext()) {
            try {
                cmd = sc.nextLine();
            } catch (java.util.NoSuchElementException e) {
                Command.exit();
            }
            String[] cmdSplit = cmd.split(" ");
            String arg = "";
            if (cmdSplit.length > 1) {
                arg = cmdSplit[cmdSplit.length - 1];
            }
            if (Lab5.shift == -1) {
                for (int i = 13; i > 0; i--) {
                    Lab5.hist[i] = Lab5.hist[i - 1];
                }
                Lab5.hist[0] = cmdSplit[0];
            } else {
                Lab5.hist[Lab5.shift] = cmdSplit[0];
                Lab5.shift--;
            }
            try {
                switch (cmdSplit[0]) {
                    case "help":
                        Command.help();
                        break;
                    case "info":
                        Command.info();
                        break;
                    case "show":
                        try {
                            Command.show();
                        } catch (EmptyCollectionException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        }
                        break;
                    case "insert_key":
                        try {
                            Command.insertKey(arg);
                        } catch (ElementAlreadyExistsException | CoordinatesOutOfBoundsException | ZeroException | EmptyStringException | NoSuchGenreException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода данных.");
                            System.out.println("");
                        } catch (FileNotFoundException e) {
                            System.out.println("Ошибка чтения данных.");
                            System.out.println("");
                        }
                        break;
                    case "update_id":
                        try {
                            Command.updateId(arg);
                        } catch (NoSuchElementException | CoordinatesOutOfBoundsException | ZeroException | EmptyStringException | NoSuchGenreException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода данных.");
                            System.out.println("");
                        } catch (FileNotFoundException e) {
                            System.out.println("Ошибка чтения данных.");
                            System.out.println("");
                        }
                        break;
                    case "remove_key":
                        try {
                            Command.removeKey(arg);
                        } catch (NoSuchElementException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода данных.");
                            System.out.println("");
                        }
                        break;
                    case "clear":
                        try {
                            Command.clear();
                        } catch (EmptyCollectionException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        }
                        break;
                    case "save":
                        try {
                            Command.save();
                        } catch (UnableToCreateFileException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        } catch (IOException e) {
                            System.out.println("Ошибка записи данных.");
                            System.out.println("");
                        }
                        break;
                    case "execute_script":
                        if (arg.equals(ef.getName())) {
                            throw new ScriptLoopException();
                        }
                        try {
                            Command.executeScript(arg);
                        } catch (IOException e) {
                            System.out.println("Ошибка чтения файла.");
                            System.out.println("");
                        } catch (ScriptLoopException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        }
                        break;
                    case "exit":
                        Command.exit();
                        break;
                    case "history":
                        Command.history();
                        break;
                    case "replace_if_lower":
                        try {
                            Command.replaceIfLower(arg);
                        } catch (NoSuchElementException | CoordinatesOutOfBoundsException | ZeroException | EmptyStringException | NoSuchGenreException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода данных.");
                            System.out.println("");
                        } catch (FileNotFoundException e) {
                            System.out.println("Ошибка чтения данных.");
                            System.out.println("");
                        }
                        break;
                    case "remove_lower_key":
                        try {
                            Command.removeLowerKey(arg);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода данных.");
                            System.out.println("");
                        }
                        break;
                    case "remove_any_by_number_of_participants":
                        try {
                            Command.removeAnyByNumberOfParticipants(arg);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода данных.");
                            System.out.println("");
                        }
                        break;
                    case "filter_starts_with_name":
                        Command.filterStartsWithName(arg);
                        break;
                    case "print_field_descending_number_of_participants":
                        try {
                            Command.printFieldDescendingNumberOfParticipants();
                        } catch (EmptyCollectionException e) {
                            System.out.println(e.getMessage());
                            System.out.println("");
                        }
                        break;
                    default:
                        System.out.println("Неизвестная команда. Для справки введите команду help.");
                        System.out.println("");
                        break;
                }
            } catch (java.util.NoSuchElementException e) {
                Command.exit();
            }
        }
        execFlag = false;
    }

    /**
     * Данный метод вызывается при вводе команды exit и завершает выполнение программы.
     */
    public static void exit() {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("Завершение работы программы.");
        System.exit(1723);
    }

    /**
     * Данный метод вызывается при вводе команды history и выводит на экран последние 14 команд (без их аргументов).
     */
    public static void history() {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        for (int j = 13; j >= 0; j--) {
            if (Lab5.hist[j] != null) {
                System.out.println((14 - j) + ". " + Lab5.hist[j]);
            }
        }
        System.out.println("");
    }

    /**
     * Данный метод вызывается при вводе команды replace_if_lower и заменяет музыкальную группу, ключ которой указан в качестве аргумента, если название новой группы короче названия старой.
     *
     * @param arg Аргумент команды, ключ, принадлежащий элементу, который необходимо заменить.
     * @throws NumberFormatException           Исключение выбрасывается в случае, если число окажется слишком длинным или если вместо числа будут введены буквы.
     * @throws NoSuchElementException          Исключение выбрасывается в случае, если элемента с указанным в качестве аргумента ключом не существует.
     * @throws EmptyStringException            Исключение выбрасывается в случае, если введена пустая строка там, где ее быть не должно.
     * @throws ZeroException                   Исключение выбрасывается в случае, если получено значение 0 там, где его быть не должно.
     * @throws CoordinatesOutOfBoundsException Исключение выбрасывается, если хотя бы одна из координат выходит за допустимые границы.
     * @throws NoSuchGenreException            Исключение выбрасывается в случае, если введенного пользователем жанра не существует.
     * @throws FileNotFoundException           Исключение выбрасывается в случае, если программа не может найти файл, из которого нужно считать элемент коллекции.
     */
    public static void replaceIfLower(String arg) throws NumberFormatException, NoSuchElementException, EmptyStringException, ZeroException, CoordinatesOutOfBoundsException, NoSuchGenreException, FileNotFoundException {
        System.out.println("");
        int key = Integer.parseInt(arg);
        if (Lab5.collection.get(key) == null) {
            throw new NoSuchElementException();
        } else {
            if (key <= 0) {
                throw new ZeroException();
            } else {
                MusicBand tmp = new MusicBand();
                tmp.setCoordinates(new Coordinates());
                tmp.setBestAlbum(new Album());
                tmp.setId(key);
                tmp.setCreationDate(LocalDate.now());
                String tmpStr;
                Scanner er;
                if (execFlag) {
                    er = new Scanner(ef);
                    skipCount++;
                    for (int i = 0; i < skipCount; i++) {
                        String skip = er.nextLine();
                    }
                } else {
                    er = new Scanner(System.in);
                }
                System.out.println("Пожалуйста, введите данные группы, которую вы хотите вписать на это место.");
                System.out.print("Название: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Название: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        tmp.setName(tmpStr);
                    }
                } else {
                    tmp.setName(tmpStr);
                }
                System.out.print("Координата X: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= -492) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Координата X: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) <= -492) {
                            throw new CoordinatesOutOfBoundsException();
                        } else {
                            tmp.getCoordinates().setX(Long.parseLong(tmpStr));
                        }
                    }
                } else {
                    tmp.getCoordinates().setX(Long.parseLong(tmpStr));
                }
                System.out.print("Координата Y: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) > 19) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Координата Y: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) > 19) {
                            throw new CoordinatesOutOfBoundsException();
                        } else {
                            tmp.getCoordinates().setY(Float.parseFloat(tmpStr));
                        }
                    }
                } else {
                    tmp.getCoordinates().setY(Float.parseFloat(tmpStr));
                }
                System.out.print("Количество участников: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                    System.out.println("Что? Попробуйте еще разок.");
                    System.out.print("Количество участников: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.equals("")) {
                        throw new EmptyStringException();
                    } else {
                        if (Integer.parseInt(tmpStr) <= 0) {
                            throw new ZeroException();
                        } else {
                            tmp.setNumberOfParticipants(Long.parseLong(tmpStr));
                        }
                    }
                } else {
                    tmp.setNumberOfParticipants(Long.parseLong(tmpStr));
                }
                System.out.println(Arrays.toString(MusicGenre.values()));
                System.out.print("Жанр: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    tmp.setGenre(null);
                } else {
                    try {
                        tmp.setGenre(MusicGenre.valueOf(tmpStr));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.println(Arrays.toString(MusicGenre.values()));
                        System.out.println("Жанр: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                            if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                                tmp.setGenre(null);
                            } else {
                                try {
                                    tmp.setGenre(MusicGenre.valueOf(tmpStr));
                                } catch (IllegalArgumentException ex) {
                                    throw new NoSuchGenreException();
                                }
                            }
                        }
                    }
                }
                System.out.print("Название лучшего альбома: ");
                tmpStr = er.nextLine();
                if (execFlag) {
                    String skip = sc.nextLine();
                    skipCount++;
                }
                if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                    tmp.setBestAlbum(null);
                } else {
                    tmp.getBestAlbum().setName(tmpStr);
                    System.out.print("Количество треков в лучшем альбоме: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.print("Количество треков в альбоме: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                        }
                        if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                            throw new EmptyStringException();
                        } else {
                            if (Integer.parseInt(tmpStr) <= 0) {
                                throw new ZeroException();
                            } else {
                                tmp.getBestAlbum().setTracks(Integer.parseInt(tmpStr));
                            }
                        }
                    } else {
                        tmp.getBestAlbum().setTracks(Integer.parseInt(tmpStr));
                    }
                    System.out.print("Продажи лучшего альбома: ");
                    tmpStr = er.nextLine();
                    if (execFlag) {
                        String skip = sc.nextLine();
                        skipCount++;
                    }
                    if (tmpStr.split(" ").length < 1 || tmpStr.equals("") || Integer.parseInt(tmpStr) <= 0) {
                        System.out.println("Что? Попробуйте еще разок.");
                        System.out.print("Продажи лучшего альбома: ");
                        tmpStr = er.nextLine();
                        if (execFlag) {
                            String skip = sc.nextLine();
                            skipCount++;
                        }
                        if (tmpStr.split(" ").length < 1 || tmpStr.equals("")) {
                            throw new EmptyStringException();
                        } else {
                            if (Integer.parseInt(tmpStr) <= 0) {
                                throw new ZeroException();
                            } else {
                                tmp.getBestAlbum().setSales(Long.parseLong(tmpStr));
                            }
                        }
                    } else {
                        tmp.getBestAlbum().setSales(Long.parseLong(tmpStr));
                    }
                }
                if (Lab5.collection.get(key).compareTo(tmp) > 0) {
                    Lab5.collection.put(key, tmp);
                    System.out.println("Замена успешно произведена.");
                } else {
                    System.out.println("Замена произведена не была.");
                }
                System.out.println("");
            }
        }
    }

    /**
     * Данный метод вызывается при вводе команды remove_lower_key и удаляет из коллекции элементы, ключ которых меньше заданного в качестве аргумента.
     *
     * @param arg Аргумент команды, ключ, элементы с ключом меньше которого должны быть удалены.
     * @throws NumberFormatException Исключение выбрасывается в случае, если число окажется слишком длинным или если вместо числа будут введены буквы.
     */
    public static void removeLowerKey(String arg) throws NumberFormatException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        int key = Integer.parseInt(arg);
        ArrayList<Integer> delKey = new ArrayList<>();
        Lab5.collection.forEach((k, v) -> {
            if (v.getId() < key) {
                delKey.add(k);
            }
        });
        if (delKey.size() == 0) {
            System.out.println("Элементов для удаления не найдено.");
        }
        delKey.forEach((n) -> {
            Lab5.collection.remove(n);
            System.out.println("Элемент №" + n + " успешно удален.");
        });
        System.out.println("");
    }

    /**
     * Данный метод вызывается при вводе команды remove_any_by_number_of_participants и удаляет из коллекции один из элементов (последний встречающийся), у которых значение поля numberOfParticipants эквивалентно указанному в аргументе значению.
     *
     * @param arg Аргумент команды, количество участников, группа с которым должна быть удалена.
     * @throws NumberFormatException Исключение выбрасывается в случае, если число окажется слишком длинным или если вместо числа будут введены буквы.
     */
    public static void removeAnyByNumberOfParticipants(String arg) throws NumberFormatException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        int num = Integer.parseInt(arg);
        ArrayList<Integer> delKey = new ArrayList<>();
        Lab5.collection.forEach((k, v) -> {
            if (v.getNumberOfParticipants() == num) {
                delKey.add(k);
            }
        });
        if (delKey.size() == 0) {
            System.out.println("В коллекции не найдено элементов с указанным количеством участников.");
        } else {
            Lab5.collection.remove(delKey.get(delKey.size() - 1));
            System.out.println("Элемент №" + delKey.get(delKey.size() - 1) + " успешно удален.");
        }
        System.out.println("");
    }

    /**
     * Данный метод вызывается при вводе команды filter_starts_with_name и выводит элементы коллекции, у которых значение поля name начинается с заданной в качестве аргумента подстроки.
     *
     * @param arg Аргумент команды, подстрока, с которой должны начинаться искомые элементы коллекции.
     */
    public static void filterStartsWithName(String arg) {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        ArrayList<Integer> showKey = new ArrayList<>();
        Lab5.collection.forEach((k, v) -> {
            if (v.getName().startsWith(arg)) {
                showKey.add(k);
            }
        });
        if (showKey.size() == 0) {
            System.out.println("В коллекции не найдено элементов, имена которых начинаются с введенной подстроки.");
        } else {
            showKey.forEach((n) -> System.out.println(Lab5.collection.get(n).toString()));
        }
        System.out.println("");
    }

    /**
     * Данный метод вызывается при вводе команды print_field_descending_number_of_participants и выводит значения поля numberOfParticipants в порядке убываения.
     *
     * @throws EmptyCollectionException Исключение выбрасывается в случае, если команда вызывается при пустой коллекции.
     */
    public static void printFieldDescendingNumberOfParticipants() throws EmptyCollectionException {
        if (execFlag) {
            String skip = sc.nextLine();
            skipCount++;
        }
        System.out.println("");
        if (Lab5.collection.size() == 0) {
            throw new EmptyCollectionException();
        } else {
            ArrayList<Long> nop = new ArrayList<>();
            Lab5.collection.forEach((k, v) -> nop.add(v.getNumberOfParticipants()));
            nop.sort(Collections.reverseOrder());
            nop.forEach(System.out::println);
        }
        System.out.println("");
    }
}