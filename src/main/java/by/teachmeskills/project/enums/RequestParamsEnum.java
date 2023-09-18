package by.teachmeskills.project.enums;

import lombok.Getter;

@Getter
public enum RequestParamsEnum {
    SHOPPING_CART_PRODUCTS("cartProductsList"),
    CATEGORIES("categories"),
    PRODUCT("product"),
    PRODUCTS("products"),
    CURRENT_USER("currentUser"),
    ORDERS("orders"),
    MOBILE("mobile"),
    STREET("street"),
    ACCOMMODATION_NUMBER("accommodationNumber"),
    FLAT_NUMBER("flatNumber"),
    TOTAL_PAGINATED_VISIBLE_PAGES("totalPaginatedVisiblePages"),
    CURRENT_PAGE("currentPage"),
    PAGE_SIZE("pageSize"),
    TOTAL_SEARCH_RESULTS("totalSearchResults"),
    LAST_PAGE_NUMBER("lastPageNumber"),
    EXPORT_IMPORT_MESSAGE("eiMessage"),
    DISCOUNT_CODE_MESSAGE("discountCodeMessage");
    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }

}

