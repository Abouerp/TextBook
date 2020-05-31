package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.TextBook;
import com.abouerp.textbook.exception.UserNotFoundException;
import com.abouerp.textbook.mapper.TextBookMapper;
import com.abouerp.textbook.service.AdministratorService;
import com.abouerp.textbook.service.TextBookService;
import com.abouerp.textbook.vo.TextBookVO;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/textbook")
public class TextbookController {

    private final TextBookService textBookService;
    private final AdministratorService administratorService;

    public TextbookController(TextBookService textBookService,
                              AdministratorService administratorService) {
        this.textBookService = textBookService;
        this.administratorService = administratorService;
    }


    @PostMapping("/{id}")
    public ResultBean<TextBook> save(
            @PathVariable Integer id,
            @RequestBody TextBookVO textBookVO){
        Administrator administrator = administratorService.findById(id).orElseThrow(UserNotFoundException::new);
        TextBook textBook = textBookService.save(TextBookMapper.INSTANCE.toTextBook(textBookVO));
        Set<TextBook> bookSet = administrator.getTextBooks();
        bookSet.add(textBook);
        administrator.setTextBooks(bookSet);
        administratorService.save(administrator);
        return ResultBean.ok(textBook);
    }
}
