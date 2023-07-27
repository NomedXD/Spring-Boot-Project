package by.teachmeskills.project.enums;

public enum PagesPathEnum {
    SHOP_PAGE("shop"),
    LOG_IN_PAGE("login"),
    REGISTRATION_PAGE("register"),
    CATEGORY_PAGE("category"),
    CART_PAGE("cart"),
    PRODUCT_PAGE("product"),
    ACCOUNT_PAGE("account"),
    SEARCH_PAGE("search"),
    ERROR_PAGE("error"),
    PROJECT_INFO_PAGE("project-info");

    private final String path;

    PagesPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

