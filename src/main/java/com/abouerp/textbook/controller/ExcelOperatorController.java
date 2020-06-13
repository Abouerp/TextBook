package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.RoleRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.Role;
import com.abouerp.textbook.exception.BadRequestException;
import com.abouerp.textbook.exception.ExcelErrorException;
import com.abouerp.textbook.service.AdministratorService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * @author Abouerp
 */
@Controller
@RequestMapping("/api/excel")
public class ExcelOperatorController {

    private final AdministratorService administratorService;
    private final RoleRepository roleRepository;

    public ExcelOperatorController(AdministratorService administratorService,
                                   RoleRepository roleRepository) {
        this.administratorService = administratorService;
        this.roleRepository = roleRepository;
    }

    @PostMapping
    public ResultBean importAdmin(@RequestParam MultipartFile file) {
        if (file == null) {
            throw new BadRequestException();
        }
        try {
            Role role = roleRepository.findFirstByIsDefault(true).orElseThrow(null);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            InputStream inputStream = file.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            List<Administrator> administrators = new ArrayList<>();
            for (int i = 1; i <= lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                System.out.println(row.getLastCellNum());
                String username = row.getCell(0).getStringCellValue();
                String password = row.getCell(1).getStringCellValue();
                String jobNumber = row.getCell(2).getStringCellValue();

                Administrator administrator = new Administrator().setUsername(username)
                        .setPassword("{noop}" + password)
                        .setJobNumber(jobNumber)
                        .setAccountNonExpired(true)
                        .setAccountNonLocked(true)
                        .setCredentialsNonExpired(true)
                        .setEnabled(true)
                        .setRoles(roleSet);

                administrators.add(administrator);
            }
            administratorService.saveAll(administrators);
            return ResultBean.ok();
        } catch (Exception e) {
            throw new ExcelErrorException();
        }
    }

}
