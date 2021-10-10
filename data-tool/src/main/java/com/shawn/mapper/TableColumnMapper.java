package com.shawn.mapper;

import com.shawn.model.Column;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TableColumnMapper {
    @Select("select COLUMN_NAME as columnName,DATA_TYPE as dataType from information_schema.columns  where table_name = #{tableName}")
    List<Column> selectColumnByTable(String tableName);
}
