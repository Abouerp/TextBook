package com.it666.textbook.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.dao.TextBookDao;
import com.it666.textbook.entity.TextBook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author Abouerp
 */
@Service
public class TextBookService {

    private final TextBookDao textBookDao;

    public TextBookService(TextBookDao textBookDao){
        this.textBookDao = textBookDao;
    }

    public TextBook save(TextBook textBook){
        textBookDao.save(textBook);
        return textBook;
    }

    public PageInfo<TextBook> findByTeacherId(int page, int size, Integer id) {
        PageHelper.startPage(page,size);
        PageInfo<TextBook> pageInfo =new PageInfo<>(textBookDao.findByTeacherId(id),size);
        return pageInfo;
    }

    public TextBook findTextBookById(Integer id){
        return textBookDao.findByTextBookById(id);
    }

    public void deleteByTextBookId(Integer id){
         textBookDao.deleteById(id);
    }

    public TextBook updateTextBook(TextBook textBook){
        textBookDao.updateTextbook(textBook);
        return textBook;
    }

    public List<TextBook> findByTeacherIdAndStatus(Integer teacherId, Integer status){
        return textBookDao.findByTeacherIdAndStatus(teacherId,status);
    }

    public List<TextBook> findByStatus(Integer status){
        return textBookDao.findByStatus(status);
    }

    public Integer updateTextbookStatus(Integer id, Integer status) {
        return textBookDao.updateTextbookStatus(id,status);
    }

    public String outExcel(List<TextBook> textbookList,String uploadFolder) throws IOException {
        String filename = UUID.randomUUID().toString() + ".xls";
        OutputStream outputStream = new FileOutputStream(uploadFolder + filename);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("课程名称");
        row.createCell(1).setCellValue("课程学时");
        row.createCell(2).setCellValue("教材名称");
        row.createCell(3).setCellValue("出版单位");
        row.createCell(4).setCellValue("编者");
        row.createCell(5).setCellValue("出版时间");
        row.createCell(6).setCellValue("版次");
        row.createCell(7).setCellValue("书号ISBN");
        row.createCell(8).setCellValue("教材类型");
        row.createCell(9).setCellValue("联系电话");
        row.setHeightInPoints(30);
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(7, 15 * 256);
        sheet.setColumnWidth(8, 20 * 256);
        sheet.setColumnWidth(9, 12 * 256);

        int j = 1;
        for (int i = 0; i < textbookList.size(); i++) {
            HSSFRow row1 = sheet.createRow(j);
            String courseName = textbookList.get(i).getCourseName();
            if (courseName != null) {
                row1.createCell(0).setCellValue(courseName);
            }
            Integer courseTime = textbookList.get(i).getCourseTime();
            if (courseTime != null) {
                row1.createCell(1).setCellValue(courseTime);
            }
            String titleName = textbookList.get(i).getTitleName();
            if (titleName != null) {
                row1.createCell(2).setCellValue(titleName);
            }
            String publisher = textbookList.get(i).getPublisher();
            if (publisher != null) {
                row1.createCell(3).setCellValue(publisher);
            }
            String author = textbookList.get(i).getAuthor();
            if (author != null) {
                row1.createCell(4).setCellValue(author);
            }
            String titleDate = textbookList.get(i).getTitleDate();
            if (titleDate != null) {
                row1.createCell(5).setCellValue(titleDate);
            }
            String version = textbookList.get(i).getVersion();
            if (version != null) {
                row1.createCell(6).setCellValue(version);
            }
            String isbn = textbookList.get(i).getIsbn();
            if (isbn != null) {
                row1.createCell(7).setCellValue(isbn);
            }
            String titleType = textbookList.get(i).getTitleType();
            if (titleType != null) {
                row1.createCell(8).setCellValue(titleType);
            }
            String phone = textbookList.get(i).getPhone();
            if (phone != null) {
                row1.createCell(9).setCellValue(phone);
            }
            j++;
        }
        workbook.setActiveSheet(0);
        workbook.write(outputStream);
        outputStream.close();

        String path = "/api/file/" + filename;

        return path;
    }


}
