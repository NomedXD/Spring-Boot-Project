package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Cart;
import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.ProductCsv;
import by.teachmeskills.project.domain.Search;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.ProductRepository;
import by.teachmeskills.project.services.ProductService;
import by.teachmeskills.project.utils.ProductCsvConverter;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCsvConverter productCsvConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, @Lazy ProductCsvConverter productCsvConverter) {
        this.productRepository = productRepository;
        this.productCsvConverter = productCsvConverter;
    }

    @Override
    public Product create(Product entity) throws EntityOperationException {
        return productRepository.create(entity);
    }

    @Override
    public List<Product> read() throws EntityOperationException {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) throws EntityOperationException {
        return productRepository.update(entity);
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        productRepository.delete(id);
    }

    @Override
    public List<Product> getCategoryProducts(int categoryId) throws EntityOperationException {
        return productRepository.getCategoryProducts(categoryId);
    }

    @Override
    public Product getProductById(int id) throws EntityOperationException {
        return productRepository.getProductById(id);
    }

    @Override
    public Long getCountOfAllProducts() throws EntityOperationException {
        return productRepository.getCountOfAllProducts();
    }

    @Override
    public Long getCountAppropriateProducts(Search search) throws EntityOperationException {
        return productRepository.getCountAppropriateProducts(search);
    }

    @Override
    public ModelAndView getPaginatedProducts(Search search, Integer currentPage) throws EntityOperationException {
        ModelMap model = new ModelMap();
        Long count;
        List<Product> productList;
        if ((search == null) || (search.getSearchString() == null)) {
            count = getCountOfAllProducts();
            productList = productRepository.readOrderedByNameInRange((currentPage - 1) * EshopConstants.PAGE_SIZE, EshopConstants.PAGE_SIZE);
        } else {
            count = getCountAppropriateProducts(search);
            productList = productRepository.getSearchedProducts(search, (currentPage - 1) * EshopConstants.PAGE_SIZE, EshopConstants.PAGE_SIZE);
            model.addAttribute(EshopConstants.SEARCH_ENTITY, search);
        }
        model.addAttribute(RequestParamsEnum.TOTAL_SEARCH_RESULTS.getValue(), count);
        model.addAttribute(RequestParamsEnum.CURRENT_PAGE.getValue(), currentPage);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGINATED_VISIBLE_PAGES.getValue(), EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        model.addAttribute(RequestParamsEnum.LAST_PAGE_NUMBER.getValue(), Math.ceil(count / EshopConstants.PAGE_SIZE.doubleValue()));
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productList);
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), model);
    }

    /*
      Внедрение конкретно HttpServletRequest, так как заранее неизвестно, сколько будет параметров в post запросе
     */
    @Override
    public ModelAndView applyProductsQuantity(Cart cart, HttpServletRequest request) {
        for (Product product : cart.getProducts()) {
            String quantity = request.getParameter(product.getId() + "quantity");
            if (quantity != null) {
                Integer currentQuantity = cart.getProductQuantities().get(product.getId());
                cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * (Integer.parseInt(quantity) - currentQuantity));
                cart.getProductQuantities().replace(product.getId(), Integer.parseInt(quantity));
            }
        }
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath());
    }

    @Override
    public ModelAndView exportCategoryProducts(Integer categoryId) throws CSVExportException {
        writeCsv(categoryId);
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productRepository.getCategoryProducts(categoryId));
        model.addAttribute(RequestParamsEnum.EXPORT_IMPORT_MESSAGE.getValue(), EshopConstants.successfulExportMessage);
        return new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath(), model);
    }

    private void writeCsv(Integer categoryId) throws CSVExportException {
        List<ProductCsv> productCsvList = productCsvConverter.convertInto(productRepository.getCategoryProducts(categoryId));
        try (Writer productsWriter = Files.newBufferedWriter(Paths.get("src/main/resources/category_" + categoryId + "_products.csv"))) {
            StatefulBeanToCsv<ProductCsv> productsSbc = new StatefulBeanToCsvBuilder<ProductCsv>(productsWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            productsSbc.write(productCsvList);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorProductsExportMessage, PagesPathEnum.CATEGORY_PAGE.getPath());
        }
    }

    @Override
    public ModelAndView importCategoryProducts(MultipartFile file, Integer categoryId) throws CSVImportException {
        List<Product> productList = parseCsv(file);
        productList.forEach(product -> {
            product.setId(null);
            productRepository.create(product);
        });
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productRepository.getCategoryProducts(categoryId));
        model.addAttribute(RequestParamsEnum.EXPORT_IMPORT_MESSAGE.getValue(), EshopConstants.successfulImportMessage);
        return new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath(), model);
    }

    private List<Product> parseCsv(MultipartFile file) throws CSVImportException {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader productsReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductCsv> productsCtb = new CsvToBeanBuilder<ProductCsv>(productsReader)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withType(ProductCsv.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<ProductCsv> productCsvList = productsCtb.parse();
                return productCsvConverter.convertFrom(productCsvList);
            } catch (IOException e) {
                throw new CSVImportException(EshopConstants.errorProductsImportMessage, PagesPathEnum.CATEGORY_PAGE.getPath());
            }
        } else {
            throw new CSVImportException(EshopConstants.errorFileNullMessage, PagesPathEnum.CATEGORY_PAGE.getPath());
        }
    }
}