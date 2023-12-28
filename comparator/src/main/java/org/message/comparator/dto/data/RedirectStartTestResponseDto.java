package org.message.comparator.dto.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedirectStartTestResponseDto {
    private String compareApiResponse;
    private String producerApiResponse;
    private UUID testUUID;
}
