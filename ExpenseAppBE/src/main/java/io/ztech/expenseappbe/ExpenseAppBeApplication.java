package io.ztech.expenseappbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("io.ztech.expensesapp.controllers")
public class ExpenseAppBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseAppBeApplication.class, args);
	}
}
