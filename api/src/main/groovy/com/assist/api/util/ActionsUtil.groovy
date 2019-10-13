package com.assist.api.util

import com.assist.api.models.ActionsModel
import com.assist.api.pogo.request.ActionSaveRequest

class ActionsUtil {

    static ActionsModel convertToActionsModel(ActionSaveRequest actionSaveRequest){
        ActionsModel actionsModel = new ActionsModel()
        actionsModel.actionId = actionSaveRequest.actionId
        actionsModel.actionType = actionSaveRequest.actionType
        actionsModel.description = actionSaveRequest.description
        actionsModel.itemName = actionSaveRequest.itemName
        actionsModel.itemQuantity = actionSaveRequest.itemQuantity
        actionsModel.postedByUser = actionSaveRequest.postedByUser
        actionsModel.postedByOrganisation = actionSaveRequest.postedByOrganisation
        actionsModel.fulfilled = actionSaveRequest.fulfilled
        actionsModel.locationId = actionSaveRequest.locationId
        actionsModel.latitude = actionSaveRequest.latitude
        actionsModel.longitude = actionSaveRequest.longitude
        actionsModel.fileId = actionSaveRequest.fileId
        return actionsModel
    }

    static ActionsModel merge(ActionsModel newRequest,ActionsModel previousModel){
        ActionsModel actionsModel = new ActionsModel()
        actionsModel.actionId = newRequest.actionId
        actionsModel.actionType = newRequest.actionType ?: previousModel.actionType
        actionsModel.description = newRequest.description ?: previousModel.description
        actionsModel.itemName = newRequest.itemName ?: previousModel.itemName
        actionsModel.itemQuantity = newRequest.itemQuantity ?: previousModel.itemQuantity
        actionsModel.postedByUser = newRequest.postedByUser ?: previousModel.postedByUser
        actionsModel.postedByOrganisation = newRequest.postedByOrganisation ?: previousModel.postedByOrganisation
        actionsModel.fulfilled = newRequest.fulfilled != null ?: previousModel.fulfilled
        actionsModel.locationId = newRequest.locationId ?: previousModel.locationId
        actionsModel.latitude = newRequest.latitude ?: previousModel.latitude
        actionsModel.longitude = newRequest.longitude ?: previousModel.longitude
        actionsModel.fileId = newRequest.fileId ?: previousModel.fileId
        return actionsModel
    }
}
