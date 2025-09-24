package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.user.CreateUserInput
import org.vn.dentalClinicMgt.domain.dto.user.UpdateUserInput
import org.vn.dentalClinicMgt.domain.dto.user.UserDTO
import org.vn.dentalClinicMgt.domain.entity.User
import org.vn.dentalClinicMgt.repository.RoleRepository
import org.vn.dentalClinicMgt.repository.UserRepository
import org.vn.dentalClinicMgt.service.UserService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import java.time.LocalDate

@Service
class UserServiceImpl (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository
) : UserService {
    override fun handleGetUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND,"User not found: $username", "USER_NOT_FOUND") }
    }

    override fun validateUserLogin(username: String, password: String): User {
        val user = userRepository.findByUsername(username)
            .orElseThrow { BusinessException(ErrorCode.UNAUTHORIZED, "Invalid credentials", "INVALID_LOGIN") }
        if (!passwordEncoder.matches(password, user.password)) {
            throw BusinessException(ErrorCode.UNAUTHORIZED, "Invalid credentials", "INVALID_LOGIN")
        }
        return user
    }

    override fun updateUserToken(userId: Long, refreshToken: String?) {
        val user = userRepository.findById(userId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "User not found", "USER_NOT_FOUND") }
        user.refreshToken = refreshToken
        userRepository.save(user)
    }

    override fun getUserByUsernameAndRefreshToken(username: String, refreshToken: String): User? {
         return userRepository.getUserByUsernameAndRefreshToken(username, refreshToken)
    }

    override fun getUsers(): List<UserDTO> {
        return userRepository.findAll().map { it.toDTO() }
    }

    override fun createUser(input: CreateUserInput): UserDTO {
        if (input.password != input.rePassword) {
            throw IllegalArgumentException("Password and RePassword do not match")
        }

        val role = roleRepository.findById(input.roleId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND,"Role not found", "ROLE_NOT_FOUND") }

        val user = User(
            username = input.username,
            password = passwordEncoder.encode(input.password),
            fullName = input.fullName,
            birthdate = input.birthdate,
            phone = input.phone,
            email = input.email,
            salary = input.salary,
            enabled = input.enabled,
            role = role
        )
        return userRepository.save(user).toDTO()
    }

    override fun updateUser(input: UpdateUserInput): UserDTO {
        val user = userRepository.findById(input.userId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "User not found") }

        val role = roleRepository.findById(input.roleId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Role not found") }

        // update field cho phép sửa
        user.fullName = input.fullName
        user.birthdate = input.birthdate
        user.phone = input.phone
        user.salary = input.salary
        user.enabled = input.enabled
        user.role = role

        return userRepository.save(user).toDTO()
    }

    override fun listUsers(search: String?, pageable: Pageable): Page<User> {
        return userRepository.listUser(search, pageable)
    }

    override fun findUserByUserId(id: Long): UserDTO? {
        return userRepository.findById(id).map { it.toDTO() }.orElseGet { null }
    }

    private fun User.toDTO() = UserDTO(
        userId = this.userId,
        username = this.username,
        fullName = this.fullName ?: "",
        email = this.email?:"",
        phone = this.phone?:"",
        birthdate = this.birthdate?:LocalDate.now(),
        salary = this.salary?:0,
        enabled = this.enabled,
        roleName = this.role.roleName
    )

}
