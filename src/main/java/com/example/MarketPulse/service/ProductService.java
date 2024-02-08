package com.example.MarketPulse.service;

import com.example.MarketPulse.dto.ProductDto;
import com.example.MarketPulse.exceptions.ExtensionNotSupportedException;
import com.example.MarketPulse.exceptions.ResourceNotFoundException;
import com.example.MarketPulse.model.Category;
import com.example.MarketPulse.model.Product;
import com.example.MarketPulse.model.User;
import com.example.MarketPulse.repository.CategoryRepository;
import com.example.MarketPulse.repository.ProductRepository;
import com.example.MarketPulse.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DtoMapperService dtoMapperService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, DtoMapperService dtoMapperService, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.dtoMapperService = dtoMapperService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public ProductDto createProduct(ProductDto productDto) {
        User seller = userRepository.findById(productDto.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + productDto.getSellerId()));
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productDto.getCategoryId()));

        Product product = dtoMapperService.productDtoToProduct(productDto, seller, category);
        Product savedProduct = productRepository.save(product);

        return dtoMapperService.productToProductDto(savedProduct);
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {

        boolean categoryExists = categoryRepository.existsById(categoryId);
        if (!categoryExists) {
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }
        List <Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
            .map(dtoMapperService :: productToProductDto)
            .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProducts() {
        List<Product>products = productRepository.findAll();
        return products.stream()
                .map(dtoMapperService :: productToProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product niet gevonden met id: " + productId));
        return dtoMapperService.productToProductDto(product);
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product niet gevonden met ID: " + productId));

        if (productDto.getPrice() >= 0) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getDescription() != null){
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getSellerId() != null){
            User seller = userRepository.findById(productDto.getSellerId())
                    .orElseThrow(()-> new ResourceNotFoundException("User not found with ID: " + productDto.getSellerId()));
            product.setSeller(seller);
        }
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(()-> new ResourceNotFoundException("Category not found with ID: " + productDto.getCategoryId()));
            product.setCategory(category);
        }

        Product updatedProduct = productRepository.save(product);

        return dtoMapperService.productToProductDto(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


    public Optional<Product> findImage(String fileName) {
        return productRepository.findByFileName(fileName);
    }

    public ResponseEntity<byte[]> singleFileDownload(String fileName, HttpServletRequest request) {
        Optional<Product> optionalProductFile = productRepository.findByFileName(fileName);

        if (!optionalProductFile.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Product productFile = optionalProductFile.get();

        // Verondersteld dat je de binaire data van de afbeelding wilt terugsturen
        // Je moet de binaire data van de afbeelding laden, niet de URL
        byte[] imageData = loadProductImageData(productFile); // Implementeer deze methode

        if (imageData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String mimeType = request.getServletContext().getMimeType(productFile.getFileName());
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // Standaard MIME-type als niet gedetecteerd
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + productFile.getFileName() + "\"")
                .body(imageData);
    }

    private byte[] loadProductImageData(Product product) {
        // Veronderstelt dat product.getImageData() de binaire data van de afbeelding retourneert
        return product.getImageData();
    }



    public void deleteProductImage(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with ID: " + productId));

            product.setFileName(null);
            product.setImageData(null);
            productRepository.save(product);
        }

    private boolean isJpgOrJpeg(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileExtension = "";
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension);
    }

    public Product uploadFileDocument(MultipartFile file, Long productId) throws IOException, ExtensionNotSupportedException {
        if (!isJpgOrJpeg(file)) {
            throw new ExtensionNotSupportedException("Only .jpg or .jpeg files are allowed");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        product.setFileName(name);
        product.setImageData(file.getBytes());

        return productRepository.save(product);
    }
}

