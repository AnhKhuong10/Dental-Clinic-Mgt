<template>
  <Layout>
      <!-- Toast Notification -->
<!-- Toast Notification -->
<div
  v-if="showToast"
  class="fixed bottom-5 right-5 px-4 py-2 rounded-lg shadow-lg text-white z-[9999]"
  :class="toastType === 'success' ? 'bg-green-500' : 'bg-red-500'"
>
  {{ toastMessage }}
</div>

      <PageBreadcrumb :pageTitle="currentPageTitle" />
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="User list">
         <!-- Nút Create ở header -->
      <template #header-actions>
    <button
      @click="openCreate()"
      class="rounded-lg bg-brand-500 px-4 py-2 text-sm font-medium text-white hover:bg-brand-600"
    >
      + Create User
    </button>
  </template>
         <BaseTable
    :columns="userColumns"
    :data="users"
    :pageNumber="pageNumber"
    :totalPages="totalPages"
    :showPagination="true"
    @change-page="loadPage"
  >
    <!-- format birthDate -->
    <template #birthdate="{ value }">
      {{ formatDate(value) }}
    </template>

    <template #role="{ value }">
      {{ value?.roleName || "-" }}
    </template>

    <!-- custom actions -->
    <template #actions="{ row }">
      <button class="btn-icon edit" @click="openEdit(row)">✏️</button>
      <button class="btn-icon delete" @click="openDelete(row.id)">🗑️</button>
      <button class="btn-icon detail" @click="openDetail(row)">🔍</button>
    </template>
  </BaseTable>

  <DetailModal
    :visible="showDetailModal"
    title="User Detail"
    :fields="selectedUserFields"
    @close="closeDetailModal"
  />

  <CreateModal
    :visible="showCreateModal"
    title="Create new user"
    :fields="userFieldsCreate"
    :model="{}"
    :listRoles="listRoles"
    @close="closeCreateModal"
    @save="createNewUser">

        <template #roleId="{ form }">
        <select
          v-model="form.roleId"
          class="w-full rounded-md border border-gray-300 p-2 text-sm
                dark:border-gray-600 dark:bg-gray-900 dark:text-white"
        >
          <option disabled value="">-- Select Role --</option>
          <option v-for="c in listRoles" :key="c.roleId" :value="c.roleId">
            {{ c.roleName }}
          </option>
        </select>
      </template>
  </CreateModal>


      </ComponentCard>
    </div>
  </Layout>

</template>

<script setup lang="ts">
import { getListUser, createUser } from "@/graphql/user"
import type { CreateUserInput, User } from "@/types/user"
import {computed, ref, watch, onMounted } from "vue"
import BaseTable from "@/components/tables/basic-tables/BasicTable.vue"
import Layout from "@/components/layout/Layout.vue"
import PageBreadcrumb from "@/components/common/PageBreadcrumb.vue";
import ComponentCard from "@/components/common/ComponentCard.vue";
import { useSearchStore } from "@/stores/search";
import { formatDate } from "@/utils/dateFormatter"
import DetailModal from "@/components/modal/DetailModal.vue"
import CreateModal from "@/components/modal/CreateModal.vue"
import type { Role } from "@/types/role"
import { getRole} from "@/graphql/role"


//Get List User
const currentPageTitle =ref("User Management");

const pageNumber = ref(0)
const pageSize = ref(5)
const search = ref("")
const users = ref<User[]>([])
const totalPages = ref(0)


const userColumns = [
  { key: "userId", label: "UserId" },
  { key: "username", label: "Username" },
  { key: "fullName", label: "FullName" },
  { key: "birthdate", label: "BirthDate" },
  { key: "phone", label: "Phone" },
  { key: "email", label: "Email" },
  { key: "enabled", label: "Active" },
  { key: "salary", label: "Salary" },
  { key: "role", label:"Role"},
  { key: "actions", label:"Actions"},
]

const {result, refetch} = getListUser(pageNumber.value, pageSize.value, "")

watch(result, (val) => {
    if(val?.users){
        users.value = val.users.content
        totalPages.value = val.users.totalPages
        pageNumber.value = val.users.pageNumber
    }
})

function loadPage(page: number) {
  pageNumber.value = page
  refetch({
    page,
    size: pageSize.value,
    search: search.value || null
  })
}

const searchStore = useSearchStore()

watch(() => searchStore.keyword, (val) => {
  pageNumber.value = 0
  refetch({
    page: 0,
    size: pageSize.value,
    search: val || null
  })
})


// Detail
const showDetailModal = ref(false)
const selectedUser = ref<User | null>(null)

function openDetail(user: User){
  selectedUser.value = user
  showDetailModal.value = true
}

function closeDetailModal(){
  selectedUser.value = null
  showDetailModal.value = false
}

const selectedUserFields = computed(() => {
  if(!selectedUser.value) return []
  return [
    { label: "UserId", value: selectedUser.value.userId},
    { label: "Username", value: selectedUser.value.username},
    { label: "FullName", value: selectedUser.value.fullName},
    { label: "BirthDate", value: new Date(selectedUser.value.birthdate).toLocaleDateString("vi-VN")},
    { label: "Phone", value: selectedUser.value.phone},
    { label: "Email", value: selectedUser.value.email},
    { label: "Active", value: selectedUser.value.enabled},
    { label: "Salary", value: selectedUser.value.salary},
    { label: "Role", value: selectedUser.value.role.roleName}
  ]
}
)

const userFieldsCreate = [
  { key: "username", label: "Username", type: "text" },
  { key: "password", label: "Password", type: "password" },
  { key: "rePassword", label: "Re-Password", type: "password" },
  { key: "fullName", label: "Full Name", type: "text" },
  { key: "birthdate", label: "Birth Date", type: "date" },
  { key: "phone", label: "Phone", type: "text" },
  { key: "email", label: "Email", type: "email" },
  { key: "salary", label: "Salary", type: "number" },

  // Radio cho Active/Inactive
  {
    key: "enabled",
    label: "Status",
    type: "radio",
    options: [
      { label: "Active", value: true },
      { label: "Inactive", value: false }
    ]
  },
  // Select cho Role
  { key: "roleId", label: "Role", type: "select" }
]

//Role
const listRoles = ref<Role[]>([])

onMounted(() => {
  const { result } = getRole()
  watch(result, (val) => {
    if (val?.listRoles) {
      listRoles.value = val.listRoles.map((r) => ({
        roleId: Number(r.roleId),
        roleName: r.roleName
      }))
    }
  })
})

//Create
const showCreateModal = ref(false)

function openCreate(){
  showCreateModal.value = true
}

function closeCreateModal(){
  showCreateModal.value = false
}

const {mutate: createUserMutation, onDone , onError} = createUser()

function createNewUser(input: CreateUserInput) {
  createUserMutation({ input });

  onDone(() => {
    showSuccess("Create user successfully!");
    // reload lại list
    refetch();
    closeCreateModal();
  });

  onError((error) => {
    showError("Create user failed: " + error.message);
  });
}


// State popup
const showToast = ref(false);
const toastMessage = ref("");
const toastType = ref("success"); // "success" | "error"

// Hàm show toast
function showSuccess(message: string) {
  toastMessage.value = message;
  toastType.value = "success";
  showToast.value = true;
  setTimeout(() => (showToast.value = false), 3000);
}

// Hàm show error toast
function showError(message: string) {
  toastMessage.value = message;
  toastType.value = "error";
  showToast.value = true;
  setTimeout(() => (showToast.value = false), 3000);
}

</script>