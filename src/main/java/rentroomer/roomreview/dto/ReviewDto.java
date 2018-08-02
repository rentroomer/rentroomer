package rentroomer.roomreview.dto;

public class ReviewDto {

    private int cost;
    private int deposit;
    private String structure;

    public ReviewDto() {
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "cost=" + cost +
                ", deposit=" + deposit +
                ", structure='" + structure + '\'' +
                '}';
    }
}
