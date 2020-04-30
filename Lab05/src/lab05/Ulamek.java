package lab05;


public class Ulamek {
    private long licznik;
    private long mianownik;

    public Ulamek(int licznik) {
        this.licznik = licznik;
        this.mianownik = 1;
        zredukujDoPostaciNieskracalnej();
    }

    public Ulamek(int licznik, int mianownik) throws IllegalArgumentException {
        this.licznik = licznik;
        if (mianownik == 0) {
            throw new IllegalArgumentException("mianownik nie moze byc rowny 0");
        }
        this.mianownik = mianownik;
        zredukujDoPostaciNieskracalnej();
    }

    private Ulamek(long licznik, long mianownik) {
        this.licznik = licznik;
        this.mianownik = mianownik;
        zredukujDoPostaciNieskracalnej();
    }

    private void zredukujDoPostaciNieskracalnej() {
        long nwd = Math.abs(najwiekszyWspolnyDzielnik(this.licznik, this.mianownik));
        long nowyLicznik = Math.abs(this.licznik) / nwd;
        long nowyMianownik = Math.abs(this.mianownik) / nwd;
        this.licznik = nowyLicznik * Long.signum(this.licznik) * Long.signum(this.mianownik);
        this.mianownik = Math.abs(nowyMianownik);
    }

    private long najwiekszyWspolnyDzielnik(long a, long b) {
        return a == 0 ? b : najwiekszyWspolnyDzielnik(b % a, a);
    }

    public Ulamek dodaj(Ulamek u) {
        long nwd = najwiekszyWspolnyDzielnik(this.mianownik, u.mianownik);
        long nowyMianownik = (this.mianownik * u.mianownik) / nwd;
        long nowyLicznik = this.licznik * (nowyMianownik / this.mianownik) + u.licznik * (nowyMianownik / u.mianownik);
        return new Ulamek((int) nowyLicznik, (int) nowyMianownik);
    }

    public Ulamek dodaj(int n) {
        return dodaj(new Ulamek(n));
    }

    public Ulamek odejmij(Ulamek u) {
        Ulamek przeciwna = new Ulamek(-u.licznik, u.mianownik);
        return dodaj(przeciwna);
    }

    public Ulamek odejmij(int n) {
        return odejmij(new Ulamek(n));
    }

    public Ulamek pomnoz(Ulamek u) {
        long nowyMianownik = this.mianownik * u.mianownik;
        long nowyLicznik = this.licznik * u.licznik;
        return new Ulamek(nowyLicznik, nowyMianownik);
    }

    public Ulamek pomnoz(int n) {
        return pomnoz(new Ulamek(n));
    }

    public Ulamek podziel(Ulamek u) {
        if (u.licznik == 0) {
            throw new ArithmeticException("dzielenie przez 0");
        }
        Ulamek tmp = new Ulamek(u.mianownik, u.licznik);
        return this.pomnoz(tmp);
    }

    public Ulamek podziel(int n) {
        return podziel(new Ulamek(n));
    }

    public int compareTo(Ulamek u) {
        if (this.licznik == u.licznik && this.mianownik == u.mianownik) {
            return 0;
        }

        // ulamek zawsze jest w wersji znormalizowanej
        // mianownik > 0, znak ulamka == znak licznika
        int this_sgn = Long.signum(this.licznik);
        int u_sgn = Long.signum(u.licznik);

        if (this_sgn != u_sgn) {
            if (this_sgn == 1) {
                return 1;
            }
            if (u_sgn == 1) {
                return -1;
            }
            return this_sgn == 0 ? 1 : -1;
        }

        return (this.odejmij(u).licznik > 0) ? 1 : -1;
    }

    public String toString() {
        // ulamek zawsze jest w wersji znormalizowanej
        // mianownik > 0, znak ulamka == znak licznika
        if (this.mianownik == 1 || this.licznik == 0) {
            return String.valueOf(this.licznik);
        }
        return String.valueOf(this.licznik) + '/' + String.valueOf(this.mianownik);
    }
}