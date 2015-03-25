package com.apress.prospringmvc.bookstore.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apress.prospringmvc.bookstore.domain.Book;
import com.apress.prospringmvc.bookstore.service.BookstoreService;

@Controller
public class BookDetailController {
	
	@Autowired
	private BookstoreService bookstoreService;
	
	
	@RequestMapping(value="/book/detail/{bookId}")
	public String detail(@PathVariable("bookId") long bookId, Model model) {
		Book book = this.bookstoreService.findBook(bookId);
		model.addAttribute(book);
		return "book/detail";
	}
}
