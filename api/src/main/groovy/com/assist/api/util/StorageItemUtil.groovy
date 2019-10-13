package com.assist.api.util

import com.assist.api.models.StorageItemModel
import com.assist.api.pogo.request.StorageInventoryUpdationRequest

class StorageItemUtil {

    static StorageItemModel convert(StorageInventoryUpdationRequest storageInventoryUpdationRequest){
        StorageItemModel storageItemModel = new StorageItemModel()
        storageItemModel.storageId = storageInventoryUpdationRequest.storageId
        storageItemModel.centreId = storageInventoryUpdationRequest.centreId
        storageItemModel.itemName = storageInventoryUpdationRequest.itemName
        storageItemModel.itemQuantity = storageInventoryUpdationRequest.itemQuantity
        return storageItemModel
    }
}
