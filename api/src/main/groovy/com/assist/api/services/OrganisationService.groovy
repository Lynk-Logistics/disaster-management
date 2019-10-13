package com.assist.api.services

import com.assist.api.models.OrganisationModel
import com.assist.api.repositories.OrganisationsRepository
import com.assist.api.util.OrganisationUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganisationService {

    @Autowired
    private OrganisationsRepository organisationsRepository
    @Autowired
    private UserOrganisationMappingService userOrganisationMappingService

    OrganisationModel save(OrganisationModel organisationModel){
        if(organisationModel.organisationId){
            OrganisationModel mergedModel = OrganisationUtils.merge(organisationModel,findById(organisationModel.organisationId))
            return organisationsRepository.save(mergedModel)
        }else{
            OrganisationModel savedModel = organisationsRepository.save(organisationModel)
            userOrganisationMappingService.addUser(savedModel.organisationId,organisationModel.createdBy,true,organisationModel.createdBy)
            return savedModel
        }
    }

    OrganisationModel findById(Long organisationId){
        organisationsRepository.findById(organisationId).orElse(null)
    }

    List<OrganisationModel> findAllInLocation(Long locationId){
        organisationsRepository.findByLocationId(locationId)
    }
}
