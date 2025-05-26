package com.example.joohyun.repository;

import com.example.joohyun.entity.Order;
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

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByOrderDateDesc(User user);

    @Override
    void flush();

    @Override
    <S extends Order> S saveAndFlush(S entity);

    @Override
    <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<Order> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    Order getOne(Long aLong);

    @Override
    Order getById(Long aLong);

    @Override
    Order getReferenceById(Long aLong);

    @Override
    <S extends Order> List<S> findAll(Example<S> example);

    @Override
    <S extends Order> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Order> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Order> findAll();

    @Override
    List<Order> findAllById(Iterable<Long> longs);

    @Override
    <S extends Order> S save(S entity);

    @Override
    Optional<Order> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Order entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends Order> entities);

    @Override
    void deleteAll();

    @Override
    List<Order> findAll(Sort sort);

    @Override
    Page<Order> findAll(Pageable pageable);

    @Override
    <S extends Order> Optional<S> findOne(Example<S> example);

    @Override
    <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Order> long count(Example<S> example);

    @Override
    <S extends Order> boolean exists(Example<S> example);

    @Override
    <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
