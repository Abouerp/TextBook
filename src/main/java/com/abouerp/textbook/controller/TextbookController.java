package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.ClassInformationRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.ClassInformation;
import com.abouerp.textbook.domain.TextBook;
import com.abouerp.textbook.exception.TextBookNotFoundException;
import com.abouerp.textbook.exception.UserNotFoundException;
import com.abouerp.textbook.mapper.TextBookMapper;
import com.abouerp.textbook.service.AdministratorService;
import com.abouerp.textbook.service.TextBookService;
import com.abouerp.textbook.vo.TextBookVO;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/textbook")
public class TextbookController {

    private final TextBookService textBookService;
    private final AdministratorService administratorService;
    private final ClassInformationRepository classInformationRepository;

    public TextbookController(TextBookService textBookService,
                              AdministratorService administratorService,
                              ClassInformationRepository classInformationRepository) {
        this.textBookService = textBookService;
        this.administratorService = administratorService;
        this.classInformationRepository = classInformationRepository;
    }

    private static TextBook update(TextBook textBook, TextBookVO textBookVO) {
        if (textBookVO != null && textBookVO.getAuthor() != null) {
            textBook.setAuthor(textBookVO.getAuthor());
        }
        if (textBookVO != null && textBookVO.getCourseName() != null) {
            textBook.setCourseName(textBookVO.getCourseName());
        }
        if (textBookVO != null && textBookVO.getCourseTime() != null) {
            textBook.setCourseTime(textBookVO.getCourseTime());
        }
        if (textBookVO != null && textBookVO.getFlag() != null) {
            textBook.setFlag(textBookVO.getFlag());
        }
        if (textBookVO != null && textBookVO.getIsbn() != null) {
            textBook.setIsbn(textBookVO.getIsbn());
        }
        if (textBookVO != null && textBookVO.getPhone() != null) {
            textBook.setPhone(textBookVO.getPhone());
        }
        if (textBookVO != null && textBookVO.getPublisher() != null) {
            textBook.setPublisher(textBookVO.getPublisher());
        }
        if (textBookVO != null && textBookVO.getVersion() != null) {
            textBook.setVersion(textBookVO.getVersion());
        }
        if (textBookVO != null && textBookVO.getTitleName() != null) {
            textBook.setTitleName(textBookVO.getTitleName());
        }
        if (textBookVO != null && textBookVO.getTitleDate() != null) {
            textBook.setTitleDate(textBookVO.getTitleDate());
        }
        if (textBookVO != null && textBookVO.getTitleType() != null) {
            textBook.setTitleType(textBookVO.getTitleType());
        }
        if (textBookVO != null && textBookVO.getStatus() != null) {
            textBook.setStatus(textBookVO.getStatus());
        }
        if (textBookVO != null && textBookVO.getReviewOpinion() != null) {
            textBook.setReviewOpinion(textBookVO.getReviewOpinion());
        }
        if (textBookVO != null && textBookVO.getReviewDate() != null) {
            textBook.setReviewDate(textBookVO.getReviewDate());
        }
        return textBook;
    }

    @PostMapping("/{id}")
    public ResultBean<TextBook> save(
            @PathVariable Integer id,
            @RequestBody TextBookVO textBookVO) {
        Administrator administrator = administratorService.findById(id).orElseThrow(UserNotFoundException::new);
        textBookVO.setDate(new Date());
        Set<ClassInformation> classInformationList = classInformationRepository.findByIdIn(textBookVO.getClassLists()).stream().collect(Collectors.toSet());
        TextBook textBook = TextBookMapper.INSTANCE.toTextBook(textBookVO);
        textBook.setClassList(classInformationList);
        textBook.setAdministrator(administrator);
        textBook = textBookService.save(textBook);
        administratorService.save(administrator);
        return ResultBean.ok(textBook);
    }

    @PutMapping("/{id}")
    public ResultBean<TextBook> update(
            @PathVariable Integer id,
            @RequestBody TextBookVO textBookVO) {
        TextBook textBook = textBookService.findById(id).orElseThrow(TextBookNotFoundException::new);
        if (textBookVO != null && !textBookVO.getClassLists().isEmpty()) {
            List<ClassInformation> classInformationList = classInformationRepository.findByIdIn(textBookVO.getClassLists());
            textBook.setClassList(classInformationList.stream().collect(Collectors.toSet()));
        }
        return ResultBean.ok(textBookService.save(update(textBook, textBookVO)));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id) {
        textBookService.deleteById(id);
        return ResultBean.ok();
    }


}
