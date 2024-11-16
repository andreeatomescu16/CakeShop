package filter;

import domain.Cake;

public class FilterCakesByPrice implements AbstractFilter<Cake> {
    private int price;

    public FilterCakesByPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean accept(Cake entity) {
        return entity.getWeight() > price;
    }
}