package web.intranet

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class IntranetApplication

fun main(args: Array<String>) {
    SpringApplication.run(IntranetApplication::class.java, *args)
}