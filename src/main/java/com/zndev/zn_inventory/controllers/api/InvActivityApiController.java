package com.zndev.zn_inventory.controllers.api;

import com.zndev.zn_inventory.helpers.Helper;
import com.zndev.zn_inventory.models.main.InventoryActivity;
import com.zndev.zn_inventory.models.other.Response;
import com.zndev.zn_inventory.repository.InventoryActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class InvActivityApiController {

    @Autowired
    private InventoryActivityRepo invActivityRepo;

    @PostMapping("api/inv_activity/add")
    private Response addInventoryActivity(@ModelAttribute InventoryActivity activity) {
        try {
            invActivityRepo.save(activity);
            return Helper.createResponse("Inventory Activity Saved.", true);
        } catch (Exception ex) {
            return Helper.createResponse("An Error Occurred\n" + ex.toString(), false);
        }
    }


    @GetMapping("api/inv_activity/view/{inv_act_id}")
    private Response getInvActivityBy(@PathVariable("inv_act_id") int invActivityId) {
        Optional<InventoryActivity> optionalInventoryActivity = invActivityRepo.findById(invActivityId);
        List<InventoryActivity> list = new ArrayList<>();
        list.add(optionalInventoryActivity.get());
        return Helper.createResponse("Request Successful", true, list);
    }

    @GetMapping("api/inv_activity/page/{page}/{size}")
    private Response getInvActivityByPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size);
        Page<InventoryActivity> inventoryActivities = invActivityRepo.findAll(pageRequest);
        Response response = Helper.createResponse("Request Successful", true);
        response.setList(inventoryActivities.getContent());
        return response;
    }

    @GetMapping("api/inv_activity/count")
    private Map<String, Long> getInvReferenceCount() {
        HashMap<String, Long> map = new HashMap<>();
        map.put("count", invActivityRepo.count());
        return map;
    }

    @GetMapping("api/inv_activity/last_id")
    private Map<String, Integer> getInvActivityLastId() {
        HashMap<String, Integer> map = new HashMap<>();
        InventoryActivity inventoryActivity = invActivityRepo.findTopByOrderByIdDesc();
        if (inventoryActivity != null) {
            map.put("last_id", inventoryActivity.getId());
        } else {
            map.put("last_id", 0);
        }
        return map;
    }


    @DeleteMapping("api/inv_activity/delete/{inv_act_id}")
    private Response deleteInventoryActivity(@PathVariable("inv_act_id") int invActivityId) {
        try {
            invActivityRepo.deleteById(invActivityId);
            return Helper.createResponse("Inventory Activity Deleted", true);
        } catch (Exception ex) {
            return Helper.createResponse("Error: " + ex.toString(), false);
        }
    }

}
