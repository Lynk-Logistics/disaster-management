package com.assist.api.pogo.dto

import com.assist.api.models.StorageItemModel

class StorageInventoryDto {

    private Long storageId
    private Long centreId
    private String itemName
    private Integer itemQuantity

    StorageInventoryDto(StorageItemModel storageItemModel) {
        storageId = storageItemModel.storageId
        centreId = storageItemModel.centreId
        itemName = storageItemModel.itemName
        itemQuantity = storageItemModel.itemQuantity
    }

    Long getStorageId() {
        return storageId
    }

    void setStorageId(Long storageId) {
        this.storageId = storageId
    }

    Long getCentreId() {
        return centreId
    }

    void setCentreId(Long centreId) {
        this.centreId = centreId
    }

    String getItemName() {
        return itemName
    }

    void setItemName(String itemName) {
        this.itemName = itemName
    }

    Integer getItemQuantity() {
        return itemQuantity
    }

    void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity
    }
}
