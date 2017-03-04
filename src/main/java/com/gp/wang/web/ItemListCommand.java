package com.gp.wang.web;

import com.gp.wang.domain.Item;
import lombok.Data;
import org.springframework.beans.support.PagedListHolder;

import java.io.Serializable;

/**
 * Created by gaopeng on 3/3/17.
 */
@Data
public class ItemListCommand implements Serializable{

    String keyword;
    PagedListHolder<Item> pagedItems;
}
