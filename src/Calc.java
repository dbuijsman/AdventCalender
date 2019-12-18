public class Calc {
    static long gcd(long a, long b){
        a = Math.abs(a);
        b = Math.abs(b);
        while(a*b!=0){
            if(a>b){
                a-=b;
            } else {
                b-=a;
            }
        }
        return a+b;
    }
    static long lcm(long a, long b){
        return a*b/gcd(a,b);
    }
}
