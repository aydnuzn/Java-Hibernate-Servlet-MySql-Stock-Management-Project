package appPack;

public enum KdvRate {
    Percent_zero("0"),
    Percent_one("1"),
    Percent_eight("8"),
    Percent_eighteen("18");

    private final String name;

    private KdvRate(String name) {
        this.name = name;
    }

    public String getDesc(){
        return this.name;
    }

}
