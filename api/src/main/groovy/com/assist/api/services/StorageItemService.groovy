package com.assist.api.services

import com.assist.api.models.StorageItemModel
import com.assist.api.repositories.StorageItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class StorageItemService {

    @Autowired
    private StorageItemRepository storageItemRepository

    StorageItemModel save(StorageItemModel storageItemModel){
        StorageItemModel existingModel = findByItemName(storageItemModel.itemName,storageItemModel.centreId)
        StorageItemModel updatedModel
        if(existingModel){
            existingModel.itemQuantity = storageItemModel.itemQuantity
            updatedModel = storageItemRepository.save(existingModel)
        }else{
            updatedModel = storageItemRepository.save(storageItemModel)
        }
        if(updatedModel.itemQuantity == 0){
            delete(updatedModel.storageId)
        }
        return updatedModel
    }

    StorageItemModel findByItemName(String itemName,Long centreId){
        return storageItemRepository.findByItemNameAndCentreId(itemName,centreId)
    }

    List<StorageItemModel> getDetails(Long centreId){
        return storageItemRepository.findByCentreId(centreId)
    }

    @Transactional
    @Modifying
    void delete(Long storageId){
        storageItemRepository.deleteById(storageId)
    }

}
