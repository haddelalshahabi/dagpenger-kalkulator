package no.nav.årslønn;

/**
 * Representerer en persons inntekt for et gitt kalenderår.
 * Klassen holder på informasjon om hvilket år lønnen gjelder for, og selve beløpet.
 * 
 * @author Emil Elton Nilsen
 * @version 1.0
 */
public class Årslønn {
    // Året lønnen tilhører (for eksempel 2023).
    private final int åretForLønn;

    // Beløpet for årslønnen i det spesifikke året.
    private final double årslønn;

    /**
     * Konstruktør for å opprette et Årslønn-objekt med gitt år og lønnsbeløp.
     * @param åretForLønn året som lønnen gjelder for.
     * @param årslønn beløpet for årslønnen.
     */
    public Årslønn(int åretForLønn, double årslønn) {
        this.åretForLønn = åretForLønn;
        this.årslønn = årslønn;
    }

    /**
     * Henter året som lønnen gjelder for.
     * @return året for lønnen.
     */
    public int hentÅretForLønn() {
        return åretForLønn;
    }

    /**
     * Henter beløpet for årslønnen.
     * @return årslønnen som et tall.
     */
    public double hentÅrslønn() {
        return årslønn;
    }
}