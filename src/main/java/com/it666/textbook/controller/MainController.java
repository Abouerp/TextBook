package com.it666.textbook.controller;

import com.github.pagehelper.PageInfo;
import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.bean.ResultCode;
import com.it666.textbook.domain.MainBook;
import com.it666.textbook.service.MainBookService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/main")
public class MainController {

    private final MainBookService mainBookService;

    public MainController(MainBookService mainBookService) {
        this.mainBookService = mainBookService;
    }

    /**
     * 主页  根据学院获取书    分页
     * @param page
     * @param size
     * @param collegeId    学院id 根据官网的学院来定义数字
     * @return
     */
    @GetMapping("/{collegeId}")
    public ResultBean<PageInfo<MainBook>> findByCollege(@RequestParam(value = "page",defaultValue = "1") int page,
                                                        @RequestParam(value = "size",defaultValue = "10") int size,
                                                        @PathVariable Integer collegeId) {
        String collegeName = "";
        switch (collegeId){
            case 1:
                collegeName = "电子信息学院";
                break;
            case 2:
                collegeName = "机电工程学院";
                break;
            case 3:
                collegeName = "计算机学院";
                break;
            case 4:
                collegeName = "材料与食品学院";
                break;
            case 5:
                collegeName = "人文社会科学学院";
                break;
            case 6:
                collegeName = "管理学院";
                break;
            case 7:
                collegeName = "经贸学院";
                break;
            case 8:
                collegeName = "外国语学院";
                break;
            case 9:
                collegeName = "艺术设计学院";
                break;
            case 10:
                collegeName = "马克思主义学院";
                break;
            default:
                collegeName = "体育部";
                break;
        }
        return new ResultBean<>(ResultCode.SUCCESS,mainBookService.findByCollege(page,size,collegeName) );
    }

    /**
     * 获取书籍详细信息
     * @param id           书本的id
     * @return
     */
    @GetMapping("/book/{id}")
    public ResultBean<MainBook> findByBookId(@PathVariable Integer id){
        return new ResultBean<>(ResultCode.SUCCESS, mainBookService.findByBookId(id) );
    }
}
