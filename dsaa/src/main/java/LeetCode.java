import java.util.HashMap;
import java.util.Map;

/**
 * @author Hui.Liu
 * @since 2021-12-23 9:27
 */
public class LeetCode {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> dic = new HashMap<>();
        int res = 0, tmp = 0;
        for (int j = 0; j < s.length(); j++) {
            int i = dic.getOrDefault(s.charAt(j), -1); // 获取索引 i
            dic.put(s.charAt(j), j); // 更新哈希表
            if (tmp < j -i) { // 当 j = 0
                tmp = tmp + 1;
            } else {
                tmp = j -i;
            }
            // tmp = ((tmp < j - i) ? tmp + 1 : j - i); // dp[j - 1] -> dp[j]

            res = Math.max(res, tmp); // max(dp[j - 1], dp[j])
        }
        return res;
    }
}
