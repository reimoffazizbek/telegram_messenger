package uz.pdp.messenger.back.service;

import uz.pdp.messenger.back.payload.*;

import java.util.List;
import java.util.UUID;

public interface HandlerService {
    List<HandlerDTO> findAllMyHandlerByUserId(UUID id);

    ChatDTO findChatByUserId(UUID firstUser, UUID secondUser);

    ChatDTO createChat(UUID firstUserId, UUID secondUserId);

    boolean deleteChat(UUID chatId);

    GroupDTO createGroup(CreateGroupDTO dto);

    GroupDTO addUserToGroup(GroupDTO currentGroup, List<UserDTO> newUsers, UUID currentUserId);

    GroupDTO editGroupName(GroupDTO currentGroup, String newName, UUID currentUserId);

    GroupDTO deleteUserFromGroup(List<GroupUserInformationDTO> deleteUser, GroupDTO currentGroup, UserDTO currentUser);

    GroupDTO editOrCreateGroupBio(GroupDTO currentGroup, String newBio, UserDTO currentUser);
}
