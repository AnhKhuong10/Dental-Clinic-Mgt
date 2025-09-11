<template>
  <!-- Overlay -->
  <div
    class="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm"
    @click.self="$emit('close')"
  >
    <!-- Modal container -->
    <div
      class="relative w-full max-w-[700px] rounded-3xl bg-white p-6 dark:bg-gray-900 shadow-lg"
    >
      <!-- Close button -->
      <button
        @click="$emit('close')"
        class="absolute right-5 top-5 flex h-8 w-8 items-center justify-center rounded-full bg-gray-100 text-gray-500 hover:bg-gray-200 dark:bg-gray-700 dark:text-gray-300"
      >
        ✖
      </button>

      <!-- Nội dung từ DetailModal.vue -->
      <slot />
    </div>
  </div>
</template>

<script setup lang="ts">
defineEmits(["close"])
</script>
