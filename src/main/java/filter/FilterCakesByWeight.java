package filter;

import domain.Cake;

public class FilterCakesByWeight implements AbstractFilter<Cake> {
    private int weight;

    public FilterCakesByWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean accept(Cake entity) {
        return entity.getWeight() < weight;
    }
}