package io.xeros.chance;



import java.util.Objects;

/**
 * An item with an common chance.
 *
 * @author C.T For RuneRogue
 */
public class WeightedChance<T> implements WeightedObject<T> {

	/**
	 * The representation.
	 */
	private final T representation;

	/**
	 * The weight type.
	 */
	private double weight;
	
	public static final double COMMON = 10;
	public static final double UNCOMMON = 7;
	public static final double RARE = 3;
	public static final double VERY_RARE = 1;
	public static final double LEGENDARY = 0.5;//0.50% Chance?
	

	public WeightedChance(double weight, T representation) {
		if (weight <= 0) {
			throw new IllegalArgumentException("The weight of an item must be larger than zero!");
		}
		this.representation = Objects.requireNonNull(representation);
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public T get() {
		return representation;
	}
	
	@Override
	public String toString() {
		return "[Object: " + get() + ", " + "Weight: " + getWeight() + "]";
	}

	@Override
	public int compareTo(WeightedObject<T> o) {
		return (int) Math.signum(getWeight() - o.getWeight());
	}
}