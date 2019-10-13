package com.assist.api.util

import com.assist.api.models.OrganisationModel
import com.assist.api.pogo.request.OrganisationSaveRequest

class OrganisationUtils {

    static OrganisationModel create(OrganisationSaveRequest organisationSaveRequest,Long userId){
        OrganisationModel organisationModel = new OrganisationModel()
        organisationModel.organisationId = organisationSaveRequest.organisationId
        organisationModel.name = organisationSaveRequest.name
        organisationModel.url = organisationSaveRequest.url
        organisationModel.locationId = organisationSaveRequest.locationId
        organisationModel.createdBy = userId
        organisationModel.description = organisationSaveRequest.description
        return organisationModel
    }

    static OrganisationModel merge(OrganisationModel saveRequest,OrganisationModel existingModel){
        if(existingModel && saveRequest){
            OrganisationModel organisationModel = new OrganisationModel()
            organisationModel.organisationId = saveRequest.organisationId
            organisationModel.name = saveRequest.name ?: existingModel.name
            organisationModel.url = saveRequest.url ?: existingModel.url
            organisationModel.locationId = saveRequest.locationId ?: existingModel.locationId
            organisationModel.description = saveRequest.description ?: existingModel.description
            return organisationModel
        }else if(existingModel){
            return existingModel
        }else if(saveRequest){
            return saveRequest
        }
    }
}
