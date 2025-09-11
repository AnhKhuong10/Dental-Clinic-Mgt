import type {User, UserPage, CreateUserInput, UpdateTeacherInpu, UserDTO} from '@/types/user'
import { useMutation, useQuery } from '@vue/apollo-composable'
import gql from 'graphql-tag'

function getListUser(page: number, size: number, search?: string){
    return useQuery<
    { users: UserPage}, // kiểu dữ liệu trả về (result)
    { page: number; size: number; search?: string| null} // kiểu biến query
  >(
    gql`
    query ($page: Int!, $size: Int!, $search: String) {
      users(page: $page, size: $size, search: $search) {
        content {
                userId
                username
                fullName
                birthdate
                phone
                email
                enabled
                salary
                role{
                  roleId
                  roleName
                }
                }
        totalPages
        totalElements
        pageNumber
        pageSize
      }
    }
  `,
    { page, size, search }
    ,
    { fetchPolicy: 'network-only' }
  )
}

function createUser() {
  return useMutation
  < {createUser: UserDTO},
    {input : CreateUserInput}
  >
  (
    gql
    `mutation createUser($input: CreateUserInput!){
      createUser(input: $input){
        userId
        username
        fullName
        email
        phone
        birthdate
        salary
        enabled
        roleName
      }
    }
    `
  )
}


export {getListUser, createUser}