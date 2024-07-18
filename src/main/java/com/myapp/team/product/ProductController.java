package com.myapp.team.product;

import com.myapp.team.option.Option;
import com.myapp.team.option.OptionService;
import com.myapp.team.user.config.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/prod")
public class ProductController {

    private final ProductService productService;
    private final OptionService optionService;

    @Autowired
    public ProductController(ProductService productService, OptionService optionService) {
        this.productService = productService;
        this.optionService = optionService;
    }

    // 상품 메인 페이지
    @GetMapping("/products")
    public String getProducts(@RequestParam(required = false) String category, Model model) {

        List<Product> products;
//        List<Product> products = productService.findAllProducts();

        // NumberFormat 인스턴스 생성
        NumberFormat nf = NumberFormat.getInstance(Locale.KOREAN);

        // 카테고리 관련 설정쓰
        if (category == null || category.isEmpty()) {
            products = productService.findAllProducts();
        } else {
            products = productService.findProductsByCategory(category);
        }
        // 옵션 여러개 리스트
        for (Product product : products) {
            List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
            product.setOptions(options);
        }

        // 각 상품의 가격을 천 단위로 콤마를 붙여서 문자열로 변환
        for (Product product : products) {
            String priceWithComma = nf.format(product.getProductPrice());
            product.setPriceWithComma(priceWithComma);
        }

        model.addAttribute("products", products);
//        model.addAttribute("products", productService.getAllProducts());
        return "product";
    }


    // 등록된 전체 상품을 리스트로 보여주는 곳 (관리자)
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        // 옵션 여러개면 다 보여주는가ㅓ ..
        for (Product product : products) {
            List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
            product.setOptions(options);
        }
        model.addAttribute("products", products);
        return "list";
    }

    // 상품 추가 페이지
    @GetMapping("/insert")
    public String createProductPage(Model model) throws Exception {
        model.addAttribute("product", new Product());
        return "insert";
    }

    // 상품 추가
    @PostMapping("/insert")
    public String insertProduct(Product product, @RequestParam("optionName") List<String> optionNames,
                                @RequestParam("optionValue") List<String> optionValues,
                                @RequestParam("optionCount") List<Integer> optionCounts,
                                RedirectAttributes ra) {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < optionNames.size(); i++) {
            Option option = new Option();
            option.setOptionName(optionNames.get(i));
            option.setOptionValue(optionValues.get(i));
            option.setOptionCount(optionCounts.get(i));
            options.add(option);
        }
        product.setOptions(options);

        // 이미지 파일 저장 (복붙해온거)
        MultipartFile image = product.getProductImageFile();
        if (image != null && !image.isEmpty()) {
        Date createDate = new Date();
        String storeFileName = createDate.getTime() + "." + image.getOriginalFilename();
        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectory(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storeFileName), StandardCopyOption.REPLACE_EXISTING);
            }
            // 저장된 이미지 경로를 설정
            product.setProductImageName(storeFileName);
            product.setProductImagePath("/images/" + storeFileName);
        } catch (Exception ex) {
            System.out.println("사진 없져" + ex.getMessage());
            }
        }
        productService.insertProduct(product);
        ra.addFlashAttribute("manage_result", product.getProductName());
        return "redirect:/admin/prodlist";
    }


    @PostMapping("/insertOption")
    public String insertOption(Product product, Option option) {
        optionService.insertOption(product, option);
        return "redirect:/admin/prodlist";
    }

    // 상품 수정 페이지
    @GetMapping("/update/{id}")
    public String updateProductPage(@PathVariable("id") int id, Model model) {
        Product product = productService.findProductById(id);
        List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
        product.setOptions(options);
        model.addAttribute("product", product);
        return "update";
    }

    // HTTP POST 요청을 처리, 경로 변수 id를 사용
    @PostMapping("/update/{id}")
    // 경로 변수 id와 모델 속성 product를 받아서 처리 / BindingResult-유효성 검사 결과, RedirectAttributes-리다이렉션 후 속성 전달에 사용
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute Product product, BindingResult result, RedirectAttributes ra) {
        // PathVariable로 전달된 경로 변수 id를 Product 객체의 productNo 필드에 설정
        product.setProductNo(id);

        // 기존 제품 정보를 가져와서 기존 이미지 정보를 유지
        Product existingProduct = productService.findProductById(id);

        // 이미지 파일 저장
        MultipartFile image = product.getProductImageFile();
        if (image != null && !image.isEmpty()) {
            Date createDate = new Date();
            String storeFileName = createDate.getTime() + "." + image.getOriginalFilename();
            try {
                String uploadDir = "public/images/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectory(uploadPath);
                }
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storeFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                // 저장된 이미지 경로를 설정
                product.setProductImageName(storeFileName);
                product.setProductImagePath("/images/" + storeFileName);
            } catch (Exception ex) {
                System.out.println("사진 없져" + ex.getMessage());
            }
        } else {
            // 새로운 이미지가 없으면 기존 이미지를 유지 ...
            product.setProductImageName(existingProduct.getProductImageName());
            product.setProductImagePath(existingProduct.getProductImagePath());
        }

        // Product 먼저 업데이트
        productService.updateProduct(product);
        // 옵션 업데이트
        for (Option option : product.getOptions()) {
            option.setProductId(id);
            optionService.updateOption(option);
        }

        // 결과 메시지를 RedirectAttributes에 추가
        ra.addFlashAttribute("manage_result", product.getProductName());
        System.out.println("update test = " + product);

        // 제품 목록 페이지로 리다이렉션
        return "redirect:/admin/prodlist";
    }


    // 상품 삭제
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        optionService.deleteOption(id);
        productService.deleteProduct(id);
        return "redirect:/admin/prodlist";
    }

    // 옵션 삭제
    @PostMapping("/option/delete")
    public String deleteOption(int optionNo) {
        optionService.deleteOption(optionNo);
        return "redirect:/admin/prodlist";
    }

//    // 상품 상세 페이지
//    @GetMapping("/detail/{id}")
//    public String detailProduct(@PathVariable("id") int no, Model model, Principal principal) {
//        Product product = productService.findProductByNo(no);
//        List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
//        product.setOptions(options);
//        model.addAttribute("product", product);
//
//        if (principal != null) {
//            CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
//            int userNo = userDetails.getUser().getUserNo();
//            model.addAttribute("userNo", userNo);
//        }
//        return "detail";
//    }

    // 상품 상세 페이지
    @GetMapping("/detail/{prodNo}")
    public String detailProduct(@PathVariable("prodNo") int prodNo, Model model, Principal principal) {
        Product product = productService.findProductByNo(prodNo);
        List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
        product.setOptions(options);
        model.addAttribute("product", product);

        DecimalFormat decFormat = new DecimalFormat("###,###");

        if (principal != null) {
            CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
            int userNo = userDetails.getUser().getUserNo();
            System.out.println(userNo);
            model.addAttribute("userNo", userNo);
        }
        return "detail";
    }

}