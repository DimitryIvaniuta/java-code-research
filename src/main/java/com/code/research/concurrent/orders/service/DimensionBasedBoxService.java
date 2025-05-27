package com.code.research.concurrent.orders.service;

import com.code.research.concurrent.orders.domain.OrderBox;
import com.code.research.concurrent.orders.exception.BoxNotFoundException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Chooses a box by simple item-count capacity rules.
 */
public class DimensionBasedBoxService implements BoxService {
    private static final Logger logger = Logger.getLogger(DimensionBasedBoxService.class.getName());
    private final List<BoxDefinition> catalog;

    public DimensionBasedBoxService(List<BoxDefinition> boxCatalog) {
        Objects.requireNonNull(boxCatalog, "boxCatalog");
        // Sort ascending by capacity so we pick the smallest fitting box
        this.catalog = new ArrayList<>(boxCatalog);
        this.catalog.sort(Comparator.comparingInt(BoxDefinition::getCapacity));
    }

    @Override
    public OrderBox chooseBox(List<String> itemSkus) throws BoxNotFoundException {
        int count = Objects.requireNonNull(itemSkus, "itemSkus").size();
        for (BoxDefinition def : catalog) {
            if (def.getCapacity() >= count) {
                logger.fine(() -> "Chose box " + def.getSize() + " for " + count + " items");
                return new OrderBox(def.getSize());
            }
        }
        throw new BoxNotFoundException("No box large enough for " + count + " items");
    }

    @Override
    public void packItems(OrderBox box, List<String> itemSkus) {
        // In a real impl you’d record each item’s placement; here we just log.
        for (String sku : itemSkus) {
            logger.fine(() -> "Packing SKU " + sku + " into box " + box.getSize());
        }
    }

    /**
     * Simple box definition: name & max item count capacity.
     */
    @Getter
    public static class BoxDefinition {
        private final String size;
        private final int capacity;

        public BoxDefinition(String size, int capacity) {
            this.size = Objects.requireNonNull(size, "size");
            this.capacity = capacity;
        }
    }
}
