<template>
  <Modal v-if="visible" @close="$emit('close')">
    <div class="relative w-full max-w-[700px] rounded-3xl bg-white p-6 dark:bg-gray-900">
      <button @click="$emit('close')" class="absolute right-5 top-5">✖</button>
      <h4 class="mb-4 text-xl font-semibold text-gray-800 dark:text-white/90">{{ title }}</h4>

      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 gap-4 sm:grid-cols-2">
        <!-- Loop fields -->
        <div v-for="field in fields" :key="field.key" class="space-y-1">
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-400">
            {{ field.label }}
          </label>

        <!-- Ưu tiên slot -->
        <slot :name="field.key" :form="form">
          <!-- Select -->
          <select
            v-if="field.type === 'select'"
            v-model="form[field.key]"
            class="w-full rounded-md border border-gray-300 p-2 text-sm
                  dark:border-gray-600 dark:bg-gray-900 dark:text-white"
          >
            <option v-for="opt in field.options" :key="opt.value" :value="opt.value">
              {{ opt.label }}
            </option>
          </select>

          <!-- Radio -->
          <div v-else-if="field.type === 'radio'" class="flex gap-4">
            <label v-for="opt in field.options" :key="opt.value" class="flex items-center gap-1">
              <input type="radio" :value="opt.value" v-model="form[field.key]" />
              {{ opt.label }}
            </label>
          </div>

          <!-- Default input -->
          <input
            v-else
            v-model="form[field.key]"
            :type="field.type || 'text'"
            class="w-full rounded-md border border-gray-300 p-2 text-sm
                  dark:border-gray-600 dark:bg-gray-900 dark:text-white"
          />
        </slot>

        </div>

        <!-- Footer -->
        <div class="col-span-2 flex justify-end gap-2 mt-4">
          <button type="button" @click="$emit('close')">Cancel</button>
          <button type="submit" class="bg-brand-500 text-white px-4 py-2 rounded-lg hover:bg-brand-600">
            Save
          </button>
        </div>
      </form>
    </div>
  </Modal>
</template>

<script setup lang="ts">
import { reactive, watch } from "vue"
import Modal from "./Modal.vue"

interface Field {
  key: string
  label: string
  type?: string
  options?: { label: string; value: any }[]  // dùng cho radio / select
}


const props = defineProps<{
  visible: boolean
  title: string
  fields: Field[]
  model: Record<string, any>
}>()

const emit = defineEmits(["close", "save"])
const form = reactive<Record<string, any>>({})

watch(
  () => props.model,
  (val) => props.fields.forEach(f => form[f.key] = val?.[f.key] ?? ""),
  { immediate: true, deep: true }
)

function handleSubmit() {
  emit("save", { ...form })
}
</script>
