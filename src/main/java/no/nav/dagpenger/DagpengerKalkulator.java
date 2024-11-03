package no.nav.dagpenger;

import no.nav.grunnbeløp.GrunnbeløpVerktøy;
import no.nav.årslønn.Årslønn;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import no.nav.saksbehandling.Resultat;
import no.nav.saksbehandling.Spesialisering;

/**
 * Kalkulator for å beregne hvor mye dagpenger en person har rett på i Norge basert på dagens grunnbeløp (1G).
 * For å kvalifisere for dagpenger må en person oppfylle visse krav knyttet til inntekt de siste årene.
 */
public class DagpengerKalkulator {
    // Verktøy for å hente grunnbeløp (1G) brukt i beregninger.
    private final GrunnbeløpVerktøy grunnbeløpVerktøy;

    // Liste over årslønner som brukes for å beregne dagpenger.
    private final List<Årslønn> årslønner;

    // Konstruktør som initialiserer grunnbeløpverktøyet og årslønnlisten.
    public DagpengerKalkulator() {
        this.grunnbeløpVerktøy = new GrunnbeløpVerktøy();
        this.årslønner = new ArrayList<>();
    }

    /**
     * Beregner dagsatsen for en person hvis vedkommende har rett til dagpenger.
     * @return dagsatsen eller 0 hvis personen ikke har rett til dagpenger.
     */
    public double kalkulerDagsats() {
        if (!harRettigheterTilDagpenger()) return 0;

        int arbeidsdagerIÅret = 260;
        BeregningsMetode beregningsMetode = velgBeregningsMetode();

        switch (beregningsMetode) {
            case SISTE_ÅRSLØNN:
                return Math.ceil(hentÅrslønnVedIndeks(0).hentÅrslønn() / arbeidsdagerIÅret);
            case MAKS_ÅRLIG_DAGPENGERGRUNNLAG:
                return Math.ceil(grunnbeløpVerktøy.hentMaksÅrligDagpengegrunnlag() / arbeidsdagerIÅret);
            case GJENNOMSNITTET_AV_TRE_ÅR:
                double gjennomsnitt = summerNyligeÅrslønner(3) / 3;
                return Math.ceil(gjennomsnitt / arbeidsdagerIÅret);
            default:
                return 0;
        }
    }

    /**
     * Sjekker om en person har rett til dagpenger basert på årslønner.
     * @return true hvis personen har rett til dagpenger, ellers false.
     */
    public boolean harRettigheterTilDagpenger() {
        double sisteÅrslønn = hentÅrslønnVedIndeks(0).hentÅrslønn();
        double treÅrsGjennomsnitt = summerNyligeÅrslønner(3) / 3;

        // Sjekker om gjennomsnittet av de siste 3 årene eller siste års lønn oppfyller kravene.
        return treÅrsGjennomsnitt >= grunnbeløpVerktøy.hentTotaltGrunnbeløpForGittAntallÅr(3) ||
               sisteÅrslønn >= grunnbeløpVerktøy.hentMinimumÅrslønnForRettPåDagpenger();
    }

    /**
     * Velger den beste beregningsmetoden for dagsats basert på tilgjengelige årslønner.
     * @return beregningsmetoden som skal brukes.
     */
    public BeregningsMetode velgBeregningsMetode() {
        // Henter siste års lønn.
        double sisteÅrslønn = hentÅrslønnVedIndeks(0).hentÅrslønn(); 

        // Beregner gjennomsnittet av de siste tre årene.
        double gjennomsnittTreÅr = summerNyligeÅrslønner(3) / 3;

        // Henter maks grunnbeløp      
        double maksÅrligGrunnbeløp = grunnbeløpVerktøy.hentMaksÅrligDagpengegrunnlag();  

        System.out.println("Siste årslønn: " + sisteÅrslønn);
        System.out.println("Gjennomsnitt av tre år: " + gjennomsnittTreÅr);
        System.out.println("Maks årlig grunnbeløp: " + maksÅrligGrunnbeløp);

        if (sisteÅrslønn > maksÅrligGrunnbeløp) {
            return BeregningsMetode.MAKS_ÅRLIG_DAGPENGERGRUNNLAG;
        }
        if (sisteÅrslønn > gjennomsnittTreÅr) {
            return BeregningsMetode.SISTE_ÅRSLØNN;
        }
        return BeregningsMetode.GJENNOMSNITTET_AV_TRE_ÅR;
    }

    // Enum for å representere ulike beregningsmetoder.
    public enum BeregningsMetode {
        SISTE_ÅRSLØNN, GJENNOMSNITTET_AV_TRE_ÅR, MAKS_ÅRLIG_DAGPENGERGRUNNLAG
    }

    /**
     * Legger til en ny årslønn i listen og sorterer den i synkende rekkefølge (nyeste først).
     * @param årslønn årslønnen som skal legges til.
     */
    public void leggTilÅrslønn(Årslønn årslønn) {
        årslønner.add(årslønn);
        årslønner.sort(Comparator.comparingInt(Årslønn::hentÅretForLønn).reversed());
    }

    /**
     * Henter en årslønn basert på dens posisjon i listen.
     * @param indeks posisjonen i listen.
     * @return årslønnen på gitt indeks.
     */
    private Årslønn hentÅrslønnVedIndeks(int indeks) {
        if (indeks < årslønner.size()) {
            return årslønner.get(indeks);
        }
        throw new IndexOutOfBoundsException("Ingen årslønn registrert på indeks: " + indeks);
    }

    /**
     * Summerer sammen de siste N antall årslønner.
     * @param antallÅrÅSummere antall år som skal summeres.
     * @return summen av årslønner for de siste N årene.
     */    
    private double summerNyligeÅrslønner(int antallÅrÅSummere) {
        return årslønner.stream().limit(antallÅrÅSummere).mapToDouble(Årslønn::hentÅrslønn).sum();
    }

    /**
     * Genererer et resultat for beregningen, inkludert spesialisering basert på dagsatsen.
     * @return et Resultat-objekt med dagsats og spesialisering.
     */
    public Resultat genererResultat() {
        double dagsats = kalkulerDagsats();
        Spesialisering spesialisering;

        // Bestemmer spesialiseringen basert på dagsatsen.
        if (dagsats == 0) {
            spesialisering = Spesialisering.AVSLAG_LAV_INNTEKT;
        } else if (dagsats >= grunnbeløpVerktøy.hentMaksÅrligDagpengegrunnlag() / 260) {
            spesialisering = Spesialisering.INNVILGET_MAKSSATS;
        } else {
            spesialisering = Spesialisering.INNVILGET;
        }
        return new Resultat(dagsats, spesialisering);
    }
}