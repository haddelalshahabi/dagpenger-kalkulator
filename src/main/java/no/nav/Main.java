package no.nav;

import no.nav.dagpenger.DagpengerKalkulator;
import no.nav.årslønn.Årslønn;
import no.nav.saksbehandling.Resultat;
import no.nav.saksbehandling.Saksbehandler;
import no.nav.saksbehandling.Spesialisering;

public class Main {
    public static void main(String[] args) {
        // Opprett en instans av kalkulatoren for å beregne dagpenger.
        DagpengerKalkulator kalkulator = new DagpengerKalkulator();
        
        // Legg til testdata for å simulere inntekter de siste tre årene.
        kalkulator.leggTilÅrslønn(new Årslønn(2023, 500000));
        kalkulator.leggTilÅrslønn(new Årslønn(2022, 450000));
        kalkulator.leggTilÅrslønn(new Årslønn(2021, 400000));

        // Print for å indikere at beregning starter.
        System.out.println("--- Beregning av dagsats ---");

        // Generer resultatet for dagpengeberegning og vis det i terminalen.
        Resultat resultat = kalkulator.genererResultat();
        System.out.printf("Dagsats beregnet: %.2f kr%n", resultat.getDagsats());
        System.out.println("Spesialisering for resultatet: " + resultat.getSpesialisering());
        System.out.println("--- Beregning fullført ---\n");

        // Opprett en saksbehandler med spesialiseringen 'INNVILGET'.
        Saksbehandler behandler = new Saksbehandler("Ola Nordmann", Spesialisering.INNVILGET);
        System.out.println("Saksbehandler: " + behandler.getNavn() + " med spesialisering: " + behandler.getSpesialisering());
        System.out.println("Legger til ubehandlede resultater for behandling...");

        // Legg til resultatet som ubehandlet hvis det samsvarer med spesialiseringen.
        behandler.leggTilUbehandletResultat(resultat);

        // Sjekk om det finnes ubehandlede resultater for saksbehandleren.
        if (behandler.hentUbehandledeResultater().isEmpty()) {
            System.out.println("Ingen ubehandlede resultater funnet for denne spesialiseringen.");
        } else {
            // Hvis det er ubehandlede resultater, behandle dem én etter én.
            System.out.println("\n--- Ubehandlede resultater funnet ---");
            for (Resultat res : behandler.hentUbehandledeResultater()) {
                System.out.printf("Behandler resultat med dagsats: %.2f kr og spesialisering: %s%n", res.getDagsats(), res.getSpesialisering());
                // Godkjenn resultatet og print en melding om at det er godkjent.
                behandler.godkjennResultat(res);
                System.out.println("Resultatet ble godkjent.");
            }
        }
        // Print for å indikere at saksbehandlingen er ferdig.
        System.out.println("\n--- Saksbehandling fullført ---");
    }
}

        // Utfører beregning av dagsats
        /*** System.out.println("---🤖 Kalkulerer dagsats... 🤖---");
        double dagsats = dagpengerKalkulator.kalkulerDagsats();
        
        // Sjekker og viser resultatet
        if (dagsats > 0) {
            System.out.println("Personen har rett på følgende dagsats: " + dagsats + " kr");
        } else {
            System.out.println("Personen har ikke rett på dagpenger.");
        }
        System.out.println("---🤖 Dagsats ferdig kalkulert 🤖---"); **/
