package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public interface CategoryService extends BaseService<Category>{
    void getCategoryByName(String name);

    Optional<Category> getCategoryById(Integer id);

    void exportCategories(HttpServletResponse response) throws CSVExportException;

    ModelAndView getPaginatedCategories(Integer currentPage, Integer pageSize);

    ModelAndView importCategories(MultipartFile file) throws CSVImportException;
}
