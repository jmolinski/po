package egzamin;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class Bank {
    private final String nazwa;
    private Konto[] konta;

    public Bank(String nazwa) {
        this.nazwa = nazwa;
    }

    public void przetworzZleceniaPrzelewu(Collection<ZleceniePrzelewu> zlecenia) {
        for(var zlecenie : zlecenia) {
            try {
                przetworzZleceniePrzelewu(zlecenie);
            }
            catch (PowaznaAwaria e) {
                System.out.println("Wystapila powazna awaria podczas przetwarzania zlecenia " + zlecenie);
                continue;
            }
            catch (MaloPowaznAwaria e) {
                System.out.println("Wystapil problem podczas przetwarzania zlecenia " + zlecenie);
                continue;
            }

            System.out.println("Wykonano zlecenie " + zlecenia);
        }

        wypiszStanWszystkichKont();
    }

    public void koniecMiesiaca() {
        przetworzZleceniaPrzelewu(utworzZleceniaSplatyRat());
        przetworzZleceniaPrzelewu(utworzZleceniaSplatyKontKartowych());
        doliczOdsetkiDoKont();

        wypiszStanWszystkichKont();
    }

    private Collection<ZleceniePrzelewu> utworzZleceniaSplatyRat() {
        var listaZlecen = new ArrayList<ZleceniePrzelewu>();

        for (var konto : konta) {
            if (konto.czyKontoRatalne()) {
                KontoRatalne k = (KontoRatalne) konto;
                listaZlecen.add(k.przygotujZleceniePrzelewuRaty());
            }
        }

        return listaZlecen;
    }

    private Collection<ZleceniePrzelewu> utworzZleceniaSplatyKontKartowych() {
        var listaZlecen = new ArrayList<ZleceniePrzelewu>();

        for (var konto : konta) {
            if (konto.czyKontoKredytowe() && !konto.czyKontoRatalne()) {
                KontoKartowe k = (KontoKartowe) konto;
                listaZlecen.add(k.przygotujZlecenieSplatyKarty());
            }
        }

        return listaZlecen;
    }

    private void doliczOdsetkiDoKont() {
        for (var konto : konta) {
            if (konto.czyKontoOszczednosciowe()) {
                KontoOszczednosciowe k = (KontoOszczednosciowe)konto;
                konto.wplac(k.waluta(), ((KontoOszczednosciowe) konto).nalezneOdsetkiZaMiesiac());
            }
        }
    }

    private void wypiszStanWszystkichKont() {
        for(var konto : konta) {
            System.out.println(konto);
        }
    }

    private void przetworzZleceniePrzelewu(ZleceniePrzelewu zlecenie) throws PowaznaAwaria, MaloPowaznAwaria {
        Konto zKonta = null;
        Konto naKonto = null;

        for (var k : konta) {
            if (k.numer().equals(zlecenie.numerKontaDocelowego())) {
                naKonto = k;
            }
            if (k.numer().equals(zlecenie.numerKontaWyjsciowego())) {
                zKonta = k;
            }
        }

        if (zKonta == null || naKonto == null) {
            throw new PowaznaAwaria();
        }

        if (!zKonta.czyWyplataMozliwa(zlecenie.walutaPrzelewu(), zlecenie.kwota()) || !naKonto.czyWplataMozliwa(zlecenie.walutaPrzelewu(), zlecenie.kwota())) {
            throw new MaloPowaznAwaria();
        }

        zKonta.wyplac(zlecenie.walutaPrzelewu(), zlecenie.kwota());
        naKonto.wplac(zlecenie.walutaPrzelewu(), zlecenie.kwota());
    }
}

abstract class KontoDoPrzechowywania extends PojedynczeKonto {
    public KontoDoPrzechowywania(String numer, String wlasciciel, String waluta) {
        super(numer, wlasciciel, waluta);
    }

    public boolean czyWplataMozliwa(String waluta, int kwota) {
        return this.waluta.equals(waluta);
    }

    public boolean wplac(String waluta, int kwota) {
        if (!czyWplataMozliwa(waluta, kwota)) {
            return false;
        }
        this.saldo += kwota;
        return true;
    }
}

abstract class KontoKredytowe extends PojedynczeKonto {
    protected KontoBiezace powiazaneKonto;

