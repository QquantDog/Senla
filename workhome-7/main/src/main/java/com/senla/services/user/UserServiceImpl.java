package com.senla.services.user;

import com.senla.dao.driver.DriverDao;
import com.senla.dao.role.RoleDao;
import com.senla.dao.user.UserDao;
import com.senla.dto.user.UserCreateDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.exceptions.DaoException;
import com.senla.models.driver.Driver;
import com.senla.models.role.Role;
import com.senla.models.user.User;
import com.senla.specification.UserSpecification;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl extends AbstractLongIdGenericService<User> implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = userDao;
    }

    @Override
//    @Transactional
    public void findByEmail(String email) {
        Optional<User> user = userDao.findByEmail(email);
        user.ifPresentOrElse(System.out::println, ()-> System.out.println("No user with such email found"));
    }

    @Override
    public void allJoinWithPassengers() {
        List<Object[]> arr = userDao.allJoinWithPassengers();
        arr.forEach((row) -> {
            System.out.println("User_firstname: " + row[0]);
            System.out.println("User_role_name: " + row[1]);
        });
    }

    @Override
    public void findAllActiveDriversWithAccountRegisteredForMoreThanParameter(Integer months) {
        List<User> users = userDao.findAllActiveDriversWithAccountRegisteredForMoreThanMonths(months);
        System.out.println("months: " + months);
        users.forEach(System.out::println);
    }

    @Override
    public void findUserBySpecification(String email, String firstName, String lastName, LocalDateTime from, LocalDateTime to) {
        Specification<User> spec = UserSpecification.buildSpecification(email, firstName, lastName, from, to);

        userDao.findUserBySpecification(spec).forEach(System.out::println);
    }

    @Override
    @Transactional
    public User createUser(UserCreateDto dto) {

        Optional<Role> resp = roleDao.findById(dto.getRoleId());
        if(resp.isEmpty()) throw new DaoException("Role not found");

        Role role = resp.get();
        User user = modelMapper.map(dto, User.class);
        user.setRole(role);
        user.setRegistrationDate(LocalDateTime.now());

        if(role.getRoleName().equals("driver")) {
            Driver driver = getEmptyDriverDetails();
            user.setDriver(driver);
            driver.setUser(user);
        }
        return abstractDao.create(user);

//        return user;
    }

//    апдейт юзера не подразумевает переход его в другую роль - создается новы акк
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User updateUser(Long id, UserUpdateDto dto) {
        Optional<User> resp = abstractDao.findById(id);
        if (resp.isEmpty()) throw new DaoException("Can't find entity with id " + id);
        else {
            User userToUpdate = resp.get();
            modelMapper.map(dto, userToUpdate);
            abstractDao.update(userToUpdate);
            return userToUpdate;
        }
    }

    private Driver getEmptyDriverDetails(){
        return Driver.builder().isOnShift(false).isOnRide(false).build();
    }
}
