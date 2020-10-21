package com.shawn.mybatis.learn.mapper;

import com.shawn.mybatis.learn.domain.Member;
import org.apache.ibatis.annotations.Select;

/**
 * @author luxufeng
 * @date 2020/10/19
 **/
public interface MemberMapper {
    @Select("select * from yh_member where id = #{id}")
    Member selectMember(Long id);
}
