import './assets/main.css'
// Import Swiper styles
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import 'jsvectormap/dist/jsvectormap.css'
import 'flatpickr/dist/flatpickr.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import VueApexCharts from 'vue3-apexcharts'
import { DefaultApolloClient } from '@vue/apollo-composable'
import { apolloClient } from './api/apolloClient'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(VueApexCharts)
app.provide(DefaultApolloClient, apolloClient)
app.mount('#app')
