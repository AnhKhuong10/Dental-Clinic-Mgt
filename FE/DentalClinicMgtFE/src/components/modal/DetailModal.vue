<template>
  <Modal v-if="visible" @close="$emit('close')">
    <h4 class="mb-4 text-xl font-semibold text-gray-800 dark:text-white/90">
      {{ title }}
    </h4>

    <div class="grid grid-cols-1 gap-4 sm:grid-cols-2">
      <div
        v-for="(field, index) in fields"
        :key="index"
        class="p-2 border rounded-lg bg-gray-50 dark:bg-gray-800"
      >
        <p class="text-xs font-medium text-gray-500 dark:text-gray-400">
          {{ field.label }}
        </p>
        <p class="text-sm text-gray-800 dark:text-white">
          {{ field.value }}
        </p>
      </div>
    </div>

    <div class="flex justify-end mt-6">
      <button
        @click="$emit('close')"
        class="rounded-lg border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
      >
        Close
      </button>
    </div>
  </Modal>
</template>

<script setup lang="ts">
import  Modal from "./Modal.vue"
defineProps<{
  visible: boolean
  title: string
  fields: { label: string; value: string | number | boolean| null }[]
}>()

defineEmits(["close"])
</script>
