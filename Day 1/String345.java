public class String345 {
    public static void main(String[] args) {
       
        char[] s = {'h', 'e', 'l', 'l', 'o'};
        Solution sol = new Solution();
        sol.reverseString(s);
        System.out.println(s);
    }
}

class Solution {
    public void reverseString(char[] s) {
        int i = 0, j = s.length - 1;
        while (i < j) {
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            i++;
            j--;
        }
    }
}




// ! leet code concept
// class Solution {
//         public void reverseString(char[] s) {
//         int i=0, j=s.length-1;
//         while(i<j){
//             char temp = s[i];
//             s[i]=s[j];
//             s[j] = temp;
//             i++; j--;
//         }
//     }
// }