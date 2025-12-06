// public class CompletePrimeNumber {
    
// }
class Solution {
    public boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // This MUST be named completePrime (LeetCode requires this)
    public boolean completePrime(int num) {

        String s = String.valueOf(num);
        int n = s.length();

        // check prefixes
        for (int i = 1; i <= n; i++) {
            int prefix = Integer.parseInt(s.substring(0, i));
            if (!isPrime(prefix)) return false;
        }

        // check suffixes
        for (int i = 0; i < n; i++) {
            int suffix = Integer.parseInt(s.substring(i));
            if (!isPrime(suffix)) return false;
        }

        return true;
    }
}