package uz.pdp.messenger.back.payload;

import java.util.List;
import java.util.UUID;

public record GroupDTO(UUID id, List<GroupUserInformationDTO> GroupUserInformationDTO, String groupName, String logo, String bio) implements HandlerDTO {
}
