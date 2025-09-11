<template>
  <div class="overflow-hidden rounded-xl border border-gray-200 bg-white">
    <div class="max-w-full overflow-x-auto">
      <table class="min-w-full">
        <thead>
          <tr class="border-b border-gray-200">
            <th
              v-for="col in columns"
              :key="col.key"
              class="px-3 py-3 text-left"
            >
              <p class="font-medium text-gray-500 text-theme-xs">{{ col.label }}</p>
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-200">
          <tr
            v-for="row in data"
            :key="row.id as string"
            class="border-t border-gray-100"
          >
            <td
              v-for="col in columns"
              :key="col.key"
              class="px-3 py-3"
            >
              <!-- cho phép custom cell -->
              <!-- <slot :name="col.key" :row="row" :value="row[col.key]">
                {{ row[col.key] }}
              </slot> -->
              <slot :name="col.key" :row="row" :value="getNestedValue(row, col.key)">
                {{ getNestedValue(row, col.key) }}
              </slot>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- optional pagination -->
    <div v-if="showPagination" class="mt-4">
      <Pagination
        :pageNumber="pageNumber ?? 1"
        :totalPages="totalPages ?? 1"
        @change-page="onChangePage"
      />
    </div>
  </div>
</template>

<script setup lang="ts" generic="T">
import Pagination from "@/components/layout/Pagination.vue"

defineProps<{
  columns: { key: string; label: string }[]
  data: T[]
  pageNumber?: number
  totalPages?: number
  showPagination?: boolean
}>()

const emit = defineEmits(["change-page"])
function onChangePage(page: number) {
  emit("change-page", page)
}

function getNestedValue(obj: any, path: string) {
  return path.split(".").reduce((acc, part) => acc && acc[part], obj)
}

</script>
