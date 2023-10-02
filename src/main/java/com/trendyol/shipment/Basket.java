package com.trendyol.shipment;

import java.util.*;
import java.util.stream.Collectors;

public class Basket {
    private List<Product> products;

    public ShipmentSize getShipmentSize() {

        if (getProducts().size() < 3) {
            if (getProducts().isEmpty()) {
                return null;
            }

            return getProducts().stream()
                    .map(Product::getSize)
                    .max(Comparator.naturalOrder())
                    .orElse(null);
        } else {
            Map<ShipmentSize, Long> sizeOfMap = getProducts().stream()
                    .collect(Collectors.groupingBy(Product::getSize, Collectors.counting()));

            if (sizeOfMap.containsKey(ShipmentSize.X_LARGE) && sizeOfMap.get(ShipmentSize.X_LARGE) >= 3) {
                return ShipmentSize.X_LARGE;
            }

            Optional<Map.Entry<ShipmentSize, Long>> numberOfFrequency = sizeOfMap.entrySet().stream()
                    .max(Comparator.comparing(Map.Entry::getValue));

            if (numberOfFrequency.isPresent()) {
                Map.Entry<ShipmentSize, Long> size = numberOfFrequency.get();
                if (size.getValue() >= 3) {
                    if (size.getKey() == ShipmentSize.SMALL) {
                        return ShipmentSize.MEDIUM;
                    } else if (size.getKey() == ShipmentSize.MEDIUM) {
                        return ShipmentSize.LARGE;
                    } else if (size.getKey() == ShipmentSize.LARGE) {
                        return ShipmentSize.X_LARGE;
                    } else {
                        return null;
                    }
                }else{
                    if (getProducts().isEmpty()) {
                        return null;
                    }

                    return getProducts().stream()
                            .map(Product::getSize)
                            .max(Comparator.naturalOrder())
                            .orElse(null);
                }

            } else {
                return null;
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}