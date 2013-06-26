package net.eusashead.bjugquerydsl.hateoas;

import org.apache.commons.beanutils.ConvertUtilsBean;

public class SimplePropertyConverterImpl implements SimplePropertyConverter {

	private final ConvertUtilsBean convertUtils;

	public SimplePropertyConverterImpl() {
		this.convertUtils = new ConvertUtilsBean();
	}

	public SimplePropertyConverterImpl(ConvertUtilsBean convertUtils) {
		this.convertUtils = convertUtils;
	}

	public Object convert(Object o, Class<?> to) {
		return convertUtils.convert(o, to);
	}
}
