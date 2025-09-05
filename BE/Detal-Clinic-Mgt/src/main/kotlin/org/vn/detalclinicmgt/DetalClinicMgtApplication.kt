package org.vn.detalclinicmgt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class DetalClinicMgtApplication

fun main(args: Array<String>) {
    runApplication<DetalClinicMgtApplication>(*args)
}
