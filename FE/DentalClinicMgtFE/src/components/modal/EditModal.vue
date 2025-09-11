<template>
  <Modal v-if="visible" @close="$emit('close')">
    <div class="relative w-full max-w-[700px] rounded-3xl bg-white p-6 dark:bg-gray-900">
      <!-- Close btn -->
      <button
        @click="$emit('close')"
        class="absolute right-5 top-5 flex h-10 w-10 items-center justify-center rounded-full 
               bg-gray-100 text-gray-400 hover:bg-gray-200 hover:text-gray-600"
      >
        ✖
      </button>

      <!-- Title -->
      <h4 class="mb-4 text-xl font-semibold">{{ title }}</h4>

      <!-- Form -->
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 gap-4 sm:grid-cols-2">
        <div
          v-for="(field, index) in fields"
          :key="index"
          class="p-2 border rounded-lg bg-gray-50"
        >
          <label class="text-xs font-medium text-gray-500">{{ field.label }}</label>

          <!-- 👇 Ưu tiên slot để custom -->
          <slot :name="field.key" :form="form">
            <!-- Fallback input mặc định -->
            <input
              v-model="form[field.key]"
              :type="field.type || 'text'"
              class="w-full rounded-md border border-gray-300 p-2 text-sm
                     dark:border-gray-600 dark:bg-gray-900 dark:text-white"
            />
          </slot>
        </div>

        <!-- Footer -->
        <div class="col-span-2 flex justify-end gap-2 mt-4">
          <button type="button" @click="$emit('close')"
            class="rounded-lg border px-4 py-2 text-sm">Cancel</button>
          <button type="submit"
            class="rounded-lg bg-blue-500 px-4 py-2 text-sm text-white">Update</button>
        </div>
      </form>
    </div>
  </Modal>
</template>

<script setup lang="ts">
import { ref, watch } from "vue"
import Modal from "./Modal.vue"

interface Field {
  key: string
  label: string
  type?: string
}

const props = defineProps<{
  visible: boolean
  title: string
  fields: Field[]
  model: Record<string, unknown> | null
  studentClasses?: { id: number; name: string }[]
}>()

const emit = defineEmits(["close", "save"])

const form = ref<Record<string, unknown>>({
    hireDate: null as string | null,
})

// khi mở modal -> copy model vào form để hiển thị
watch(
  () => props.model,
  (val) => {
    form.value = { ...(val || {}) }
  },
  { immediate: true }
)

function handleSubmit() {
  emit("save", { ...form.value })
}
</script>
