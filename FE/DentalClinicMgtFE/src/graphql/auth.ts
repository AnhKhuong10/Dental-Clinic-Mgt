import { gql } from 'graphql-tag'
import { useMutation } from '@vue/apollo-composable'

function login() {
  return useMutation(gql`
    mutation Login($input: ReqLoginDTO!) {
      login(input: $input) {
        accessToken
        user {
          userId
          username
          fullName
          email
          role
        }
      }
    }
  `)
}

export {login}