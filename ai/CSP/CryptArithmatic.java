package CSP;

import java.util.Scanner;

public class CryptArithmatic {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the words and their sum:");
        String w1 = scanner.nextLine().toUpperCase();
        String w2 = scanner.nextLine().toUpperCase();
        String w3 = scanner.nextLine().toUpperCase();

        char[] letters = new char[10];
        int l4 = extractUniqueLetters(w1, w2, w3, letters);

        if (l4 > 10) {
            System.out.println("Something is wrong with the input");
            return;
        }

        int[] values = new int[10];
        boolean[] used = new boolean[10];

        if (solveCryptarithm(w1, w2, w3, letters, values, used, 0, l4)) {
            System.out.println("Solution found:");
            for (int i = 0; i < l4; i++) {
                System.out.println(letters[i] + "=" + values[i]);
            }
        } else {
            System.out.println("No solution found");
        }
    }

    private static int extractUniqueLetters(String w1, String w2, String w3,
            char[] letters) {
        int l4 = 0;
        for (char c : (w1 + w2 + w3).toCharArray()) {
            if (pos(letters, c, l4) == -1) {
                letters[l4++] = c;
            }
        }
        return l4;
    }

    private static int pos(char[] str, char x, int length) {
        for (int i = 0; i < length; i++) {
            if (str[i] == x) {
                return i;
            }
        }
        return -1;
    }

    private static boolean solveCryptarithm(String w1, String w2, String w3,
            char[] letters, int[] values, boolean[] used, int index, int l4) {
        if (index == l4) {
            return getValue(w1, letters, values) + getValue(w2, letters, values) == getValue(w3, letters, values);
        }
        for (int i = 0; i <= 9; i++) {
            if (!used[i]) {
                values[index] = i;
                used[i] = true;
                if (solveCryptarithm(w1, w2, w3, letters, values, used, index + 1, l4)) {
                    return true;
                }
                used[i] = false;
            }
        }
        return false;
    }

    private static int getValue(String word, char[] letters, int[] values) {
        int num = 0;
        for (char c : word.toCharArray()) {
            num = num * 10 + values[pos(letters, c, letters.length)];
        }
        return num;
    }
}