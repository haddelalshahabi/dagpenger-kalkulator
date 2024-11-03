package no.nav.saksbehandling;

import java.util.ArrayList;
import java.util.List;

/**
 * Representerer en saksbehandler som kan godkjenne eller avslå resultater.
 * Saksbehandleren har et navn og en spesialisering som definerer hvilke resultater de kan behandle.
 */
public class Saksbehandler {
    // Navnet på saksbehandleren.
    private final String navn;

    // Spesialiseringen til saksbehandleren (f.eks. 'INNVILGET', 'AVSLAG_LAV_INNTEKT').
    private final Spesialisering spesialisering;

    // Liste over ubehandlede resultater som saksbehandleren kan behandle.
    private final List<Resultat> ubehandledeResultater = new ArrayList<>();

    // Konstruktør som oppretter en saksbehandler med et navn og en spesialisering.
    public Saksbehandler(String navn, Spesialisering spesialisering) {
        this.navn = navn;
        this.spesialisering = spesialisering;
    }

    // Henter navnet til saksbehandleren.
    public String getNavn() {
        return navn;
    }

    // Henter spesialiseringen til saksbehandleren.
    public Spesialisering getSpesialisering() {
        return spesialisering;
    }

    /**
     * Legger til et ubehandlet resultat for saksbehandleren, hvis det samsvarer med saksbehandlerens spesialisering
     * og resultatet ikke allerede er behandlet.
     * @param resultat resultatet som skal legges til.
     */
    public void leggTilUbehandletResultat(Resultat resultat) {
        if (resultat.getSpesialisering() == spesialisering && !resultat.erBehandlet()) {
            ubehandledeResultater.add(resultat);
        }
    }

    /**
     * Henter alle ubehandlede resultater som saksbehandleren kan behandle.
     * @return en liste over ubehandlede resultater.
     */
    public List<Resultat> hentUbehandledeResultater() {
        return new ArrayList<>(ubehandledeResultater);
    }

    /**
     * Godkjenner et resultat og fjerner det fra listen over ubehandlede resultater.
     * @param resultat resultatet som skal godkjennes.
     */
    public void godkjennResultat(Resultat resultat) {
        if (ubehandledeResultater.contains(resultat)) {
            resultat.setBehandlet(true);
            ubehandledeResultater.remove(resultat);
            System.out.println("Resultatet er godkjent av " + navn);
        }
    }

    /**
     * Avslår et resultat og fjerner det fra listen over ubehandlede resultater.
     * @param resultat resultatet som skal avslås.
     */
    public void avslåResultat(Resultat resultat) {
        if (ubehandledeResultater.contains(resultat)) {
            resultat.setBehandlet(true);
            ubehandledeResultater.remove(resultat);
            System.out.println("Resultatet er avslått av " + navn);
        }
    }
}
