package MyEat.myeat.controller;

import MyEat.myeat.service.RiderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Tag(name="Rider API", description = "라이더 API")
@Controller
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;


}
