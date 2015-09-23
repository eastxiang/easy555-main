package com.easy555.common.repository.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class IntegerValuedEnumType<T extends Enum<T> & IntegerValuedEnum> implements UserType, ParameterizedType {

	/**
	 * Enum class for this particular user type.
	 */
	private Class<T> enumClass;

	/**
	 * Value to use if null.
	 */
	private Integer defaultValue;

	public IntegerValuedEnumType() {

	}

	@SuppressWarnings("unchecked")
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enum");
		try {
			enumClass = (Class<T>) Class.forName(enumClassName).asSubclass(Enum.class)
					.asSubclass(IntegerValuedEnum.class);
		} catch (ClassNotFoundException e) {
			throw new HibernateException("Enum class not found", e);
		}

		String defaultValueStr = parameters.getProperty("defaultValue");
		if (defaultValueStr != null && !defaultValueStr.isEmpty()) {
			try {
				setDefaultValue(Integer.parseInt(defaultValueStr));
			} catch (NumberFormatException e) {
				throw new HibernateException("Invalid default value", e);
			}
		}
	}

	public Integer getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Integer defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * The class returned by <tt>nullSafeGet()</tt>.
	 * 
	 * @return Class
	 */
	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return enumClass;
	}

	public int[] sqlTypes() {
		return new int[] { Types.INTEGER };
	}

	public boolean isMutable() {
		return false;
	}

	public Object assemble(Serializable cached, Object owner) {
		return cached;
	}

	public Serializable disassemble(Object value) {
		return (Enum) value;
	}

	public Object deepCopy(Object value) {
		return value;
	}

	public boolean equals(Object o, Object o1) {
		if (o == o1) {
			return true;
		}
		if (o == null || o == null) {
			return false;
		}

		return o.equals(o1);
	}

	public int hashCode(Object x) {
		return x.hashCode();
	}

	public Object replace(Object original, Object target, Object owner) {
		return original;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Integer value = rs.getInt(names[0]);

		Object result = rs.wasNull() ? null : decode(value);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.INTEGER);
		} else {
			st.setInt(index, encode((EnumSet<T>) value));
		}
	}

	/*
	 * Encode the EnumSet into an integer based on bit on/off
	 */
	private int encode(EnumSet<T> set) {
		int ret = 0;
		for (T val : set) {
			ret |= 1 << val.getCode();
		}
		return ret;
	}

	/*
	 * Decode the integer back to a EnumSet based on bit on/off
	 */
	private EnumSet<T> decode(int code) {
		Map<Integer, T> codeMap = new HashMap<Integer, T>();
		for (T val : EnumSet.allOf(enumClass)) {
			codeMap.put(val.getCode(), val);
		}

		EnumSet<T> result = EnumSet.noneOf(enumClass);
		while (code != 0) {
			int ordinal = Integer.numberOfTrailingZeros(code);
			code ^= Integer.lowestOneBit(code);
			result.add(codeMap.get(ordinal));
		}
		return result;
	}

}