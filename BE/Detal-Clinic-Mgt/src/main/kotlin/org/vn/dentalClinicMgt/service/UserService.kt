package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.user.CreateUserInput
import org.vn.dentalClinicMgt.domain.dto.user.UpdateUserInput
import org.vn.dentalClinicMgt.domain.dto.user.UserDTO
import org.vn.dentalClinicMgt.domain.entity.User

interface UserService {
    fun handleGetUserByUsername(username: String): User
    fun validateUserLogin(username: String, password: String): User
    fun getUsers(): List<UserDTO>
    fun createUser(input: CreateUserInput): UserDTO
    fun updateUser(input: UpdateUserInput): UserDTO
    fun listUsers(search: String?, pageable: Pageable): Page<User>
}