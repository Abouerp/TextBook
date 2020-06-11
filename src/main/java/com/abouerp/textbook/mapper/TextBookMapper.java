package com.abouerp.textbook.mapper;

import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.TextBook;
import com.abouerp.textbook.dto.TextBookDTO;
import com.abouerp.textbook.vo.TextBookVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TextBookMapper {
    TextBookMapper INSTANCE = Mappers.getMapper(TextBookMapper.class);

    TextBook toTextBook(TextBookVO textBookVO);

    @Mapping(target = "realName", expression = "java(realNameConvent(textBook.getAdministrator()))")
    TextBookDTO toDTO(TextBook textBook);

    default String realNameConvent(Administrator administrator){
        return administrator.getRealName();
    }
}
