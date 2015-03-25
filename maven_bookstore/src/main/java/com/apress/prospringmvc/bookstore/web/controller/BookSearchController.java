package com.apress.prospringmvc.bookstore.web.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apress.prospringmvc.bookstore.annotation.ExternalUserDetails;
import com.apress.prospringmvc.bookstore.domain.Book;
import com.apress.prospringmvc.bookstore.domain.BookSearchCriteria;
import com.apress.prospringmvc.bookstore.domain.Category;
import com.apress.prospringmvc.bookstore.service.BookstoreService;

@Controller
public class BookSearchController {
	@Autowired
	private BookstoreService bookstoreService;
	
	
	@ModelAttribute
	public BookSearchCriteria criteria() {
		return new BookSearchCriteria();
	}
	
	@ModelAttribute("categories")
	public List<Category> getCategory() {
		return this.bookstoreService.findAllCategories();
	}
	
	@RequestMapping(value="/book/search", method = {RequestMethod.GET})
	public Collection<Book> list(@ModelAttribute("bookSearchCriteria") BookSearchCriteria criteria) {		
		return this.bookstoreService.findBooks(criteria);
	}
}
