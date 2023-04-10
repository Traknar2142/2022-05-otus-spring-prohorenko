package ru.otus.homework.utill;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.otus.homework.enums.Authority;
import ru.otus.homework.enums.Role;
import ru.otus.homework.exceptions.RoleNotFoundException;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class GrandedAuthorityUtil {
    /* Выдачу тех или иных прав в зависимости от роли впринципе можно сделать несколькими способами:
    *  - Вынести их в отдельную БД таблицу в форме: authority - id роли
    *       Плюсы: все по выдаче нужных authority в зависимости от роли берем из БД
    *       Минусы: накладные расходы из-за регульного обращения к БД т.е. медленно
    *  - Вынести в конфиг файл и с помощью @Value вычитывать из него значения
    *       Плюсы: Бд не надо, все конфигурирется в самом конфиге
    *       Минусы: Потенциально раздутый конфиг файл и необходимость рестартовать приложение для применения
    *               потенциальных изменений
    *  В качестве компромиса сделал утилитарный класс, где захардкодил выдачу authority в зависимости от роли*/
    public static Set<GrantedAuthority> getAuthoritiesByRole(Role role){
        if (role.equals(Role.USER)){
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(Authority.READ.name()));
            return authorities;
        } else if (role.equals(Role.ADMIN)){
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(Authority.CREATE.name()));
            authorities.add(new SimpleGrantedAuthority(Authority.READ.name()));
            authorities.add(new SimpleGrantedAuthority(Authority.UPDATE.name()));
            authorities.add(new SimpleGrantedAuthority(Authority.DELETE.name()));
            return authorities;
        }
        throw new RoleNotFoundException("Role is not found");
    }
}
