package org.hui.dsaa.nowcoder.medium.hj17;

import java.util.Objects;
import java.util.Scanner;

/**
 * @author Hui.Liu
 * @since 2022-03-15 10:20
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            int x = 0;
            int y = 0;
            String gj = sc.nextLine();
            String[] steps = gj.split(";");
            for (String step : steps) {
                if (step.length() <= 1) continue;
                String numS = step.substring(1);
                Integer num;
                try {
                    num = Integer.valueOf(numS);
                } catch (NumberFormatException e) {
                    num = null;
                }
                if (Objects.isNull(num)) {
                    continue;
                }
                switch (step.charAt(0)) {
                    case 'A':
                        x -= num;
                        break;
                    case 'D':
                        x += num;
                        break;
                    case 'S':
                        y -= num;
                        break;
                    case 'W':
                        y += num;
                        break;
                    default: break;
                }
            }
            System.out.println(x + "," + y);
        }
    }
}
