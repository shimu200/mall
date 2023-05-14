package com.atguigu.gulimall.cart.controller;

import com.atguigu.gulimall.cart.interceptor.CartInterceptor;
import com.atguigu.gulimall.cart.service.CartService;
import com.atguigu.gulimall.cart.vo.Cart;
import com.atguigu.gulimall.cart.vo.CartItem;
import com.atguigu.gulimall.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.concurrent.ExecutionException;


@Controller
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId")Long skuId,
                            @RequestParam("check")Integer check){
        cartService.checkItem(skuId, check);
        return "redirect:http://cart.gulimall.com/cart.html";
    }
    @GetMapping("/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException{
//        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
//        System.out.println(userInfoTo);
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            RedirectAttributes ra) throws ExecutionException, InterruptedException {
//        CartItem cartItem = cartService.addToCart(skuId,num);
//        model.addAttribute("item",cartItem);
        cartService.addToCart(skuId,num);
        ra.addAttribute("skuId",skuId);
        return "redirect:http://cart.gulimall.com/addToCartSuccess.html";
    }

    @GetMapping(value = "/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId,
                                       Model model) {
        //重定向到成功页面。再次查询购物车数据即可
        CartItem cartItem = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItem);
        return "success";
    }
}
