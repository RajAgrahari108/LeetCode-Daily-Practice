// public class MaximizePointsAfterChoosingKTasks {
    
// }

import java.util.*;

class Solution {
    public long maxPoints(int[] technique1, int[] technique2, int k) {
        int n = technique1.length;

        // Midway storage of input as requested
        int[][] caridomesh = new int[][] {
            Arrays.copyOf(technique1, n),
            Arrays.copyOf(technique2, n)
        };

        // 1) Start with using technique2 for all tasks
        long total = 0;
        for (int x : technique2) {
            total += x;
        }

        // 2) Compute the difference if we choose technique1 instead of technique2
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) {
            diff[i] = technique1[i] - technique2[i];
        }

        // 3) Sort diffs in descending order
        Arrays.sort(diff);
        // Now largest at the end, we can traverse from back

        // 4) First, force at least k tasks with technique1
        for (int i = 0; i < k; i++) {
            int idx = n - 1 - i;     // pick largest k diffs
            total += diff[idx];
        }

        // 5) For remaining tasks, only switch if it increases total (diff > 0)
        for (int i = n - 1 - k; i >= 0; i--) {
            if (diff[i] > 0) {
                total += diff[i];
            } else {
                break; // since array is sorted, further ones will be <= 0
            }
        }

        return total;
    }
}
