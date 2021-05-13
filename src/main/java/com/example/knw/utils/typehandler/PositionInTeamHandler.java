package com.example.knw.utils.typehandler;

import com.example.knw.utils.enumpackage.PositionInTeam;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将团队内关于职位名称和权限相对应的json转化为PositionInTeam
 *
 * @author qanna
 * @date 2021-04-22
 */
@MappedTypes(PositionInTeam.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class PositionInTeamHandler extends BaseTypeHandler<PositionInTeam> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, PositionInTeam positionInTeam, JdbcType jdbcType) throws SQLException {
        try {
            preparedStatement.setString(i, positionInTeam.getPositionNameAsJson());
        }catch (Exception e){
            e.printStackTrace();
            preparedStatement.setString(i, null);
        }
    }

    @Override
    public PositionInTeam getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String position = resultSet.getString(s);
        try {
            return new PositionInTeam(position);
        }catch (Exception e){
            e.printStackTrace();
            return new PositionInTeam();
        }
    }

    @Override
    public PositionInTeam getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String position = resultSet.getString(i);
        try {
            return new PositionInTeam(position);
        }catch (Exception e){
            e.printStackTrace();
            return new PositionInTeam();
        }
    }

    @Override
    public PositionInTeam getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String position = callableStatement.getString(i);
        try {
            return new PositionInTeam(position);
        }catch (Exception e){
            e.printStackTrace();
            return new PositionInTeam();
        }
    }
}
