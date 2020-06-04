package elections;

public class VoterBuilder {
    public VoterBuilder(String firstName, String lastName, int baseConstituencyNumber, int type,
                        String extraArguments) {

                /*
        typ wyborcy, który jest reprezentowany jako liczba:
        ○ 1 - Żelazny elektorat partyjny
        ○ 2 - Żelazny elektorat kandydata
        ○ 3 - Minimalizujący jednocechowy wybierający spośród wszystkich partii
        ○ 4 - Maksymalizujący jednocechowy wybierający spośród wszystkich partii
        ○ 5 - Wszechstronny wybierający spośród wszystkich partii
        ○ 6 - Minimalizujący jednocechowy wybierający spośród jednej partii
        ○ 7 - Maksymalizujący jednocechowy wybierający spośród jednej partii
        ○ 8 - Wszechstronny wybierający spośród jednej partii

        Dodatkowo, w przypadku wyborców typu 1 i 2 w tym samym wierszu mamy jeszcze
        (po spacji) nazwę partii, a w przypadku wyborców typu 2 dodatkowo jeszcze pozycję
        kandydata na liście partii (ponieważ kandydaci mogą mieć takie samo imię i
        nazwisko, więc to partia i pozycja na liście w okręgu jednoznacznie identyfikuje
        kandydata). W przypadku wyborców typu 5 i 8 mamy za to wartości bazowe wag,
        które dany wyborca przypisuje poszczególnym cechom kandydatów: {...}
        (dla każdego wyborcy kolejność wartości wag jest taka sama). Wszystkie te wartości
        są oddzielone pojedynczą spacją i powinny być ze zbioru: {-100,-99,…,0,...,99,100}.
        W przypadku wyborców typu 8 na końcu (po wagach) powinna być jeszcze nazwa
        partii. W przypadku wyborców typu 3,4,6,7 po typie jest natomiast (po spacji) jedna
        liczba ze zbioru {1,2,...,c}, określająca która wartość cechy kandydatów powinna być
        maksymalizowana/minimalizowana, a w przypadku wyborców typu 6 i 7 potem jest
        jeszcze (po spacji) nazwa partii.
         */
    }

    public Voter build() {
        return null;
    }
}
