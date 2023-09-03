package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.EntityOperationException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public interface CategoryService extends BaseService<Category>{
    void getCategoryByName(String name) throws EntityOperationException;

    Optional<Category> getCategoryById(Integer id) throws EntityOperationException;

    void exportCategories(HttpServletResponse response) throws CSVExportException;

    ModelAndView importCategories(MultipartFile file) throws CSVImportException;
}
