package com.assist.api.repositories

import com.assist.api.models.ActionedByModel
import com.assist.api.models.ActionedByModelPK
import org.springframework.data.jpa.repository.JpaRepository

interface ActionedByRepository extends JpaRepository<ActionedByModel,ActionedByModelPK>{

}