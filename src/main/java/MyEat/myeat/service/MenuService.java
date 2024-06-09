package MyEat.myeat.service;

import MyEat.myeat.domain.Menu;
import MyEat.myeat.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

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
}
