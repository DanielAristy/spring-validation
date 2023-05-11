package co.com.example.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class HeaderDTO {

    @NotEmpty
    @JsonProperty("consumidor_id")
    private String consumerId;
    @NotEmpty
    @JsonProperty("ip_invocacion")
    private String invocationIp;
    @NotEmpty
    @JsonProperty("empresa")
    private String company;
    @NotEmpty
    @JsonProperty("empresa")
    private String business;
}
