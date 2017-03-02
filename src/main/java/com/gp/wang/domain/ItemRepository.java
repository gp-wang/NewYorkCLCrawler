package com.gp.wang.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gwang on 10/13/2016.
 */
public interface ItemRepository extends CrudRepository<Item, Integer> {
    List<Item> findAll();
}
