package com.efr.shopManager.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "shopOrders")
@Entity
public class ShopUser {

    public interface SummaryView{}
    public interface DetailView extends SummaryView{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({SummaryView.class,DetailView.class})
    private Long id;

    @JsonView({SummaryView.class,DetailView.class})
    private String name;

    @Email(message = "Некорректный формат email")
    @JsonView({SummaryView.class,DetailView.class})
    private String email;

    @JsonView(DetailView.class)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ShopOrder> shopOrders = new ArrayList<>();

    public ShopUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
}