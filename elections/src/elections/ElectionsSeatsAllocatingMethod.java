package elections;

import java.util.HashMap;

abstract public class ElectionsSeatsAllocatingMethod {
    private final HashMap<Constituency, ConstituencyResults> resultsByConstituency;
    protected final Constituency[] constituencies;
    protected final Party[] parties;

    protected ElectionsSeatsAllocatingMethod(Constituency[] constituencies, Party[] parties) {
        this.constituencies = constituencies;
        this.parties = parties;

        this.resultsByConstituency = castVotesInConstituencies();
    }

    private HashMap<Constituency, ConstituencyResults> castVotesInConstituencies() {
        return null;  // TODO
    }

    abstract public ElectionResults getResults();
}

class ConstituencyResults {

}

class DHondtMethod extends ElectionsSeatsAllocatingMethod {

    //W wyniku dla każdej z 3 metod przeliczania głosów na mandaty program powinien wypisać
    //w kolejnych wierszach:
    //● nazwę metody przeliczania głosów
    //● dla każdego okręgu wyborczego (podstawowego lub scalonego - można przyjąć
    //dowolnie):
    //○ nr okręgu wyborczego (w przypadku scalonego okręgu można podać 2
    //numery)
    //○ imię i nazwisko wyborcy, imię i nazwisko kandydata, na którego głosował (po
    //1 wierszu na wyborcę)
    //○ imię i nazwisko kandydata, jego partię i numer na liście oraz łączną liczbę
    //głosów na niego (po 1 wierszu na kandydata)
    //○ ciąg par (nazwa partii, liczba mandatów z danego okręgu)
    //● łącznie (dla wszystkich okręgów): ciąg par (nazwa partii, liczba mandatów ze
    // wszystkich okręgów)

    // Metoda
    // DLA OKREGU:
    //     nr okregu
    //     imie wyborcy, imie kandydata glosu
    //     imie kandydata, partie, numer na liscie i liczbe glosow
    //     ciąg par (nazwa partii, liczba glosow)
    //
    // DLA PARTII:
    //     ciag par (nazwa partii, łącznie mandatów)

    public DHondtMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }

    @Override
    public ElectionResults getResults() {
        return null;  // # TODO
    }
}


class SainteLagueMethod extends ElectionsSeatsAllocatingMethod {
    public SainteLagueMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }
}


class HareNiemeyerMethod extends ElectionsSeatsAllocatingMethod {
    public HareNiemeyerMethod(Constituency[] constituencies, Party[] parties) {
        super(constituencies, parties);
    }
}