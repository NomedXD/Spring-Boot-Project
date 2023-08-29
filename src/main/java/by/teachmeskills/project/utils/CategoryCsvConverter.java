package by.teachmeskills.project.utils;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.domain.CategoryCsv;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.services.ImageService;
import by.teachmeskills.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryCsvConverter {

    private final UserService userService;
    private final ImageService imageService;
    private final CategoryService categoryService;

    @Autowired
    public CategoryCsvConverter(UserService userService, ImageService imageService, CategoryService categoryService) {
        this.userService = userService;
        this.imageService = imageService;
        this.categoryService = categoryService;
    }

    public List<CategoryCsv> convertInto(List<Category> categoryList) {
        List<CategoryCsv> categoryCsvList = new ArrayList<>();
        categoryList.forEach(category -> categoryCsvList.add(CategoryCsv.builder()
                .id(category.getId())
                .name(category.getName())
                .imageId(category.getImage().getId())
                .sometext(category.getSometext()).build()));
        return categoryCsvList;
    }

    public List<Category> convertFrom(List<CategoryCsv> categoryCsvList) {
        return map(categoryCsvList);
    }

    private List<Category> map(List<CategoryCsv> categoryCsvList) {
        List<Category> categoryList = new ArrayList<>();
        categoryCsvList.forEach(categoryCsv -> {
            categoryList.add(Category.builder()
                    .id(categoryCsv.getId())
                    .name(categoryCsv.getName())
                    .image(imageService.getImageById(categoryCsv.getImageId()))
                    .sometext(categoryCsv.getSometext())
                    .productList(new ArrayList<>()).build());
        });
        return categoryList;
    }
}
