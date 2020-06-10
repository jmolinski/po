package elections;

import java.util.HashMap;

public class ElectionResults {
    public ElectionResults(String methodName, HashMap<Constituency, ConstituencyResults> resultsByConstituency) {
        // TODO
    }

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
    //     ciąg par (nazwa partii, liczba mandatów)
    //
    // DLA PARTII:
    //     ciag par (nazwa partii, łącznie mandatów)

    public void print() {

    }
}
