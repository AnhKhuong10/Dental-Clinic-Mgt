import { ApolloClient, InMemoryCache, createHttpLink } from '@apollo/client/core'
import { setContext } from '@apollo/client/link/context'

// endpoint GraphQL BE
const httpLink = createHttpLink({
  uri: 'http://localhost:8080/graphql',
})

// middleware để gắn token
const authLink = setContext((_, { headers }) => {
  const accessToken =
    localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken')

  return {
    headers: {
      ...headers,
      Authorization: accessToken ? `Bearer ${accessToken}` : '',
    },
  }
})

export const apolloClient = new ApolloClient({
  link: authLink.concat(httpLink),
  cache: new InMemoryCache(),
})
