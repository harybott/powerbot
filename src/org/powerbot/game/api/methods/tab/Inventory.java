package org.powerbot.game.api.methods.tab;

import java.util.LinkedList;
import java.util.List;

import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class Inventory {
	public static final int WIDGET = 679;
	public static final int WIDGET_BANK = 763;
	public static final int WIDGET_PRICE_CHECK = 204;
	public static final int WIDGET_EQUIPMENT_BONUSES = 670;
	public static final int WIDGET_EXCHANGE = 644;
	public static final int WIDGET_SHOP = 621;
	public static final int WIDGET_DUNGEONEERING_SHOP = 957;
	public static final int WIDGET_BEAST_OF_BURDEN_STORAGE = 665;

	public static final int[] ALT_WIDGETS = {
			WIDGET_BANK,
			WIDGET_PRICE_CHECK, WIDGET_EQUIPMENT_BONUSES,
			WIDGET_EXCHANGE, WIDGET_SHOP, WIDGET_DUNGEONEERING_SHOP,
			WIDGET_BEAST_OF_BURDEN_STORAGE
	};

	public static Item[] getItems() {
		return getItems(true);
	}

	public static Item[] getItems(final boolean cached) {
		final WidgetChild inventoryWidget = getWidget(cached);
		if (inventoryWidget != null) {
			final WidgetChild[] inventoryChildren = inventoryWidget.getChildren();
			if (inventoryChildren.length > 27) {
				final List<Item> items = new LinkedList<Item>();
				for (int i = 0; i < 28; ++i) {
					if (inventoryChildren[i].getChildId() != -1) {
						items.add(new Item(inventoryChildren[i]));
					}
				}
				return items.toArray(new Item[items.size()]);
			}
		}
		return new Item[0];
	}

	public static int getCount() {
		return getItems().length;
	}

	public static int getCount(final int id) {
		return getCount(false, new Filter<Item>() {
			public boolean accept(final Item item) {
				return item.getId() == id;
			}
		});
	}

	public static int getCount(final Filter<Item> itemFilter) {
		return getCount(false, itemFilter);
	}

	public static int getCount(final boolean countStack, final int id) {
		return getCount(countStack, new Filter<Item>() {
			public boolean accept(final Item item) {
				return item.getId() == id;
			}
		});
	}

	public static int getCount(final boolean countStack, final Filter<Item> itemFilter) {
		final Item[] items = getItems();
		int count = 0;
		for (final Item item : items) {
			if (item != null && itemFilter.accept(item)) {
				if (countStack) {
					count += item.getStackSize();
				} else {
					++count;
				}
			}
		}
		return count;
	}

	public static WidgetChild getWidget(final boolean cached) {
		for (final int widget : ALT_WIDGETS) {
			WidgetChild inventory = Widgets.get(widget, 0);
			if (inventory != null && inventory.getAbsoluteX() > 50) {
				return inventory;
			}
		}
		if (!cached) {
			Tabs.INVENTORY.open(true);
		}
		return Widgets.get(WIDGET, 0);
	}
}
