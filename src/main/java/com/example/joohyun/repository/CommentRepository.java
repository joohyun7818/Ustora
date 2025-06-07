package com.example.joohyun.repository;

import com.example.joohyun.entity.Comment;
import com.example.joohyun.entity.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProduct(Product product);

    @Override
    void flush();

    @Override
    <S extends Comment> S saveAndFlush(S entity);

    @Override
    <S extends Comment> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<Comment> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    Comment getOne(Long aLong);

    @Override
    Comment getById(Long aLong);

    @Override
    Comment getReferenceById(Long aLong);

    @Override
    <S extends Comment> List<S> findAll(Example<S> example);

    @Override
    <S extends Comment> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Comment> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Comment> findAll();

    @Override
    List<Comment> findAllById(Iterable<Long> longs);

    @Override
    <S extends Comment> S save(S entity);

    @Override
    Optional<Comment> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Comment entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends Comment> entities);

    @Override
    void deleteAll();

    @Override
    List<Comment> findAll(Sort sort);

    @Override
    Page<Comment> findAll(Pageable pageable);

    @Override
    <S extends Comment> Optional<S> findOne(Example<S> example);

    @Override
    <S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Comment> long count(Example<S> example);

    @Override
    <S extends Comment> boolean exists(Example<S> example);

    @Override
    <S extends Comment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
