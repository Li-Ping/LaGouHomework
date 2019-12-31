package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {

    private PreparedStatement preparedStatement;

    public void preparedMethod(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 获取sql语句
        String sql = mappedStatement.getSql();
        // 转换sql语句
        BoundSql boundSql = getBoundSql(sql);
        // 获取预处理对象
        preparedStatement = connection.prepareStatement(boundSql.getSql());
        // 设置参数
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        String parameterType = mappedStatement.getParameterType();
        Class<?> classType = getClassType(parameterType);
        for (int i = 0;i < parameterMappingList.size();i++){
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            Field declaredField = classType.getDeclaredField(parameterMapping.getContent());
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1, o);
        }
    }

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        preparedMethod(configuration, mappedStatement, params);
        // 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        // 封装返回结果集
        Class<?> resultType = getClassType(mappedStatement.getResultType());

        ArrayList<Object> objects = new ArrayList<>();
        while (resultSet.next()){
            Object o = resultType.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i = 1;i <= metaData.getColumnCount();i++){
                String columnName = metaData.getColumnName(i);
                Object object = resultSet.getObject(columnName);
                // 内省
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, object);
            }
            objects.add(o);
        }
        return (List<E>) objects;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        preparedMethod(configuration, mappedStatement, params);
        // 执行sql
        int num = preparedStatement.executeUpdate();
        return num;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if(parameterType != null){
            Class<?> aClass = Class.forName(parameterType);
            return  aClass;
        }
        return  null;
    }

    /**
     * 解析sql语句 #{} 使用 ? 代替 ; 存储参数
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String parse = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parse, parameterMappings);
        return  boundSql;
    }
}
