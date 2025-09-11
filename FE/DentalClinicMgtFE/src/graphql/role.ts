import type { Role } from "@/types/role";
import { gql } from "@apollo/client/core";
import { useQuery } from "@vue/apollo-composable";

function getRole(){
    return useQuery<
        {listRoles  : Role[]}
    >(
        gql`
            query{
                listRoles{
                    roleId
                    roleName
                }
            }
        `
    )
}

export { getRole }