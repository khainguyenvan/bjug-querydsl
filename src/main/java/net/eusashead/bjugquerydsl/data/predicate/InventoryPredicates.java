package net.eusashead.bjugquerydsl.data.predicate;

import java.util.Date;

import net.eusashead.bjugquerydsl.data.entity.Inventory;
import net.eusashead.bjugquerydsl.data.entity.QInventory;

import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.expr.BooleanExpression;

public class InventoryPredicates {
	
	@QueryDelegate(Inventory.class)
	public static BooleanExpression isNotSold(QInventory inventory) {
		return inventory.sold.isNull();
	}
	
	@QueryDelegate(Inventory.class)
	public static BooleanExpression inStockSince(QInventory inventory, Date date) {
		return inventory.created.before(date);
	}

}
