package org.example.controller;

import org.example.dto.ExpenseDto;
import org.example.model.Expense;
import org.example.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
  @Autowired
  private ExpenseRepository expenseRepository;

  @PostMapping
  public ResponseEntity<String> addExpense(@RequestBody ExpenseDto expenseDto, HttpServletRequest req) {
    System.out.println("Received DTO: " + expenseDto);

    String userId = req.getAttribute("userId").toString();

    Expense expense = new Expense(expenseDto);
    expense.setUserId(userId);
    expense.setAmount(expenseDto.getAmount());
    expense.setDescription(expenseDto.getDescription());
    expense.setCategory(expenseDto.getCategory());
    expense.setPaymentMethod(expenseDto.getPaymentMethod());

    expenseRepository.save(expense);
    
    return new ResponseEntity<String>("Successfully created expense", HttpStatus.CREATED);
  }

  @GetMapping("{userId}")
  public ResponseEntity<?> getExpenses(@PathVariable String userId) {
    if (userId == null || userId.isEmpty()) {
      return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
    }
    
    Iterable<Expense> expenses = expenseRepository.findAll()
      .stream()
      .filter(expense -> userId.equals(expense.getUserId()))
      .toList();
    return new ResponseEntity<>(expenses, HttpStatus.OK);
  }
}