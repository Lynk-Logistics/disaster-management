package com.assist.api.repositories

import com.assist.api.models.ActionsModel
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface ActionsRepository extends PagingAndSortingRepository<ActionsModel,Long> {

    List<ActionsModel> findByActionTypeAndLocationIdAndCreatedOnBetween(String actionType, Long locationId, Date startDate, Date endDate, Pageable pageable)
    List<ActionsModel> findByPostedByUserAndActionTypeAndLocationIdAndCreatedOnBetween(Long userId,String actionType, Long locationId, Date startDate, Date endDate, Pageable pageable)
}