package net.eusashead.bjugquerydsl.data.predicate;

import java.util.Date;

import net.eusashead.bjugquerydsl.data.entity.Inventory;
import net.eusashead.bjugquerydsl.data.entity.QInventory;

import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.Predicate;

public class InventoryPredicates {
	
	@QueryDelegate(Inventory.class)
	public static Predicate isNotSold(QInventory inventory) {
		return inventory.sold.isNull();
	}
	
	@QueryDelegate(Inventory.class)
	public static Predicate inStockSince(QInventory inventory, Date date) {
		return inventory.created.before(date);
	}
	
	@QueryDelegate(Inventory.class)
	public static Predicate like(QInventory qtype, Inventory example) {
	    return example.getInventoryId() != null ? qtype.inventoryId.eq(example.getInventoryId()) : null;
	}
}
