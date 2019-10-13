package com.assist.api.services

import com.assist.api.models.UserModel
import com.assist.api.models.UserOrganisationMappingModel
import com.assist.api.models.UserOrganisationMappingModelPK
import com.assist.api.repositories.UserOrganisationMappingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserOrganisationMappingService {

    @Autowired
    private UserOrganisationMappingRepository userOrganisationMappingRepository
    @Autowired
    private UserService userService

    void addUser(long orgId,long userId,boolean isAdmin,long addedBy){
        UserOrganisationMappingModel userOrganisationMappingModel = new UserOrganisationMappingModel(
                organisationId : orgId,
                userId : userId,
                isAdmin : isAdmin,
                addedBy : addedBy
        )
        userOrganisationMappingRepository.save(userOrganisationMappingModel)
    }

    void addUser(String userIdentifier,long orgId,boolean isAdmin,long addedBy){
        UserModel userModel = userService.findUser(userIdentifier)
        if(userModel){
            if(userOrganisationMappingRepository.findById(new UserOrganisationMappingModelPK(organisationId:orgId,userId:userModel.userId))){
                throw new Exception('User is already mapped to the organisation')
            }
            addUser(orgId,userModel.userId,isAdmin,addedBy)
        }else {
            throw new Exception('No user found')
        }
    }

}
