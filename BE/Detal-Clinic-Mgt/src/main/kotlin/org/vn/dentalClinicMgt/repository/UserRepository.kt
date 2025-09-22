package org.vn.dentalClinicMgt.repository


import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>

    @Query("""
        SELECT u FROM User u
        WHERE (: search IS NULL 
             OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))
             OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
             OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :search, '%'))
             OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))           
    """)
    fun listUser(
        @Param("search") search: String?,
        pageable: Pageable,
        ) : Page<User>

    fun findByUsernameAndRefreshToken(username: String, refreshToken: String): User?

    @Query(
        value = "SELECT * FROM users u WHERE u.username = :username AND BINARY u.refreshToken = :refreshToken",
        nativeQuery = true
    )
    fun getUserByUsernameAndRefreshToken(
        @Param("refreshToken") refreshToken: String,
        @Param("username") username: String
    ): User?
}
