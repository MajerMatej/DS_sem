package BE;

public class Store {
    private int store_id;
    private String store_name;
    private String city;
    private String street;

    public Store(int store_id, String store_name, String city, String street) {
        this.store_id = store_id;
        this.store_name = store_name;
        this.city = city;
        this.street = street;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Store name: '" + store_name + " City: " + city + " Street: '" + street +"\n";
    }
}
