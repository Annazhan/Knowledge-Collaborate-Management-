package com.example.knw.dao;

import com.example.knw.pojo.KnwTeam;
import com.example.knw.pojo.KnwTeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KnwTeamMapper {
    long countByExample(KnwTeamExample example);

    int deleteByExample(KnwTeamExample example);

    int deleteByPrimaryKey(Integer teamId);

    int insert(KnwTeam record);

    int insertSelective(KnwTeam record);

    List<KnwTeam> selectByExampleWithBLOBs(KnwTeamExample example);

    List<KnwTeam> selectByExample(KnwTeamExample example);

    KnwTeam selectByPrimaryKey(Integer teamId);

    Integer selectLastTeamID();

    int updateByExampleSelective(@Param("record") KnwTeam record, @Param("example") KnwTeamExample example);

    int updateByExampleWithBLOBs(@Param("record") KnwTeam record, @Param("example") KnwTeamExample example);

    int updateByExample(@Param("record") KnwTeam record, @Param("example") KnwTeamExample example);

    int updateByPrimaryKeySelective(KnwTeam record);

    int updateByPrimaryKeyWithBLOBs(KnwTeam record);

    int updateByPrimaryKey(KnwTeam record);
}