package com.example.knw.utils.typehandler;

import com.example.knw.utils.enumpackage.UserLevelEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将数据库中TINYINT的user.level转化为UserLevelEnum
 *
 * @author qanna
 * @date 2021-04-14
 */
@MappedJdbcTypes(JdbcType.TINYINT)
@MappedTypes(value = UserLevelEnum.class)
public class UserLevelEnumHandler extends BaseTypeHandler<UserLevelEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UserLevelEnum userLevelEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setByte(i, (byte) userLevelEnum.getI());
    }

    @Override
    public UserLevelEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int id = resultSet.getByte(s);
        return UserLevelEnum.getElementById(id);
    }

    @Override
    public UserLevelEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getByte(i);
        return UserLevelEnum.getElementById(id);
    }

    @Override
    public UserLevelEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int id = callableStatement.getByte(i);
        return UserLevelEnum.getElementById(id);
    }
}
