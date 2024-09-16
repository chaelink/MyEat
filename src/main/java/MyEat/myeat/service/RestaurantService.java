package MyEat.myeat.service;

import MyEat.myeat.domain.Restaurant;
import MyEat.myeat.repository.ReataurantJPARepository;
import MyEat.myeat.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ReataurantJPARepository restaurantJPARepository;


    public Long join(Restaurant restaurant) {
        validateDuplicateRestaurant(restaurant);
        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    private void validateDuplicateRestaurant(Restaurant restaurant) {
        Restaurant findRestaurant = restaurantRepository.findByLoginId(restaurant.getLoginId());
        if (findRestaurant != null) {
            throw new DuplicateRestaurantException("이미 존재하는 아이디입니다.");
        }
    }

    //사용자 정의 예외 클래스
    public class DuplicateRestaurantException extends RuntimeException {
        public DuplicateRestaurantException(String message) {
            super(message);
        }
    }

    public Page<Restaurant> findAll(Pageable pageable) {
        //return restaurantRepository.findAll();
        return restaurantJPARepository.findAll(pageable);

    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id);
    }
}
