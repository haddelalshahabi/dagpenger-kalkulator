package no.nav.grunnbeløp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Verktøyklasse med forskjellige hjelpemetoder for å kalkulere grunnbeløpsverdier,
 * som brukes i prosessen for å beregne hvilken dagsats en person har rett på.
 * Grunnbeløpet hentes fra NAV sitt grunnbeløp API.
 * 
 * @author Emil Elton Nilsen
 * @version 1.0
 */
public class GrunnbeløpVerktøy {
    // Lagrer grunnbeløpet som brukes i beregningene.
    private double grunnbeløp;

    /**
     * Standardkonstruktør som henter grunnbeløpet fra API'et.
     * Setter grunnbeløpet til 0 hvis det oppstår en feil under henting.
     */
    public GrunnbeløpVerktøy() {
        try {
            this.grunnbeløp = new GrunnbeløpAPI().hentGrunnbeløp();
        } catch (IOException | InterruptedException e) {
            // Setter grunnbeløpet til 0 som en standardverdi ved feil og logger feilen.
            Logger.getLogger(GrunnbeløpVerktøy.class.getName()).log(Level.SEVERE, "Feil ved henting av grunnbeløp", e);
            this.grunnbeløp = 0; 
        }
    }

    /**
     * Konstruktør for testing som lar brukeren sette grunnbeløpet manuelt.
     * @param grunnbeløpForTest grunnbeløp som brukes for testing.
     */
    public GrunnbeløpVerktøy(double grunnbeløpForTest) {
        this.grunnbeløp = grunnbeløpForTest;
    }

    /**
     * Beregner det totale grunnbeløpet over et gitt antall år.
     * @param antallÅr antall år som skal brukes i beregningen.
     * @return total grunnbeløp for det gitte antall år.
     */
    public double hentTotaltGrunnbeløpForGittAntallÅr(int antallÅr) {
        return this.grunnbeløp * antallÅr;
    }

    /**
     * Beregner minimum årslønn som kreves for å ha rett til dagpenger, som er 1.5 ganger grunnbeløpet.
     * @return minimum årslønn for å ha rett på dagpenger.
     */
    public double hentMinimumÅrslønnForRettPåDagpenger() {
        return this.grunnbeløp * 1.5;
    }

    /**
     * Beregner maksimal årlig dagpengegrunnlag, som er 6 ganger grunnbeløpet.
     * @return maksimalt årlig dagpengegrunnlag.
     */
    public double hentMaksÅrligDagpengegrunnlag() {
        return this.grunnbeløp * 6;
    }
}
