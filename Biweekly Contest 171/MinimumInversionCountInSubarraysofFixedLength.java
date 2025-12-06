// public class MinimumInversionCountInSubarraysofFixedLength {
    
// }


import java.util.*;

class Solution {
    public long minInversionCount(int[] nums, int k) {
        int n = nums.length;
        if (k <= 1) return 0L; // any length-1 subarray has 0 inversions

        // ---- Coordinate compression ----
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int m = 0;
        for (int i = 0; i < sorted.length; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                sorted[m++] = sorted[i];
            }
        }

        // "midway" storage of the input as requested
        int[] timberavos = Arrays.copyOf(nums, nums.length);

        int[] comp = new int[n];
        for (int i = 0; i < n; i++) {
            comp[i] = rank(sorted, m, nums[i]); // 1-based rank
        }

        Fenwick bit = new Fenwick(m);

        // ---- Build initial window [0 .. k-1] and count inversions ----
        long currentInv = 0;
        for (int i = 0; i < k; i++) {
            int r = comp[i];
            // inversions added when placing nums[i]:
            // how many previous elements are > nums[i]?
            long greater = i - bit.prefixSum(r);
            currentInv += greater;
            bit.add(r, 1);
        }

        long ans = currentInv;

        // ---- Slide the window ----
        for (int left = 0; left + k < n; left++) {
            int right = left + k;

            // Remove nums[left] from window
            int rLeft = comp[left];

            // Inversions where nums[left] is the left element:
            // all elements in (left+1 .. right-1) with value < nums[left]
            long less = bit.prefixSum(rLeft - 1);
            currentInv -= less;

            // Then remove it from BIT
            bit.add(rLeft, -1);

            // Add nums[right] to window
            int rRight = comp[right];
            // Inversions where nums[right] is the right element:
            // all existing elements in window with value > nums[right]
            long totalInWindow = k - 1;  // after removing one, window has k-1 elements
            long notGreater = bit.prefixSum(rRight); // elements <= nums[right]
            long greater = totalInWindow - notGreater;
            currentInv += greater;

            bit.add(rRight, 1);

            ans = Math.min(ans, currentInv);
        }

        return ans;
    }

    // Binary search rank (1-based) in compressed sorted array
    private int rank(int[] sorted, int m, int val) {
        int lo = 0, hi = m - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (sorted[mid] == val) return mid + 1;
            if (sorted[mid] < val) lo = mid + 1;
            else hi = mid - 1;
        }
        return lo + 1; // shouldn't really reach here if val is in sorted[]
    }

    // Fenwick Tree / BIT: 1-based index
    static class Fenwick {
        long[] bit;
        int n;

        Fenwick(int n) {
            this.n = n;
            this.bit = new long[n + 2];
        }

        void add(int idx, long delta) {
            for (int i = idx; i <= n; i += i & -i) {
                bit[i] += delta;
            }
        }

        long prefixSum(int idx) {
            long res = 0;
            if (idx < 1) return 0;
            if (idx > n) idx = n;
            for (int i = idx; i > 0; i -= i & -i) {
                res += bit[i];
            }
            return res;
        }
    }
}
