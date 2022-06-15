package com.sample.shop.login.domain.repository;

import com.sample.shop.login.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

}
