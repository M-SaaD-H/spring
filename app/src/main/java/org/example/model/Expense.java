package org.example.model;

import org.example.dto.ExpenseDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "expenses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
  public Expense(ExpenseDto expenseDto) {
    this.amount = expenseDto.getAmount();
    this.description = expenseDto.getDescription();
    this.category = expenseDto.getCategory();
    this.paymentMethod = expenseDto.getPaymentMethod();
  }

  @Id
  private String id;
  private String userId;
  private int amount;
  private String description;
  private String category;
  private String paymentMethod;
  private String createdAt;
  private String updatedAt;
}