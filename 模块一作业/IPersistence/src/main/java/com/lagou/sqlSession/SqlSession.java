package com.lagou.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {

    // 查询所有
    public <E> List<E> selectList(String statementId,Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException;

    // 根据条件查询
    public <T> T selectOne(String statementId,Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    // 插入
    public int insert(String statementId,Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    // 修改
    public int update(String statementId,Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, IntrospectionException, InstantiationException;

    // 删除
    public int delete(String statementId,Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, IntrospectionException, InstantiationException;
}
