package by.teachmeskills.project.enums;

public enum RequestParamsEnum {
    MAIL("mail"),
    NAME("name"),
    SURNAME("surname"),
    DATE("date"),
    PASSWORD("password"),
    REPEATPASSWORD("repeatPassword"),
    COMMAND("command"),
    SHOPPING_CART("cart"),
    SHOPPING_CART_PRODUCTS("cartProductsList"),
    CATEGORIES("categories"),
    CATEGORYID("categoryid"),
    PRODUCT("product"),
    PRODUCTS("products"),
    PRODUCT_ID("productid"),
    CURRENT_USER("currentUser"),
    MOBILE("mobile"),
    STREET("street"),
    ACCOMMODATION_NUMBER("accommodationNumber"),
    FLAT_NUMBER("flatNumber"),
    SEARCH_FIELD("searchField");
    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

