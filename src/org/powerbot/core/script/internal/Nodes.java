package org.powerbot.core.script.internal;

import org.powerbot.game.client.HardReference;
import org.powerbot.game.client.HashTable;
import org.powerbot.game.client.Node;
import org.powerbot.game.client.SoftReference;

public class Nodes {
	public static Object lookup(final HashTable nc, final long id) {
		final Node[] buckets;
		if (nc == null || (buckets = nc.getBuckets()) == null || id < 0) {
			return null;
		}

		final Node n = buckets[(int) (id & buckets.length - 1)];
		for (Node node = n.getNext(); node != n && node != null; node = node.getNext()) {
			if (node.getId() == id) {
				if (node instanceof SoftReference) {
					return ((java.lang.ref.SoftReference) ((SoftReference) node).get()).get();
				} else if (node instanceof HardReference) {
					return ((HardReference) node).get();
				} else {
					return node;
				}
			}
		}
		return null;
	}
}