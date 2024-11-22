package MyEat.myeat.controller;

import MyEat.myeat.domain.Restaurant;
import MyEat.myeat.domain.RestaurantDTO;
import MyEat.myeat.service.MenuService;
import MyEat.myeat.service.RestaurantService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Restaurant API", description = "음식점 API")
@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Hidden
    @GetMapping(value = "/restaurants/new")
    public String createForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurants/createRestaurantForm";
    }

    @Operation(summary = "Restaurant 등록")
    @PostMapping(value = "/restaurants/new")
    public String create(@Valid RestaurantForm restaurantForm, BindingResult bindingResult,
                         @RequestParam("menuNames") List<String> menuNames, @RequestParam(name = "menuPrices") List<Long> menuPrices,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "restaurants/createRestaurantForm";
        }
        Restaurant restaurant = restaurantService.createRestaurantFromForm(restaurantForm);

        try {
            restaurantService.join(restaurant);
            menuService.saveMenus(menuNames, menuPrices, restaurant);
        } catch (RestaurantService.DuplicateRestaurantException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("showAlert",true);
            return "restaurants/createRestaurantForm";
        }
        return "redirect:/";
    }


//    @Operation(summary = "음식점 목록")
//    @GetMapping(value = "/restaurants/list")
//    public String restaurantsList(HttpSession session, Model model) {
//        long startTime = System.currentTimeMillis();
//        if(session.getAttribute("customerLoggedIn") == null) {
//            return "customers/login";
//        }
//
//        List<Restaurant> restaurants =  restaurantService.findAll();
//        model.addAttribute("restaurants", restaurants);
//        long endTime = System.currentTimeMillis();
//        long totalTime = endTime - startTime;
//        System.out.println("음식점 응답 시간 : "+ totalTime);
//        return "restaurants/restaurantsList";
//
//    }

    @Operation(summary = "음식점 목록")
    @GetMapping(value = "/restaurants/list")
    public String restaurantsList(HttpSession session, Model model) {
        long startTime = System.currentTimeMillis();
        if(session.getAttribute("customerLoggedIn") == null) {
            return "customers/login";
        }

        List<RestaurantDTO> restaurants =  restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("음식점 응답 시간 : "+ totalTime);
        return "restaurants/restaurantsList";

    }



}
