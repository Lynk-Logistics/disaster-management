package com.assist.api.controllers

import com.assist.api.models.ActionsModel
import com.assist.api.pogo.dto.ActionDto
import com.assist.api.pogo.request.ActionSaveRequest
import com.assist.api.security.CurrentUser
import com.assist.api.security.UserPrincipal
import com.assist.api.services.ActionService
import com.assist.api.util.ActionsUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/action')
class ActionsController {

    @Value('${actions.default.days}')
    private static int DEFAULT_DAYS_TO_SHOW

    @Autowired
    private ActionService actionService


    @PostMapping('/')
    ActionDto save(@RequestBody ActionSaveRequest actionSaveRequest,@CurrentUser UserPrincipal userPrincipal){
        actionSaveRequest.postedByUser = userPrincipal.userId
        ActionsModel actionsModel = ActionsUtil.convertToActionsModel(actionSaveRequest)
        actionsModel = actionService.save(actionsModel)
        return new ActionDto(actionsModel)
    }

    @GetMapping('/')
    List<ActionDto> show(@RequestParam('locationId') long locationId, @RequestParam('actionType') String actionType,
                         @RequestParam(value = 'noOfDays',required = false) Integer noOfDays,
                         @RequestParam('pageNo') int pageNumber, @RequestParam('noOfRecords') int noOfRecords,
                         @RequestParam(value = 'displayForUserOnly',required = false)Boolean displayForUserOnly,
                         @CurrentUser UserPrincipal userPrincipal){
        noOfDays = noOfDays ?: DEFAULT_DAYS_TO_SHOW
        displayForUserOnly = displayForUserOnly == null ? false : displayForUserOnly
        return actionService.fetch(locationId,actionType,noOfDays,pageNumber,
                noOfRecords,displayForUserOnly,userPrincipal.userId).collect { new ActionDto(it)}
    }

    @GetMapping('/{id}')
    ActionDto view(@PathVariable('id') Long actionId){
        return new ActionDto(actionService.findById(actionId))
    }

}
