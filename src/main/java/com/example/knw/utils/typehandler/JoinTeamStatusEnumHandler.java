package com.example.knw.utils.typehandler;

import com.example.knw.utils.enumpackage.JoinInTeamStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将数据库中Integer类型的join_team.status转化为enum
 *
 * @author qanna
 * @date 2021-04-16
 */
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value = JoinInTeamStatusEnum.class)
public class JoinTeamStatusEnumHandler extends BaseTypeHandler<JoinInTeamStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JoinInTeamStatusEnum joinInTeamStatusEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, joinInTeamStatusEnum.getCode());
    }

    @Override
    public JoinInTeamStatusEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int id = resultSet.getInt(s);
        return JoinInTeamStatusEnum.getElementById(id);
    }

    @Override
    public JoinInTeamStatusEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(i);
        return JoinInTeamStatusEnum.getElementById(id);
    }

    @Override
    public JoinInTeamStatusEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int id = callableStatement.getInt(i);
        return JoinInTeamStatusEnum.getElementById(id);
    }
}
