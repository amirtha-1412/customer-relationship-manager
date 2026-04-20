package com.crm.controller;

import com.crm.entity.Customer;
import com.crm.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET /customers?page=0&size=10&keyword=
    @GetMapping
    public String getAllCustomers(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search mode — no pagination
            List<Customer> customers = customerService.searchCustomers(keyword.trim());
            model.addAttribute("customers", customers);
            model.addAttribute("customerPage", null);
        } else {
            // Paginated mode
            Page<Customer> customerPage = customerService.getCustomersPaged(page, size);
            model.addAttribute("customers", customerPage.getContent());
            model.addAttribute("customerPage", customerPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", customerPage.getTotalPages());
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Customers");
        return "customers/list";
    }



    // GET /customers/new - Show form to add new customer
    @GetMapping("/new")
    public String showNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("pageTitle", "Add Customer");
        return "customers/form";
    }

    // POST /customers - Save new customer to database
    @PostMapping
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    // GET /customers/edit/{id} - Show edit form for existing customer
    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            model.addAttribute("pageTitle", "Edit Customer");
            return "customers/form";
        }
        return "redirect:/customers";
    }

    // POST /customers/edit/{id} - Update existing customer
    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id,
                                 @ModelAttribute("customer") Customer customer) {
        customer.setId(id);
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    // GET /customers/delete/{id} - Delete customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    // GET /customers/export - Export customers to Excel
    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");

        List<Customer> customers = customerService.getAllCustomers();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Customers");

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Header row
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "First Name", "Last Name", "Email", "Phone", "Company", "Status"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowNum = 1;
            for (Customer c : customers) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(c.getId());
                row.createCell(1).setCellValue(c.getFirstName());
                row.createCell(2).setCellValue(c.getLastName());
                row.createCell(3).setCellValue(c.getEmail());
                row.createCell(4).setCellValue(c.getPhone());
                row.createCell(5).setCellValue(c.getCompany());
                row.createCell(6).setCellValue(c.getStatus());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }
    }
}
