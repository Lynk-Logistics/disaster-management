package com.assist.api.repositories

import com.assist.api.models.FilesModel
import org.springframework.data.jpa.repository.JpaRepository

interface FilesRepository extends JpaRepository<FilesModel,Long>{
}
