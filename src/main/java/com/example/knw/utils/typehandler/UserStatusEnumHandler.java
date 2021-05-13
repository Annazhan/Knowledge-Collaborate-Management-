package com.example.knw.utils.typehandler;

import com.example.knw.utils.enumpackage.UserStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.security.core.userdetails.User;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将数据库中的KnwUser.status转化为UserStatusEnum
 *
 * @author qanna
 * @date 2021-04-14
 */
@MappedJdbcTypes(JdbcType.SMALLINT)
@MappedTypes(value = UserStatusEnum.class)
public class UserStatusEnumHandler extends BaseTypeHandler<UserStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UserStatusEnum userStatusEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setShort(i, (short) userStatusEnum.getI());
    }

    @Override
    public UserStatusEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int id = resultSet.getShort(s);
        return UserStatusEnum.getEnumByID(id);
    }

    @Override
    public UserStatusEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getShort(i);
        return UserStatusEnum.getEnumByID(id);
    }

    @Override
    public UserStatusEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int id = callableStatement.getShort(i);
        return UserStatusEnum.getEnumByID(id);
    }
}
