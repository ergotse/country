package se.ergot.country.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BelongsToVariant {
    @JsonProperty("colony") COLONY,
    @JsonProperty("protectorate") PROTECTORATE,
    @JsonProperty("mandate") MANDATE,
    @JsonProperty("personalUnion") PERSONAL_UNION,
    @JsonProperty("condominium") CONDOMINIUM,
    @JsonProperty("associatedState") ASSOCIATED_STATE,
    @JsonProperty("occupiedTerritory") OCCUPIED_TERRITORY
}
