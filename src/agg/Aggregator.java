package agg;

import data.SimpleVector;

public interface Aggregator {
	public SimpleVector aggregateWith(SimpleVector attributes);
}
