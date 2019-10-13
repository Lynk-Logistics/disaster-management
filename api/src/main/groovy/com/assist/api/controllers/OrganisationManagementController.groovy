package com.assist.api.controllers

import com.assist.api.pogo.dto.OrganisationDetailsDto
import com.assist.api.pogo.dto.OrganisationDto
import com.assist.api.pogo.request.OrganisationSaveRequest
import com.assist.api.pogo.response.GenericResponse
import com.assist.api.security.CurrentUser
import com.assist.api.security.UserPrincipal
import com.assist.api.services.OrganisationService
import com.assist.api.services.UserOrganisationMappingService
import com.assist.api.util.OrganisationUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/organisation')
class OrganisationManagementController {

    private static final Logger logger = LoggerFactory.getLogger(OrganisationManagementController)

    @Autowired
    private OrganisationService organisationService
    @Autowired
    private UserOrganisationMappingService userOrganisationMappingService

    @PostMapping('/')
    OrganisationDetailsDto save(@RequestBody OrganisationSaveRequest organisationSaveRequest,@CurrentUser UserPrincipal userPrincipal){
        return new OrganisationDetailsDto(organisationService.save(OrganisationUtils.create(organisationSaveRequest,userPrincipal.userId)))
    }

    @GetMapping('/{id}')
    OrganisationDetailsDto view(@PathVariable('id')long organisationId){
        return new OrganisationDetailsDto(organisationService.findById(organisationId))
    }

    @PostMapping('/addMember')
    GenericResponse addMember(@RequestParam('userIdentifier') String userIdentifier,@RequestParam('orgId') Long orgId,
                   @RequestParam('isAdmin') boolean isAdmin,@CurrentUser UserPrincipal userPrincipal){
        GenericResponse genericResponse = new GenericResponse()
        try {
            userOrganisationMappingService.addUser(userIdentifier,orgId,isAdmin,userPrincipal.userId)
            genericResponse.status = true
            genericResponse.message = 'User added successfully'
        }catch (any){
            logger.error("Error while adding user ${userIdentifier} to org ${orgId}",any)
            genericResponse.message = any.message
        }
        genericResponse
    }

    @GetMapping('/show/{locationId}')
    List<OrganisationDto> list(@PathVariable('locationId')Long locationId){
        return organisationService.findAllInLocation(locationId).collect {new OrganisationDto(it)}
    }

}
