package no.nav.saksbehandling;
/**
 * Representerer resultatet av en dagpengeberegning, inkludert dagsats og spesialisering.
 * Inneholder også informasjon om resultatet er behandlet eller ikke.
 */
public class Resultat {
    // Dagsatsen som er beregnet for en person.
    private final double dagsats;

    // Spesialiseringen knyttet til resultatet, f.eks. om det er innvilget eller avslått.
    private final Spesialisering spesialisering;

    //Flag som angir om resultatet er blitt behandlet (godkjent/avslått).
    private boolean behandlet;

    // Konstruktør som setter dagsats og spesialisering, og markerer resultatet som ubehandlet.
    public Resultat(double dagsats, Spesialisering spesialisering) {
        this.dagsats = dagsats;
        this.spesialisering = spesialisering;
        this.behandlet = false;
    }

    // Henter dagsatsen for resultatet.
    public double getDagsats() {
        return dagsats;
    }

    // Henter spesialiseringen for resultatet.
    public Spesialisering getSpesialisering() {
        return spesialisering;
    }

    // Sjekker om resultatet er behandlet.
    public boolean erBehandlet() {
        return behandlet;
    }

    // Setter statusen til resultatet som behandlet eller ubehandlet.
    public void setBehandlet(boolean behandlet) {
        this.behandlet = behandlet;
    }
}