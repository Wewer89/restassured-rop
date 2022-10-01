package enums;

public enum PetTagName {

    YOUNG_PET(1, "young_pet"),
    ADULT_PET(2, "adult_pet");

    private int id;
    private String tagName;

    PetTagName(int id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public int getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }
}
