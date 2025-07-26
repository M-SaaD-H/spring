package org.example.controller;

import org.example.model.Expense;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
  @PostMapping
  public String addExpense(@RequestBody Expense body) {
    System.out.println("Body: " + body);
    return "Got req";
  }
}