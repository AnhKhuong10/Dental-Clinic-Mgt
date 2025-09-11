import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useSearchStore = defineStore('search', () => {
  const keyword = ref('')
  function setKeyword(val: string) {
    keyword.value = val
  }
  return { keyword, setKeyword }
})
