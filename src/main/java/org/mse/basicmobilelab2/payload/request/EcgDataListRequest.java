package org.mse.basicmobilelab2.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EcgDataListRequest {
    private String jwt;
}
