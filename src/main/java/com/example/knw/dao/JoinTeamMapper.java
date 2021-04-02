package com.example.knw.dao;

import com.example.knw.pojo.JoinTeam;
import com.example.knw.pojo.JoinTeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinTeamMapper {
    long countByExample(JoinTeamExample example);

    int deleteByExample(JoinTeamExample example);

    int deleteByPrimaryKey(Integer joinId);

    int insert(JoinTeam record);

    int insertSelective(JoinTeam record);

    List<JoinTeam> selectByExample(JoinTeamExample example);

    JoinTeam selectByPrimaryKey(Integer joinId);

    int updateByExampleSelective(@Param("record") JoinTeam record, @Param("example") JoinTeamExample example);

    int updateByExample(@Param("record") JoinTeam record, @Param("example") JoinTeamExample example);

    int updateByPrimaryKeySelective(JoinTeam record);

    int updateByPrimaryKey(JoinTeam record);
}