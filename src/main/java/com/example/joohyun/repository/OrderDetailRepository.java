package com.example.joohyun.repository;

import com.example.joohyun.entity.OrderDetail;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Override
    void flush();

    @Override
    <S extends OrderDetail> S saveAndFlush(S entity);

    @Override
    <S extends OrderDetail> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<OrderDetail> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    OrderDetail getOne(Long aLong);

    @Override
    OrderDetail getById(Long aLong);

    @Override
    OrderDetail getReferenceById(Long aLong);

    @Override
    <S extends OrderDetail> List<S> findAll(Example<S> example);

    @Override
    <S extends OrderDetail> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

    @Override
    List<OrderDetail> findAll();

    @Override
    List<OrderDetail> findAllById(Iterable<Long> longs);

    @Override
    <S extends OrderDetail> S save(S entity);

    @Override
    Optional<OrderDetail> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(OrderDetail entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends OrderDetail> entities);

    @Override
    void deleteAll();

    @Override
    List<OrderDetail> findAll(Sort sort);

    @Override
    Page<OrderDetail> findAll(Pageable pageable);

    @Override
    <S extends OrderDetail> Optional<S> findOne(Example<S> example);

    @Override
    <S extends OrderDetail> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends OrderDetail> long count(Example<S> example);

    @Override
    <S extends OrderDetail> boolean exists(Example<S> example);

    @Override
    <S extends OrderDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
