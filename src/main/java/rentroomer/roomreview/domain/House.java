package rentroomer.roomreview.domain;

import javax.persistence.Entity;

@Entity
public class House extends AbstractEntity {

    private int cost;
    private int deposit;
    private String structure;

    public int getCost() {
        return cost;
    }

    public int getDeposit() {
        return deposit;
    }

    public String getStructure() {
        return structure;
    }
}
