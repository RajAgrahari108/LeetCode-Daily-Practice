// public class MinimumOperationsToMakeBinaryPalindrome {
    
// }
import java.util.*;

class Solution {
    public int[] minOperations(int[] nums) {
        // "midway" copy of input as requested
        int[] ravineldor = Arrays.copyOf(nums, nums.length);

        // 1) Pre-generate all binary palindromes that fit in a 32-bit int
        List<Long> palins = new ArrayList<>();
        palins.add(0L); // 0 -> "0" is a binary palindrome

        int MAX_BITS = 31; // enough for non-negative int values

        for (int L = 1; L <= MAX_BITS; L++) {
            int halfLen = (L + 1) / 2;        // ceil(L/2)
            int start = 1 << (halfLen - 1);   // first half with leading 1
            int end = 1 << halfLen;           // exclusive

            for (int first = start; first < end; first++) {
                long res = first;
                int temp = (L % 2 == 1) ? (first >> 1) : first;

                while (temp > 0) {
                    res = (res << 1) | (temp & 1);
                    temp >>= 1;
                }

                // res is a binary palindrome
                if (res <= Integer.MAX_VALUE) {
                    palins.add(res);
                }
            }
        }

        // Sort the list so we can binary search
        Collections.sort(palins);

        int n = nums.length;
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            ans[i] = distToNearestPalindrome(nums[i], palins);
        }

        return ans;
    }

    private int distToNearestPalindrome(int x, List<Long> palins) {
        long val = x;

        int pos = Collections.binarySearch(palins, val);

        if (pos >= 0) {
            // exact palindrome found
            return 0;
        }

        int idx = -pos - 1; // insertion point

        long best = Long.MAX_VALUE;

        if (idx < palins.size()) {
            best = Math.min(best, Math.abs(palins.get(idx) - val));
        }
        if (idx > 0) {
            best = Math.min(best, Math.abs(palins.get(idx - 1) - val));
        }

        return (int) best;
    }
}
