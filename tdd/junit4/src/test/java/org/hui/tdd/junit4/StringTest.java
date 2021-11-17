package org.hui.tdd.junit4;

import org.junit.Test;

import java.util.Calendar;

/**
 * @author
 * @since 2021-11-16 21:37
 */
public class StringTest {

    private static String getApplyNo() {
        String applyNo = null;
        String year = null;
        Calendar instance = Calendar.getInstance();
        String currYear = String.valueOf(instance.get(Calendar.YEAR));
        int count = 0;
        if (applyNo == null) {
            year = currYear;
        } else {
//        LS20200909xxxx
            year = applyNo.substring(2, 6);
            count = Integer.valueOf(applyNo.substring(8));
            if (!year.equals(currYear)) {
                year = currYear;
                count = 0;
            }
        }
        String monthStr = String.valueOf(instance.get(Calendar.MONTH) + 1);
        for (int i = monthStr.length(); i < 2; i++) {
            monthStr = "0" + monthStr;
        }
        String countStr = String.valueOf(++count);
        for (int i = countStr.length(); i < 4; i++) {
            countStr = "0" + countStr;
        }
        String nextApplyNo = year + monthStr + countStr;
        return nextApplyNo;
    }
    @Test
    public void test() {
        System.out.println("LS20200909xxxx".substring(2, 6));
        System.out.println("LS20200909xxxx".substring(10));
        System.out.println(getApplyNo());
    }
}
