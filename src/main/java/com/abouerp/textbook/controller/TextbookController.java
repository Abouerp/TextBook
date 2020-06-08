package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.ClassInformationRepository;
import com.abouerp.textbook.domain.*;
import com.abouerp.textbook.dto.TextBookDTO;
import com.abouerp.textbook.exception.BadRequestException;
import com.abouerp.textbook.exception.TextBookNotFoundException;
import com.abouerp.textbook.exception.UserNotFoundException;
import com.abouerp.textbook.mapper.TextBookMapper;
import com.abouerp.textbook.security.UserPrincipal;
import com.abouerp.textbook.service.AdministratorService;
import com.abouerp.textbook.service.TextBookService;
import com.abouerp.textbook.vo.TextBookVO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
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
    public ResultBean<TextBookDTO> save(
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
        return ResultBean.ok(TextBookMapper.INSTANCE.toDTO(textBook));
    }

    @PutMapping("/{id}")
    public ResultBean<TextBookDTO> update(
            @PathVariable Integer id,
            @RequestBody TextBookVO textBookVO) {
        TextBook textBook = textBookService.findById(id).orElseThrow(TextBookNotFoundException::new);
        if (textBookVO != null && !textBookVO.getClassLists().isEmpty()) {
            List<ClassInformation> classInformationList = classInformationRepository.findByIdIn(textBookVO.getClassLists());
            textBook.setClassList(classInformationList.stream().collect(Collectors.toSet()));
        }
        return ResultBean.ok(TextBookMapper.INSTANCE.toDTO(textBookService.save(update(textBook, textBookVO))));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id) {
        textBookService.deleteById(id);
        return ResultBean.ok();
    }

    @GetMapping
    public ResultBean<Page<TextBookDTO>> findAll(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable,
            Integer status,
            Integer collegeId) {
        Administrator administrator = administratorService.findById(userPrincipal.getId()).orElseThrow(UserNotFoundException::new);
        Set<Authority> authorities = new HashSet<>();
        administrator.getRoles().stream().forEach(it ->
                authorities.addAll(it.getAuthorities())
        );
        if (authorities.contains(Authority.ALL_TEXTBOOK_READ)) {
            if (collegeId != null && collegeId > 0) {
                List<Integer> ids = administratorService.findByCollegeId(collegeId)
                        .stream()
                        .map(Administrator::getId)
                        .collect(Collectors.toList());
                return ResultBean.ok(textBookService.findByAdministrator_Ids(ids, pageable, status));
            }
            return ResultBean.ok(textBookService.findAll(pageable, status));
        } else if (authorities.contains(Authority.COLLEGE_TEXTBOOK_READ)) {
            List<Integer> ids = administratorService.findByCollegeId(administrator.getCollege().getId())
                    .stream()
                    .map(Administrator::getId)
                    .collect(Collectors.toList());
            return ResultBean.ok(textBookService.findByAdministrator_Ids(ids, pageable, status));
        } else {
            return ResultBean.ok(textBookService.findByAdministrator_Id(userPrincipal.getId(), pageable, status));
        }
    }

    @PatchMapping("/{id}")
    public ResultBean<TextBookDTO> reviewTextBook(
            @PathVariable Integer id,
            @RequestBody TextBookVO textBookVO) {
        TextBook textBook = textBookService.findById(id).orElseThrow(TextBookNotFoundException::new);
        if (textBookVO == null) {
            throw new BadRequestException();
        }
        if (textBookVO.getStatus() != null) {
            textBook.setStatus(textBookVO.getStatus());
        }
        if (textBookVO.getReviewOpinion() != null) {
            textBook.setReviewOpinion(textBookVO.getReviewOpinion());
        }
        textBook.setReviewDate(new Date());
        return ResultBean.ok(TextBookMapper.INSTANCE.toDTO(textBookService.save(textBook)));
    }

    @PostMapping("/excel")
    public ResultBean<List<String>> outPutExcel(@RequestBody List<Integer> ids) throws Exception{
        List<TextBook> textBooks = textBookService.findByIdIn(ids);
        List<String> sha1s = new ArrayList<>();
        for (TextBook textBook:textBooks){
            String sha1 = textBookService.outPutExcel(textBook);
            sha1s.add(sha1);
        }
        return ResultBean.ok(sha1s);
    }
}
