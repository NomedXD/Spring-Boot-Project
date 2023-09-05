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
import by.teachmeskills.project.repositories.ProductRepository;
import by.teachmeskills.project.repositories.ProductSearchSpecification;
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
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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
    public Product create(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public List<Product> read() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getCategoryProducts(int categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public Long getCountOfAllProducts() {
        return productRepository.count();
    }

    @Override
    public Long getCountAppropriateProducts(Search search) {
        return productRepository.count(new ProductSearchSpecification(search));
    }

    @Override
    public ModelAndView getSearchedPaginatedProducts(Search search, Integer currentPage, Integer pageSize) {
        ModelMap model = new ModelMap();
        Long count;
        List<Product> productList;
        if ((search == null) || (search.getSearchString() == null)) {
            count = getCountOfAllProducts();
            Pageable pageable = PageRequest.of((currentPage - 1), pageSize,
                    Sort.by("name"));
            productList = productRepository.findAll(pageable).getContent();
        } else {
            count = getCountAppropriateProducts(search);
            Pageable pageable = PageRequest.of((currentPage - 1), pageSize, Sort.by("name"));
            productList = productRepository.findAll(new ProductSearchSpecification(search), pageable).getContent();
            model.addAttribute(EshopConstants.SEARCH_ENTITY, search);
        }
        model.addAttribute(RequestParamsEnum.TOTAL_SEARCH_RESULTS.getValue(), count);
        model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), pageSize);
        model.addAttribute(RequestParamsEnum.CURRENT_PAGE.getValue(), currentPage);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGINATED_VISIBLE_PAGES.getValue(), EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        model.addAttribute(RequestParamsEnum.LAST_PAGE_NUMBER.getValue(), Math.ceil(count / pageSize.doubleValue()));
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productList);
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getPaginatedProductsByCategoryId(Integer categoryId, Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of((currentPage - 1), pageSize);
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.CURRENT_PAGE.getValue(), currentPage);
        model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), pageSize);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGINATED_VISIBLE_PAGES.getValue(), EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        model.addAttribute(RequestParamsEnum.LAST_PAGE_NUMBER.getValue(), Math.ceil(productRepository.countAllByCategoryId(categoryId) / pageSize.doubleValue()));
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productRepository.findAllByCategoryIdOrderByName(categoryId, pageable));
        return new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath(), model);
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
    public void exportCategoryProducts(Integer categoryId, HttpServletResponse response) throws CSVExportException {
        writeCsv(categoryId, response);
    }

    private void writeCsv(Integer categoryId, HttpServletResponse response) throws CSVExportException {
        List<ProductCsv> productCsvList = productCsvConverter.convertInto(productRepository.findAllByCategoryId(categoryId));
        try (Writer productsWriter = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<ProductCsv> productsSbc = new StatefulBeanToCsvBuilder<ProductCsv>(productsWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + "category_" + categoryId + "_products.csv");
            productsSbc.write(productCsvList);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorProductsExportMessage, PagesPathEnum.CATEGORY_PAGE.getPath());
        }
    }

    @Override
    public ModelAndView importCategoryProducts(MultipartFile file, Integer categoryId) throws CSVImportException {
        List<Product> productList = parseCsv(file);
        productRepository.saveAll(productList);
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), productRepository.findAllByCategoryId(categoryId));
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