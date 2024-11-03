package no.nav.grunnbeløp;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Har ansvaret for å kontakte grunnbeløp API'et til NAV og henter dagens grunnbeløp.
 * Logger brukes for å rapportere eventuelle feil under kjøring.
 * 
 * @author Emil Elton Nilsen
 * @version 1.0
 */
public class GrunnbeløpAPI {
    // Logger for å registrere meldinger og feilsituasjoner.
    private static final Logger LOGGER = Logger.getLogger(GrunnbeløpAPI.class.getName());

    // Last inn miljøvariabler fra en .env-fil for å beskytte sensitiv informasjon.
    private static final Dotenv DOTENV = Dotenv.load();

    // URL for å kontakte NAV sitt grunnbeløp API. Den hentes fra miljøvariabelen G_API_URL.
    private static final String API_URL = DOTENV.get("G_API_URL");

    // HttpClient brukes til å sende HTTP-forespørsler.
    private final HttpClient httpClient;

    // Konstruktør som oppretter en ny instans av HttpClient.
    public GrunnbeløpAPI() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Kontakter grunnbeløp API'et til NAV og henter dagens grunnbeløp.
     * Metoden sender en HTTP-forespørsel, mottar en respons, og konverterer denne til et <code>JSONObject</code>
     * for å hente ut grunnbeløpet.
     * @return dagens grunnbeløp.
     * @throws IOException hvis en I/O-feil oppstår under kommunikasjon med API.
     * @throws InterruptedException hvis tråden blir avbrutt under venting på respons.
     */
    public double hentGrunnbeløp() throws IOException, InterruptedException {
        // Sjekker om API_URL er konfigurert. Hvis ikke, logges en alvorlig feil og et unntak kastes.
        if (API_URL == null) {
            LOGGER.log(Level.SEVERE, "API URL er ikke satt. Kontroller miljøvariabelen G_API_URL.");
            throw new IllegalStateException("API URL er ikke konfigurert.");
        }

        // Bygger en HTTP-forespørsel basert på API-URL.
        HttpRequest request = HttpRequest.newBuilder(URI.create(API_URL)).build();
        try {
            // Sender forespørselen og mottar responsen som en streng.
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Sjekker om HTTP-statuskoden indikerer suksess (200). Logger og kaster en feil hvis ikke.
            if (response.statusCode() != 200) {
                LOGGER.log(Level.SEVERE, "Feil ved henting av grunnbeløp: HTTP " + response.statusCode());
                throw new IOException("Kunne ikke hente grunnbeløp: Feilkode " + response.statusCode());
            }

            // Konverterer responsen til et JSON-objekt og henter verdien for grunnbeløpet.
            JSONObject json = new JSONObject(response.body());
            return json.getDouble("grunnbeløp");

        } catch (IOException | InterruptedException e) {
            // Logger feil og kaster unntaket videre slik at metoden som kaller denne kan håndtere det.
            LOGGER.log(Level.SEVERE, "En feil oppstod ved kontakt med grunnbeløp API'et.", e);
            throw e;
        }
    }
}