package com.it666.textbook.dao;

import com.it666.textbook.domain.TextBook;
import com.it666.textbook.entity.StatisticsPublisherRsp;
import com.it666.textbook.entity.StatisticsRep;
import com.it666.textbook.entity.TextBookHistoryRsp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Abouerp
 */

@Repository
public interface TextBookDao {

    @Select("select * from textbook order by date")
    public List<TextBook> findAll();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into textbook(course_name,course_time,title_name,publisher,author,title_date,version,ISBN,title_type,flag,phone,date,status,review_opinion,teacher_id)" +
            "values(#{courseName},#{courseTime},#{titleName},#{publisher},#{author},#{titleDate},#{version},#{isbn},#{titleType},#{flag},#{phone},#{date},#{status},#{reviewOpinion},#{teacherId})")
    public void save(TextBook textBook);

    @Update("update textbook set course_name=#{courseName},course_time=#{courseTime},title_name=#{titleName},publisher=#{publisher},author=#{author},title_date=#{titleDate}," +
            "version=#{version},ISBN=#{isbn},title_type=#{titleType},flag=#{flag},phone=#{phone},date=#{date},status=#{status},review_date=#{reviewDate},review_opinion=#{reviewOpinion},teacher_id=#{teacherId} where id = #{id}")
    public void updateTextbook(TextBook textBook);

    @Select("select * from textbook where teacher_id = #{id} order by date desc")
    public List<TextBook> findByTeacherId(Integer id);

    @Select("select * from textbook where id = #{id}")
    public TextBook findByTextBookById(Integer id);

    @Delete("delete from textbook where id = #{id}")
    public void deleteById(Integer id);

    @Select("select * from textbook where teacher_id=#{teacherId} and status=#{status} order by date desc")
    public List<TextBook> findByTeacherIdAndStatus(Integer teacherId, Integer status);

    @Select("select * from textbook where teacher_id=#{teacherId} and status >= #{status} order by review_date")
    public List<TextBook> findByTeacherIdAndOkStatus(Integer teacherId, Integer status);


    @Select("select b.id as id, title_name as titleName,course_name as courseName, b.title_type as titleType, date,review_date as reviewDate,status, real_name as teacherName from textbook b,user a where b.teacher_id = a.id and status = #{status}" +
            " order by date desc")
    public List<TextBookHistoryRsp> findByStatusUnReview(Integer status);

    @Select(" select b.id,b.course_name,b.course_time,b.title_name,b.publisher,b.author,b.title_date,b.version,b.ISBN,b.title_type,b.flag,b.phone,b.date,b.status,b.review_date,b.review_opinion,b.teacher_id from user a, textbook b where a.id = b.teacher_id and a.college = #{college}")
    public List<TextBook> findByCollege(String college);

    @Select("select sum(`status`=1) as unSubmit ,sum(`status`=2) as unReview, sum(`status`=3) + sum(`status`=4) as review,count(`status`) as count from textbook where teacher_id=#{teacherId}")
    public StatisticsRep findStatisticsByTeacherId(Integer teacherId);


    @Select("<script>"
            + "   select b.id as id, title_name as titleName,course_name as courseName,b.title_type as titleType, date,review_date as reviewDate,status, real_name as teacherName"
            + "   from textbook b,user a                        "
            + "   <where>                                       "
            + "       b.teacher_id=a.id                         "
            + "       <if test=' status != null '>              "
            +"            <choose>                              "
            + "              <when test='status == 2 '>         "
            + "                 and status=#{status}            "
            + "              </when>                            "
            +"               <when test='status == -1 '>        "
            +"                  and status>=2                   "
            +"               </when>                            "
            + "              <otherwise test='status >= 3'>     "
            + "                 and status=#{status}           "
            + "              </otherwise>                       "
            + "           </choose>                             "
            +"         </if>                                    "
            +"       <if test=' college != null '>              "
            +"          and college=#{college}                  "
            +"       </if>                                      "
            +"   </where>                                       "
            +"</script>")
    public List<TextBookHistoryRsp> findTextBookHistory(Integer status,String college);

    /**
     * 统计使用出版社书籍的数量
     * @return
     */
    @Select("select publisher ,count(publisher) as number from textbook  group by publisher order by number ")
    public List<StatisticsPublisherRsp> findStatisticsPublisherRsp();
}
