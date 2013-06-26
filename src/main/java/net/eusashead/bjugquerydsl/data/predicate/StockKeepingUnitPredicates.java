package net.eusashead.bjugquerydsl.data.predicate;

import net.eusashead.bjugquerydsl.controller.SkuSearchRequest;
import net.eusashead.bjugquerydsl.data.entity.QStockKeepingUnit;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.Predicate;

public class StockKeepingUnitPredicates {
	
	@QueryDelegate(StockKeepingUnit.class)
	public static Predicate search(QStockKeepingUnit path, SkuSearchRequest request) {
		BooleanBuilder builder = new BooleanBuilder();
		if (request.getMinPrice() != null) {
			builder.and(path.price.goe(request.getMinPrice()));
		}
		if (request.getMaxPrice() != null) {
			builder.and(path.price.loe(request.getMaxPrice()));
		}
		BooleanBuilder attrBuilder = new BooleanBuilder();
		for (String attr : request.getAttributes().keySet()) {
			BooleanBuilder valueBuilder = new BooleanBuilder();
			for (String val : request.getAttributes().get(attr)) {
				valueBuilder.or(path.values.any().attribute().name.equalsIgnoreCase(attr)
						.and(path.values.any().name.equalsIgnoreCase(val)));
			}
			attrBuilder.and(valueBuilder);
		}
		builder.and(attrBuilder.getValue());
		Predicate predicate = builder.getValue();

		return predicate;
	}

}
