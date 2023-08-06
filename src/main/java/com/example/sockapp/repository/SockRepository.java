package com.example.sockapp.repository;

import com.example.sockapp.entity.Sock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SockRepository extends JpaRepository<Sock,Long> {
    List<Sock> findSockByColorIgnoreCase(String color);

    Sock findSockByColorIgnoreCaseAndAndCottonPart(String color, int cottonPart);
}
