package com.abouerp.textbook.mapper;

import com.abouerp.textbook.domain.College;
import com.abouerp.textbook.domain.Role;
import com.abouerp.textbook.vo.CollegeVO;
import com.abouerp.textbook.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CollegeMapper {
    CollegeMapper INSTANCE = Mappers.getMapper(CollegeMapper.class);

    College toCollege(CollegeVO collegeVO);
}