    public boolean czyKontoKredytowe() {
        return true;
    }

    public boolean czyWplataMozliwa(String waluta, int kwota) {
        return false;
    }

    public boolean wplac(String waluta, int kwota) {
        return false;
    }

    public KontoKredytowe(String numer, String wlasciciel, String waluta, KontoBiezace powiazaneKonto) {
        super(numer, wlasciciel, waluta);
        this.powiazaneKonto = powiazaneKonto;
    }
}

class KontoBiezace extends KontoDoPrzechowywania {
    public String toString() {
        return "TODO";
    }

    public KontoBiezace(String numer, String wlasciciel, String waluta) {
        super(numer, wlasciciel, waluta);
    }

    public boolean wyplac(String waluta, int kwota) {
        if (!czyWyplataMozliwa(waluta, kwota)) {
            return false;
        }
        this.saldo -= kwota;
        return true;
    }

    public boolean czyWyplataMozliwa(String waluta, int kwota) {
        return (waluta.equals(this.waluta) && saldo >= 0 && kwota <= this.saldo);
    }
}

class KontoRatalne extends KontoKredytowe {
    private final int poczatkowaKwotaKredytu;
    private final float stopaOprocentowaniaRoczna;
    private final int liczbaRat;

    public String toString() {
        return "TODO";
    }

    public KontoRatalne(String numer, String wlasciciel, String waluta, KontoBiezace powiazaneKonto,
                        int kwotaKredytu, int liczbaRat, float stopaOprocentowaniaRoczna) {
        super(numer, wlasciciel, waluta, powiazaneKonto);
        this.poczatkowaKwotaKredytu = kwotaKredytu;
        this.liczbaRat = liczbaRat;
        this.stopaOprocentowaniaRoczna = stopaOprocentowaniaRoczna;
    }

    public boolean wyplac(String waluta, int kwota) {
        return false;
    }

    public boolean czyWyplataMozliwa(String waluta, int kwota) {
        return false;
    }

    public boolean splacRate(String waluta, int kwota) {
        if (czySplataRatyMozliwa(waluta, kwota)) {
            this.saldo += kwota;
            return true;
        }
        return false;
    }

    public boolean czySplataRatyMozliwa(String waluta, int kwota) {
        return waluta.equals(this.waluta) && kwota <= (-saldo);
    }

    public int naleznaRataZaAktualnyMiesiac() {
        // TODO
        return 0;
    }

    public boolean czyKontoRatalne() {
        return true;
    }

    public ZleceniePrzelewu przygotujZleceniePrzelewuRaty() {
        return new ZleceniePrzelewu(naleznaRataZaAktualnyMiesiac(), waluta, powiazaneKonto.numer(), numer);
    }
}

class KontoOszczednosciowe extends KontoDoPrzechowywania {
    float oprocentowanie;

    int nalezneOdsetkiZaMiesiac() {
        // TODO
        return 0;
    }

    public String toString() {
        return "TODO";
    }

    public KontoOszczednosciowe(String numer, String wlasciciel, String waluta, float oprocentowanie) {
        super(numer, wlasciciel, waluta);
        this.oprocentowanie = oprocentowanie;
    }

    public boolean wyplac(String waluta, int kwota) {
        return false;
    }

    public boolean czyWyplataMozliwa(String waluta, int kwota) {
        return false;
    }

    public boolean czyKontoOszczednosciowe() {
        return true;
    }
}

class KontoKartowe extends KontoKredytowe {
    private final int limitKarty;

    public String toString() {
        return "TODO";
    }

    public KontoKartowe(String numer, String wlasciciel, String waluta, KontoBiezace powiazaneKonto, int limitKarty) {
        super(numer, wlasciciel, waluta, powiazaneKonto);
        this.limitKarty = limitKarty;

    }

    public boolean wyplac(String waluta, int kwota) {
        if (czyWyplataMozliwa(waluta, kwota)) {
            this.saldo -= kwota;
            return true;
        }
        return false;
    }

    public boolean czyWyplataMozliwa(String waluta, int kwota) {
        return waluta.equals(this.waluta) && kwota <= this.limitKarty;
    }

