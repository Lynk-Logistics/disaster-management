package com.assist.api.models

import javax.persistence.*
import java.util.Collection

@Entity
@Table(name = "files", schema = "assist")
class FilesModel {
    private Long fileId
    private String fileName
    private String fileLocation
    private Collection<ActionsModel> actionsByFileId

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    Long getFileId() {
        return fileId
    }

    void setFileId(Long fileId) {
        this.fileId = fileId
    }

    @Basic
    @Column(name = "file_name", nullable = false, length = 256)
    String getFileName() {
        return fileName
    }

    void setFileName(String fileName) {
        this.fileName = fileName
    }

    @Basic
    @Column(name = "file_location", nullable = false, length = 512)
    String getFileLocation() {
        return fileLocation
    }

    void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        FilesModel that = (FilesModel) o

        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false
        if (fileLocation != null ? !fileLocation.equals(that.fileLocation) : that.fileLocation != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = fileId != null ? fileId.hashCode() : 0
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0)
        result = 31 * result + (fileLocation != null ? fileLocation.hashCode() : 0)
        return result
    }

    @OneToMany(mappedBy = "filesByFileId")
    Collection<ActionsModel> getActionsByFileId() {
        return actionsByFileId
    }

    void setActionsByFileId(Collection<ActionsModel> actionsByFileId) {
        this.actionsByFileId = actionsByFileId
    }
}
