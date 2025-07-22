package org.example.model;

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