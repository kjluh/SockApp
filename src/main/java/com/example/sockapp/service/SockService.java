package com.example.sockapp.service;

import com.example.sockapp.entity.Sock;
import com.example.sockapp.entity.Comparison;
import com.example.sockapp.repository.SockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SockService {

    private SockRepository repository;

    public SockService(SockRepository repository) {
        this.repository = repository;
    }

    public String saveSock(String color, int cottonPart, int quantity) {
            Sock sock = repository.findSockByColorIgnoreCaseAndAndCottonPart(color, cottonPart);
        if (null != sock) {
            sock.setQuantity(sock.getQuantity() + quantity);
        } else {
            sock = new Sock();
            sock.setQuantity(quantity);
            sock.setColor(color);
            sock.setCottonPart(cottonPart);
        }
        repository.save(sock);
        return "носки добавлены";
    }

    public void deleteSock(String color, int cottonPart, int quantity) {
        Sock sock = repository.findSockByColorIgnoreCaseAndAndCottonPart(color, cottonPart);
        sock.setQuantity(sock.getQuantity() - quantity);
        repository.save(sock);
    }


    public String getSock(String color, Comparison com, int cottonPart) {
        List<Sock> sockList = repository.findSockByColorIgnoreCase(color);
        return "Количество носков цвета " + checkColor(color) +
                " с долей хлопка " + checkEqual(com) + " " + cottonPart +
                " на складе = " + x(sockList, com, cottonPart).stream().mapToInt(Sock::getQuantity).sum() + " шт.";
    }

    private List<Sock> x(List<Sock> sockList, Comparison com, int cottonPart) {
        switch (com) {
            case equal:
                return sockList.stream().filter(e -> e.getCottonPart() == cottonPart).collect(Collectors.toList());
            case lessThan:
                return sockList.stream().filter(e -> e.getCottonPart() < cottonPart).collect(Collectors.toList());
            case moreThan:
                return sockList.stream().filter(e -> e.getCottonPart() > cottonPart).collect(Collectors.toList());
        }
        return sockList;
    }

    private String checkColor(String s) {
        switch (s) {
            case "red":
                return "красный";
            case "black":
                return "черный";
            case "yellow":
                return "желтый";
        }
        return "хз";
    }

    private String checkEqual(Comparison s) {
        switch (s) {
            case equal:
                return "равной";
            case moreThan:
                return "более";
            case lessThan:
                return "менее";
        }
        return "хз";
    }
}
