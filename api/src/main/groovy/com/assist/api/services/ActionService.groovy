package com.assist.api.services

import com.assist.api.models.ActionsModel
import com.assist.api.repositories.ActionsRepository
import com.assist.api.util.ActionsUtil
import com.assist.api.util.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ActionService {

    @Autowired
    private ActionsRepository actionsRepository

    ActionsModel save(ActionsModel actionsModel){
        if(actionsModel.actionId){
            // Updating an existing action
            ActionsModel existingModel = findById(actionsModel.actionId)
            return actionsRepository.save(ActionsUtil.merge(actionsModel,existingModel))
        }else{
            // Creating a new action
            return actionsRepository.save(actionsModel)
        }
    }

    List<ActionsModel> fetch(long locationId,String actionType,int noOfDays,int pageNumber,
                             int noOfRecords,boolean displayForUserOnly,Long userId){
        Date startDate = new Date()
        Date endDate = DateUtils.getDateBefore(noOfDays)
        Pageable pageable = PageRequest.of(pageNumber,noOfRecords,Sort.by('actionId').descending())
        if(displayForUserOnly){
            return actionsRepository.findByPostedByUserAndActionTypeAndLocationIdAndCreatedOnBetween(userId,actionType,locationId,endDate,startDate,pageable)
        }
        return actionsRepository.findByActionTypeAndLocationIdAndCreatedOnBetween(actionType,locationId,endDate,startDate,pageable)
    }

    ActionsModel findById(Long actionId){
        return actionsRepository.findById(actionId).orElse(null)
    }
}
