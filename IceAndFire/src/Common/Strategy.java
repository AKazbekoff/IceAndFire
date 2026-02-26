package Common;

import Items.Item;
import java.util.List;
import java.util.Scanner;

public class Strategy {

    public static final int INCREASE_TYPE_HP = 0;
    public static final int INCREASE_TYPE_DEXTERITY = 1;
    public static final int INCREASE_TYPE_POWER = 2;

    /*
     *  Сканер для принятия ввода в программу. Применяется во всех местах одним и тем же объектом.
     * */
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    /**
     * Повышает уровень игрока, предлагая ему 3 варианта повышения
     */
    public static int activityIncreaseLevel(int currentLevel) {
        String increaseMessage = "Доступно повышение! Текущий уровень:" + currentLevel + "\n" +
                "[0] +10% к максимальному уровню здоровья\n" +
                "[1] +5% к ловкости\n" +
                "[2] +10% к базовой силе персонажа\n";
        printlnText(increaseMessage);

        while (true) {
            printText("Выберите тип повышения: ");
            String input = scanner.nextLine().trim();
            try {
                int increaseType = Integer.parseInt(input);
                if (increaseType < 0 || increaseType > 2) {
                    throw new IllegalArgumentException();
                } else {
                    return increaseType;
                }
            } catch (IllegalArgumentException e) {
                printlnText("Некорректный тип повышения! Доступные варианты указаны в скобках перед описанием повышения выше");
            }
        }
    }

    public static void activityPrintBackpack(List<Item> backpack) {
        StringBuilder sb = new StringBuilder("Содержимое рюкзака:\n");
        for (int i = 0; i < backpack.size(); i++) {
            Item item = backpack.get(i);
            sb.append("[").append(i).append("]\t").append(item.getTitle()).append("\t").append(item.getPrice()).append("$\n");
        }

        printlnText(sb.toString());
    }

    public static void activityPrintCurrentLeadingHand(Item item) {
        printlnText("Выбрано \"" + item.getTitle() + "\"");
    }

    public static void activityPrintCurrentHP(int hp) {
        printlnText("Здоровье: " + hp);
    }

    public static void activityPrintError(String msg) {
        printlnText(msg);
    }

    /**
     * Метод печати сообщения с эффектом набора текста
     */
    private static void printText(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            try {
                Thread.sleep(50);
            } catch (InterruptedException _) {
            }
        }
    }

    private static void printlnText(String text) {
        printText(text);
        System.out.println("\n");
    }
}
