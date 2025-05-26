package com.example.joohyun.repository;

import com.example.joohyun.entity.Cart;
import com.example.joohyun.entity.Product;
import com.example.joohyun.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndProduct(User user, Product product);

    @Override
    void flush();

    @Override
    <S extends Cart> S saveAndFlush(S entity);

    @Override
    <S extends Cart> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<Cart> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    Cart getOne(Long aLong);

    @Override
    Cart getById(Long aLong);

    @Override
    Cart getReferenceById(Long aLong);

    @Override
    <S extends Cart> List<S> findAll(Example<S> example);

    @Override
    <S extends Cart> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Cart> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Cart> findAll();

    @Override
    List<Cart> findAllById(Iterable<Long> longs);

    @Override
    <S extends Cart> S save(S entity);

    @Override
    Optional<Cart> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Cart entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends Cart> entities);

    @Override
    void deleteAll();

    @Override
    List<Cart> findAll(Sort sort);

    @Override
    Page<Cart> findAll(Pageable pageable);

    @Override
    <S extends Cart> Optional<S> findOne(Example<S> example);

    @Override
    <S extends Cart> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Cart> long count(Example<S> example);

    @Override
    <S extends Cart> boolean exists(Example<S> example);

    @Override
    <S extends Cart, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
