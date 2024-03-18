package be.bds.bdsbes.entities.enums;

public enum StatusRoom {

    STATUS1(1, "HD", "Hoạt động"),
    STATUS2(2, "KHD", "Không hoạt động");

    private int id;
    private String code;
    private String name;

    StatusRoom() {

    }

    StatusRoom(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
