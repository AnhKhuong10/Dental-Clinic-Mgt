<template>
  <div class="flex items-center justify-center space-x-2 mt-4">
    <button
      class="px-3 py-1 border rounded"
      :disabled="pageNumber === 0"
      @click="$emit('change-page', pageNumber - 1)"
    >
      Prev
    </button>

    <button
      v-for="n in totalPages"
      :key="n"
      class="px-3 py-1 border rounded"
      :class="pageNumber === n - 1 ? 'bg-blue-500 text-white' : ''"
      @click="$emit('change-page', n - 1)"
    >
      {{ n }}
    </button>

    <button
      class="px-3 py-1 border rounded"
      :disabled="pageNumber === totalPages - 1"
      @click="$emit('change-page', pageNumber + 1)"
    >
      Next
    </button>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  pageNumber: number;
  totalPages: number;
}>();
</script>
