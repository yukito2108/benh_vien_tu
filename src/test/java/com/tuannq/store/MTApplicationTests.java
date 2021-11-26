package com.tuannq.store;

import com.tuannq.store.entity.*;

import com.tuannq.store.repository.*;
import com.tuannq.store.service.CategoryService;
import com.tuannq.store.service.MailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MTApplication.class)
@ActiveProfiles("test")
class MTApplicationTests {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void test() {
        String test = "write test case here!";
        Assertions.assertNotNull(test);
    }

    @Test
    void test1() {
//        Optional<Promotion> promotion = promotionRepository.findByCouponCode("A111");
//        if (promotion.isEmpty()) return;
//        Optional<Orders> orders = ordersRepository.findById(41L);
//        if (orders.isEmpty()) return;
//        Optional<Orders> orders1 = orders.map(o -> {
//            o.setPromotion(new Promotion.UsedPromotion(promotion.get()));
//            return ordersRepository.save(o);
//        });
        Promotion a111 = promotionRepository.checkCouponCode("A222", Set.of(1L, 25L));
        int a = 1;

    }

}
