package org.vn.dentalClinicMgt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@SpringBootApplication()
class DentalClinicMgtApplication

fun main(args: Array<String>) {
    runApplication<DentalClinicMgtApplication>(*args)
}
