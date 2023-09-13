package by.teachmeskills.project.utils;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.dto.CategoryCsv;
import by.teachmeskills.project.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CategoryCsvConverter {
    private final ImageService imageService;

    @Autowired
    public CategoryCsvConverter(ImageService imageService) {
        this.imageService = imageService;
    }

    public List<CategoryCsv> convertInto(List<Category> categoryList) {
        List<CategoryCsv> categoryCsvList = new ArrayList<>();
        categoryList.forEach(category -> categoryCsvList.add(CategoryCsv.builder()
                .id(category.getId())
                .name(category.getName())
                .imageId(category.getImages().get(0).getId())
                .sometext(category.getSometext()).build()));
        return categoryCsvList;
    }

    public List<Category> convertFrom(List<CategoryCsv> categoryCsvList) {
        return map(categoryCsvList);
    }

    private List<Category> map(List<CategoryCsv> categoryCsvList) {
        List<Category> categoryList = new ArrayList<>();
        categoryCsvList.forEach(categoryCsv -> categoryList.add(Category.builder()
                .name(categoryCsv.getName())
                .images(new ArrayList<>(Collections.singletonList(imageService.getImageById(categoryCsv.getImageId()).orElse(null))))
                .sometext(categoryCsv.getSometext())
                .productList(new ArrayList<>()).build()));
        return categoryList;
    }
}
