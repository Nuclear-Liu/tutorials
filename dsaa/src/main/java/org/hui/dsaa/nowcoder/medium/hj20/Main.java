package org.hui.dsaa.nowcoder.medium.hj20;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Hui.Liu
 * @since 2022-03-15 10:44
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String result = "NG";
            String password = sc.nextLine();
            if (password.length() > 8) {
                int upCase = 0;
                int lowCase = 0;
                int num = 0;
                int specify = 0;
                byte[] bytes = password.getBytes();
                for (byte aByte : bytes) {
                    if (aByte >= 'A' && aByte <= 'Z') {
                        upCase = 1;
                    } else if (aByte >= 'a' && aByte <= 'z') {
                        lowCase = 1;
                    } else if (aByte >= '0' && aByte <= '9') {
                        num = 1;
                    } else {
                        specify = 1;
                    }
                }
                if (upCase + lowCase + num + specify >= 3 && !haveRepeatingSubstring(password)) {
                    result = "OK";
                }
            }
            System.out.println(result);
        }
    }
    public static boolean haveRepeatingSubstring(String s) {
        HashMap<Integer, String> hashValue = new HashMap<>();
        for (int i = 0; i < s.length() - 2; i++) {
            String subS = s.substring(i, i + 3);
            if (!hashValue.containsValue(subS)) {
                hashValue.put(i, subS);
            } else {
                return true;
            }
        }
        return false;
    }
}
