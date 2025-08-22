package com.example.joohyun.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;

import com.example.joohyun.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM Product ORDER BY RAND() LIMIT :limitCount", nativeQuery = true)
    List<Product> findRandomProductsNative(@Param("limitCount") int limitCount);

    List<Product> findTop4ByOrderByPid();

    List<Product> findTop5ByOrderByPidDesc();

    List<Product> findTop3ByOrderByPriceDesc();

    List<Product> findTop6ByOrderByPriceDesc();

    @Override
    void flush();

    @Override
    <S extends Product> S saveAndFlush(S entity);

    @Override
    <S extends Product> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<Product> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    Product getOne(Long aLong);

    @Override
    Product getById(Long aLong);

    @Override
    Product getReferenceById(Long aLong);

    @Override
    <S extends Product> List<S> findAll(Example<S> example);

    @Override
    <S extends Product> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Product> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Product> findAll();

    @Override
    List<Product> findAllById(Iterable<Long> longs);

    @Override
    <S extends Product> S save(S entity);

    @Override
    Optional<Product> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Product entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends Product> entities);

    @Override
    void deleteAll();

    @Override
    List<Product> findAll(Sort sort);

    @Override
    Page<Product> findAll(Pageable pageable);

    @Override
    <S extends Product> Optional<S> findOne(Example<S> example);

    @Override
    <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Product> long count(Example<S> example);

    @Override
    <S extends Product> boolean exists(Example<S> example);

    @Override
    <S extends Product, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
