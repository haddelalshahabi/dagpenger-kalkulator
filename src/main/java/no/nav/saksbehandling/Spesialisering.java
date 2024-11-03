package no.nav.saksbehandling;
/**
 * Enum for å representere spesialiseringer som en saksbehandler kan ha når de vurderer dagpengeberegninger.
 */
public enum Spesialisering {
    // Representerer et avslag på grunn av at inntekten er for lav.
    AVSLAG_LAV_INNTEKT,

    // Representerer at dagpenger er innvilget basert på de gitte kriteriene.
    INNVILGET,

    // Representerer at dagpenger er innvilget med maksimal sats.
    INNVILGET_MAKSSATS;
}