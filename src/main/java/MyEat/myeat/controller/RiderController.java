package MyEat.myeat.controller;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.service.RiderService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(name="Rider API", description = "라이더 API")
@Controller
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @GetMapping(value = "/riders/new")
    public String createForm(Model model) {
        model.addAttribute("RiderForm", new RiderForm());
        return "riders/createRiderForm";
    }

    @Operation(summary = "Rider 등록")
    @PostMapping(value = "/riders/new")
    public String createRider(@Valid RiderForm riderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "riders/createRiderForm";
        }
        Rider rider = riderService.createRiderFromForm(riderForm);
        riderService.join(rider);
        return "redirect:/";
    }

    @Operation(summary = "계약 가능한 Rider 목록 Sync")
    @GetMapping(value = "/riders/list/sync")
    public String showContractYetRidersSync(HttpSession session, Model model) {
        long startTime = System.currentTimeMillis();
        if(session.getAttribute("customerLoggedIn") == null) {
            return "customers/login";
        }

        Customer customer = (Customer) session.getAttribute("customerLoggedIn");
        double userLat = customer.getLatitude();
        double userLon = customer.getLongitude();

        List<Rider> riders = riderService.findContractYet(userLat,userLon);
        model.addAttribute("riders", riders);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("추천 알고리즘 적용한 라이더 목록 반환 소요 시간 : "+ totalTime + "ms");
        return "riders/contractYetRidersList";
    }

    @Operation(summary = "계약 가능한 Rider 목록 ASync")
    @GetMapping(value = "/riders/list/async")
    public String showContractYetRidersAsync(HttpSession session, Model model) {
        long startTime = System.currentTimeMillis();
        if(session.getAttribute("customerLoggedIn") == null) {
            return "customers/login";
        }

        Customer customer = (Customer) session.getAttribute("customerLoggedIn");
        double userLat = customer.getLatitude();
        double userLon = customer.getLongitude();

        CompletableFuture<List<Rider>> ridersFuture = riderService.findContractYetAsync(userLat,userLon);

        try {
            List<Rider> riders = ridersFuture.get();
            model.addAttribute("riders", riders);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("추천 알고리즘 적용한 라이더 목록 반환 소요 시간(비동기) : "+ totalTime + "ms");
        return "riders/contractYetRidersList";
    }

//    //추천 알고리즘 제외
//    @Operation(summary = "계약 가능한 Rider 목록")
//    @GetMapping(value = "/riders/list")
//    public String showContractYetRiders(HttpSession session, Model model) {
//        long startTime = System.currentTimeMillis();
//        if(session.getAttribute("customerLoggedIn") == null) {
//            return "customers/login";
//        }
//
////        Customer customer = (Customer) session.getAttribute("customerLoggedIn");
////        double userLat = customer.getLatitude();
////        double userLon = customer.getLongitude();
//        //Page<Rider> riders = riderService.findContractYet(pageable);
//        List<Rider> riders = riderService.findContractYetnorec();
//        model.addAttribute("riders", riders);
//        long endTime = System.currentTimeMillis();
//        long totalTime = endTime - startTime;
//        System.out.println(totalTime);
//        return "riders/contractYetRidersList";
//    }


}
