package MyEat.myeat.service;

import MyEat.myeat.domain.Menu;
import MyEat.myeat.domain.Restaurant;
import MyEat.myeat.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public void save(Menu menu) {
        menuRepository.save(menu);
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id);
    }

    public void saveMenus(List<String> menuNames, List<Long> menuPrices, Restaurant restaurant) {
        for (int i=0; i<menuNames.size(); i++) {
            Menu menu = new Menu();
            menu.setMenuName(menuNames.get(i));
            menu.setMenuPrice(menuPrices.get(i));
            menu.setRestaurant(restaurant);
            save(menu);
            restaurant.getMenus().add(menu);
        }
    }
}
