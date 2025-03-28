package com.web.Application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.Application.entity.Expense;
import com.web.Application.entity.User;

public interface ExpenseRepository extends JpaRepository<Expense,Long>{

    public List<Expense> findByUser(User user);

}
