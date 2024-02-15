package uz.pdp.messenger.back.payload;

import java.util.List;

public record CreateGroupDTO(UserDTO admin, String groupFullName, List<UserDTO> userDTOS) {
}
