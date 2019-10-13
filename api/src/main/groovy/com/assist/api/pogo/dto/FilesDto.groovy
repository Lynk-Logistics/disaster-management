package com.assist.api.pogo.dto

import com.assist.api.models.FilesModel

class FilesDto {

    private Long fileId
    private String fileName

    FilesDto(FilesModel filesModel) {
        if(filesModel){
            fileId = filesModel.fileId
            fileName = filesModel.fileName
        }
    }

    Long getFileId() {
        return fileId
    }

    void setFileId(Long fileId) {
        this.fileId = fileId
    }

    String getFileName() {
        return fileName
    }

    void setFileName(String fileName) {
        this.fileName = fileName
    }
}
