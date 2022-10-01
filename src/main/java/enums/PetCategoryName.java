package enums;

public enum PetCategoryName {

    DOGS(1, "dogs"),
    CATS(2, "cats"),
    OTHER(3, "other");

    private int id;
    private String categoryName;

    PetCategoryName(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