    public ZleceniePrzelewu przygotujZlecenieSplatyKarty() {
        return new ZleceniePrzelewu(-saldo, waluta, powiazaneKonto.numer(), numer);
    }
}

class KontoZagregowane extends Konto {
    private final Map<String, PojedynczeKonto> kontaSkladowe;

    public KontoZagregowane(String numer, String wlasciciel) {
        super(numer, wlasciciel);
        this.kontaSkladowe = new HashMap<String, PojedynczeKonto>();

    }

    public boolean dodajKonto(PojedynczeKonto konto) {
        if (this.kontaSkladowe.containsKey(konto.waluta())) {
            return false;
        }
        this.kontaSkladowe.put(konto.waluta(), konto);
        return true;
    }

    private PojedynczeKonto wezKonto(String waluta) {
        if (!kontaSkladowe.containsKey(waluta)) {
            return null;
        }
        return kontaSkladowe.get(waluta);
    }

    public boolean wyplac(String waluta, int kwota) {
        var konto = wezKonto(waluta);
        if (konto == null || !konto.czyWyplataMozliwa(waluta, kwota)) {
            return false;
        }
        return konto.wyplac(waluta, kwota);
    }

    public boolean wplac(String waluta, int kwota) {
        var konto = wezKonto(waluta);
        if (konto == null || !konto.czyWplataMozliwa(waluta, kwota)) {
            return false;
        }
        return konto.wplac(waluta, kwota);
    }

    public boolean czyWplataMozliwa(String waluta, int kwota) {
        var konto = wezKonto(waluta);
        if (konto == null) {
            return false;
        }
        return konto.czyWplataMozliwa(waluta, kwota);
    }

    public boolean czyWyplataMozliwa(String waluta, int kwota) {
        var konto = wezKonto(waluta);
        if (konto == null) {
            return false;
        }
        return konto.czyWyplataMozliwa(waluta, kwota);
    }
}

abstract class PojedynczeKonto extends Konto {
    protected String waluta;
    protected int saldo;

    public int saldo() {
        return saldo;
    }

    public String waluta() {
        return waluta;
    }

    public PojedynczeKonto(String numer, String wlasciciel, String waluta) {
        super(numer, wlasciciel);
        this.waluta = waluta;
        this.saldo = 0;
    }
}


abstract class Konto {
    protected String numer;

    public String numer() {
        return numer;
    }

    protected String wlasciciel;

    public String wlasciciel() {
        return wlasciciel;
    }

    public Konto(String numer, String wlasciciel) {
        this.numer = numer;
        this.wlasciciel = wlasciciel;
    }

    abstract public boolean wyplac(String waluta, int kwota);

    abstract public boolean wplac(String waluta, int kwota);

    abstract public boolean czyWplataMozliwa(String waluta, int kwota);

    abstract public boolean czyWyplataMozliwa(String waluta, int kwota);

    public boolean splacRate(String waluta, int kwota) {
        return false;
    }

    public boolean czySplataRatyMozliwa(String waluta, int kwota) {
        return false;
    }

    public boolean czyKontoRatalne() {
        return false;
    }

    public boolean czyKontoKredytowe() {
        return false;
    }

    public boolean czyKontoOszczednosciowe() {
        return false;
    }
}


class ZleceniePrzelewu {
    private final int kwota;
    private final String numerKontaWyjsciowego;
    private final String numerKontaDocelowego;
    private final String waluta;

    public ZleceniePrzelewu(int kwota, String waluta, String zKonta, String naKonto) {
        this.kwota = kwota;
        this.numerKontaWyjsciowego = zKonta;
        this.numerKontaDocelowego = naKonto;
        this.waluta = waluta;
    }

    public int kwota() {
        return kwota;
    }

    public String numerKontaWyjsciowego() {
        return numerKontaWyjsciowego;
    }

    public String numerKontaDocelowego() {
        return numerKontaDocelowego;
    }

    public String walutaPrzelewu() {
        return waluta;
    }
}

class PowaznaAwaria extends Exception {

}

class MaloPowaznAwaria extends Exception {

}


public class Main {

    public static void main(String[] args) {
        // ta klasa jest tu tylko po to, żeby edytor nie wyświetlał błędu
    }
}
