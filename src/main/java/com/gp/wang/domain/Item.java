package com.gp.wang.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by gwang on 10/13/2016.
 */
@Entity
@Table(name = "item", schema = "item_db")
@Data
public class Item implements Serializable{

    private static final long serialVersionUID = -9129154559271096509L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cl_id")
    private Long clId;

    @Column(name = "price")
    private Double price;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "last_modified_time")
    private Timestamp lastModifiedTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!id.equals(item.id)) return false;
        if (price != null ? !price.equals(item.price) : item.price != null) return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false;
        if (url != null ? !url.equals(item.url) : item.url != null) return false;
        return lastModifiedTime != null ? lastModifiedTime.equals(item.lastModifiedTime) : item.lastModifiedTime == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (lastModifiedTime != null ? lastModifiedTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", clId=" + clId +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
