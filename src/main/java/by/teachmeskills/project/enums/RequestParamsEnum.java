package by.teachmeskills.project.enums;

public enum RequestParamsEnum {
    SHOPPING_CART_PRODUCTS("cartProductsList"),
    CATEGORIES("categories"),
    PRODUCT("product"),
    PRODUCTS("products"),
    CURRENT_USER("currentUser"),
    MOBILE("mobile"),
    STREET("street"),
    ACCOMMODATION_NUMBER("accommodationNumber"),
    FLAT_NUMBER("flatNumber"),
    TOTAL_PAGINATED_VISIBLE_PAGES("totalPaginatedVisiblePages"),
    CURRENT_PAGE("currentPage"),
    TOTAL_SEARCH_RESULTS("totalSearchResults"),
    LAST_PAGE_NUMBER("lastPageNumber"),
    EXPORT_IMPORT_MESSAGE("eiMessage");
    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

