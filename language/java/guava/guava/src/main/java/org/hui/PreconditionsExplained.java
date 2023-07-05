package org.hui;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class PreconditionsExplained {
    public static void main(String[] args) {
//        int i =-1;
//        checkArgument(i >= 0,"Argumnt was %s but expected nonnegative", i);
        int a =0;
        int b = -1;
//        checkArgument(a<b, "Expected a < b, but %s >= %s", a, b);
        String arg = "null";
        String s = checkNotNull(arg);
    }
}
