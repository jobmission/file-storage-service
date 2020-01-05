package com.revengemission.commons.fss.persistence.mapper;

import com.revengemission.commons.fss.persistence.entity.FileRecordEntity;
import com.revengemission.commons.fss.persistence.entity.FileRecordEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileRecordEntityMapper {
    long countByExample(FileRecordEntityExample example);

    int deleteByExample(FileRecordEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileRecordEntity record);

    int insertSelective(FileRecordEntity record);

    List<FileRecordEntity> selectByExample(FileRecordEntityExample example);

    FileRecordEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileRecordEntity record, @Param("example") FileRecordEntityExample example);

    int updateByExample(@Param("record") FileRecordEntity record, @Param("example") FileRecordEntityExample example);

    int updateByPrimaryKeySelective(FileRecordEntity record);

    int updateByPrimaryKey(FileRecordEntity record);

    FileRecordEntity selectUniqueByExample(FileRecordEntityExample example);
}