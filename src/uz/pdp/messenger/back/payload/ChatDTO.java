package uz.pdp.messenger.back.payload;

import java.util.List;
import java.util.UUID;

public record ChatDTO(UUID id, List<ChatUserInformationDTO> ChatUserInformation) implements HandlerDTO {
}
