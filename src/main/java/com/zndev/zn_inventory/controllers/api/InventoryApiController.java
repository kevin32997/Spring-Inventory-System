package com.zndev.zn_inventory.controllers.api;

import com.zndev.zn_inventory.helpers.Helper;
import com.zndev.zn_inventory.helpers.ResourceHelper;
import com.zndev.zn_inventory.models.main.Inventory;
import com.zndev.zn_inventory.models.main.Item;
import com.zndev.zn_inventory.models.other.Response;
import com.zndev.zn_inventory.repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class InventoryApiController {

    @Autowired
    private InventoryRepo inventoryRepo;

    @PostMapping("api/inventory/add")
    private Response addInventory(@ModelAttribute Inventory inventory) {
        try {
            Inventory savedInventory = inventoryRepo.save(inventory);
            List<Inventory> inventories = new ArrayList<>();

            inventories.add(savedInventory);
            return Helper.createResponse("Inventory Saved.", true, inventories);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Helper.createResponse("An Error Occurred\n" + ex.toString(), false);

        }
    }

    @GetMapping("api/inventory/view/{inventory_id}")
    private Response getInventoryById(@PathVariable("inventory_id") int inventoryId) {
        Optional<Inventory> optionalInventory = inventoryRepo.findById(inventoryId);
        List<Inventory> list = new ArrayList<>();
        list.add(optionalInventory.get());
        return Helper.createResponse("Request Successful", true, list);
    }

    @GetMapping("api/inventory/view/tag/{tag}/{id}")
    private Response getInventoryByTag(@PathVariable("tag") String tag, @PathVariable("id") int id) {
        List<Inventory> list = new ArrayList<>();

        switch (tag) {
            case ResourceHelper.TAG_ITEM:
                list = inventoryRepo.findByItemId(id);
                break;
            case ResourceHelper.TAG_LOCATION:
                list = inventoryRepo.findByLocationId(id);
                break;
        }
        return Helper.createResponse("Request Successful", true, list);
    }

    @GetMapping("api/inventory/view/byItemAndLocation/{item_id}/{location_id}")
    private Response getInventoryByItemAndLocation(@PathVariable("item_id") int itemId, @PathVariable("location_id") int locationId) {
        List<Inventory> list = inventoryRepo.findByItemIdAndLocationId(itemId, locationId);
        return Helper.createResponse("Request Successful", true, list);
    }

    @GetMapping("api/inventory/count/{tag}/{id}")
    private Map<String, Long> getInventoryCountByTag(@PathVariable("tag") String tag, @PathVariable("id") int id) {
        HashMap<String, Long> map = new HashMap<>();
        switch (tag) {
            case ResourceHelper.TAG_ITEM:
                // Get count by item
                map.put("count", inventoryRepo.countByItemId(id));
                break;
            case ResourceHelper.TAG_LOCATION:
                // Get count by location
                map.put("count", inventoryRepo.countByLocationId(id));
                break;
        }

        return map;
    }

    @GetMapping("api/inventory/count_total/{tag}/{id}")
    private Map<String, Integer> getInventoryTotalItemCountByTag(@PathVariable("tag") String tag, @PathVariable("id") int id) {
        HashMap<String, Integer> map = new HashMap<>();

        switch (tag) {
            case ResourceHelper.TAG_LOCATION:
                List<Inventory> list = inventoryRepo.findByLocationId(id);
                int count = 0;
                for (Inventory inventory : list) {
                    count += inventory.getQuantity();
                }
                map.put("count", count);
                break;
            case ResourceHelper.TAG_ITEM:
                List<Inventory> list2 = inventoryRepo.findByItemId(id);
                int count2 = 0;
                for (Inventory inventory : list2) {
                    count2 += inventory.getQuantity();
                }
                map.put("count", count2);
                break;
        }
        return map;
    }


    @GetMapping("api/inventory/pageByLocation/{location_id}/{page}/{size}")
    private Response getInventoryPageByLocation(@PathVariable("location_id") int locationId, @PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size);
        List<Inventory> inventoryPage = inventoryRepo.findAllByLocationId(locationId, pageRequest);
        return Helper.createResponse("Request Successful", true, inventoryPage);
    }


    @PutMapping("api/inventory/update/{inventory_id}")
    private Response updateInventory(@PathVariable("inventory_id") int inventoryId, @ModelAttribute Inventory details) {
        try {
            Optional<Inventory> optionalInventory = inventoryRepo.findById(inventoryId);
            Inventory inventory = optionalInventory.get();
            inventory.setQuantity(details.getQuantity());
            inventoryRepo.save(inventory);
            return Helper.createResponse("Inventory Updated.", true);
        } catch (Exception ex) {
            return Helper.createResponse("Error: " + ex.toString(), false);
        }
    }

    @DeleteMapping("api/inventory/delete/{inventory_id}")
    private Response deleteItem(@PathVariable("inventory_id") int inventory_id) {
        try {
            inventoryRepo.deleteById(inventory_id);
            return Helper.createResponse("Inventory Deleted", true);
        } catch (Exception ex) {
            return Helper.createResponse("Error: " + ex.toString(), false);
        }
    }


    //On Development - NEW CODES //////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("api/inventory/search/{search}/{location}/{size}")
    private Response searchItemInventoryByLocation(
            @PathVariable("search") String search,
            @PathVariable("location") int locationId,
            @PathVariable("size") int size) {

        Pageable pageRequest = PageRequest.of(0, size);
        List<Inventory> inventories = inventoryRepo.findByItem_nameContainingAndLocationId(search, locationId, pageRequest);


        return Helper.createResponse("Request Successful", true, inventories);
    }

    @GetMapping("api/inventory/search_location/byNameOrSerial/{location_id}/{searched}")
    private Response searchLocationInventoryByNameOrSerial(@PathVariable("location_id") int location_id, @PathVariable("searched") String searched) {

        Inventory inventory = inventoryRepo.findByLocationIdAndItem_nameOrItem_serialNumber(location_id, searched, searched);
        List<Inventory> list = new ArrayList<>();
        list.add(inventory);

        return Helper.createResponse("Request Successful", true, list);

    }

    @GetMapping("api/inventory/count_total/byItemAndLocation/{item_id}/{location_id}")
    private Map<String, Integer> getInventoryTotalItemCountByItemAndLocation(@PathVariable("item_id") int itemId, @PathVariable("location_id") int locationId) {
        HashMap<String, Integer> map = new HashMap<>();
        List<Inventory> list = inventoryRepo.findByItemIdAndLocationId(itemId, locationId);

        int count = 0;
        for (Inventory inventory : list) {
            count += inventory.getQuantity();
        }

        map.put("count", count);
        return map;
    }


}
