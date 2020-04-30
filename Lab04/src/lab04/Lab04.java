package lab04;

public class Lab04 {

    public static long fib(int n) {
        if (n < 2)
            return n;

        long a = 0, b = 1;
        for (int i = 1; i < n; i++) {
            long tmp = b;
            b = a + b;
            a = tmp;
        }
        return b;
    }

    public static long dwumian(int n, int k) {
        long wynik = 1;
        for (int i = 1; i <= k; i++) {
            wynik = wynik * (n - i + 1) / i;
        }

        return wynik;
    }

    public static boolean czyPierwsza(long n) {
        if (n < 2) return false;
        if (n < 4) return true;
        if (n % 2 == 0) return false;

        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }

        return true;
    }

    public static long sumaTablicy(int[] tab) {
        long acc = 0;
        for (long a : tab) {
            acc += a;
        }
        return acc;
    }
}
