package com.example.demo;

import com.example.demo.authorization.entities.AccountEntity;
import com.example.demo.authorization.entities.RoleEntity;
import com.example.demo.authorization.repositories.AccountRepository;
import com.example.demo.authorization.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class DemoApplicationTests {
	@Test
	void contextLoads() {
	}

	private void insertUsers(AccountRepository accountRepository, RoleRepository roleRepository) {
		RoleEntity admin = new RoleEntity(1L, "ADMIN");
		RoleEntity user = new RoleEntity(2L, "USER");
		roleRepository.save(admin);
		roleRepository.save(user);

		ArrayList<RoleEntity> role = new ArrayList<>();
		role.add(admin);
		role.add(user);

		accountRepository.save(new AccountEntity(1L,
				"Admin",
				"Admin",
				"admin@admin.com",
				"89270035410",
				"$2y$10$EOpKQmCqOK84nIRgK49nGuVFzBTZUqZcFqoPo.0Ue1mLH4AecWqFO",
				role));
		//пароль: admin
	}
}
