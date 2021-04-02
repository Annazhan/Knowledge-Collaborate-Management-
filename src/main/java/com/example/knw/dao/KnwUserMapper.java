package com.example.knw.dao;

import com.example.knw.pojo.KnwUser;
import com.example.knw.pojo.KnwUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KnwUserMapper {
    long countByExample(KnwUserExample example);

    int deleteByExample(KnwUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KnwUser record);

    int insertSelective(KnwUser record);

    List<KnwUser> selectByExample(KnwUserExample example);

    KnwUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KnwUser record, @Param("example") KnwUserExample example);

    int updateByExample(@Param("record") KnwUser record, @Param("example") KnwUserExample example);

    int updateByPrimaryKeySelective(KnwUser record);

    int updateByPrimaryKey(KnwUser record);
}