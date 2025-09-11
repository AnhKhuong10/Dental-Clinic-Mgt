<template>
  <Modal v-if="visible" @close="$emit('close')">
    <div class="p-6">
      <h4 class="text-lg font-semibold mb-4">{{ title }}</h4>
      <p class="mb-6">{{ message }}</p>

      <div class="flex justify-end gap-3">
        <button
          @click="$emit('close')"
          class="px-4 py-2 rounded bg-gray-200 hover:bg-gray-300"
        >
          Cancel
        </button>
        <button
          @click="$emit('confirm')"
          class="px-4 py-2 rounded bg-red-500 text-white hover:bg-red-600"
        >
          Confirm
        </button>
      </div>
    </div>
  </Modal>
</template>

<script setup lang="ts">
import Modal from "./Modal.vue"

defineProps<{
  visible: boolean
  title: string
  message: string
}>()

defineEmits(["close", "confirm"])
</script>
