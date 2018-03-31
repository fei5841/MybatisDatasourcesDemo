package com.liuchunshu.demo.enums;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class UniversalEnumTypeHandler<E extends BaseEnum<?, ?>> extends BaseTypeHandler<E> {

	private Class<E> type;
	
	public UniversalEnumTypeHandler(Class<E> type) {
		if(type==null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type=type;
	}
	
	private E locateEnumStatus(Object obj) {
		E[] enums=type.getEnumConstants();
		if(enums==null) {
			throw new IllegalArgumentException(type.getSimpleName()  
	                    + " does not represent an enum type.");
		}
		for(E e:enums) {
			if(e.getValue().equals(obj)) {
				return e;
			}
		}
		throw new IllegalArgumentException("未知的枚举类型：" + obj + ",请核对" + type.getSimpleName());
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setObject(i, parameter.getValue(),jdbcType.TYPE_CODE);
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Object obj=rs.getObject(columnName);
		if(rs.wasNull()) {
			return null;
		}else {
			return locateEnumStatus(obj);
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Object obj=rs.getObject(columnIndex);
		if(rs.wasNull()) {
			return null;
		}else {
			return locateEnumStatus(obj);
		}
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Object obj=cs.getObject(columnIndex);
		if(cs.wasNull()) {
			return null;
		}else {
			return locateEnumStatus(obj);
		}
	}

}
