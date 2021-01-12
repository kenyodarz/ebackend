package com.cdmservicios.ebackend.security.repositories

import com.cdmservicios.ebackend.security.models.ERole
import com.cdmservicios.ebackend.security.models.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: ERole): Optional<Role>
}