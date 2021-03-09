package com.zndev.zn_inventory.controllers.api;


import com.zndev.zn_inventory.helpers.Helper;
import com.zndev.zn_inventory.models.main.Brand;
import com.zndev.zn_inventory.models.main.Inventory;
import com.zndev.zn_inventory.models.main.InventoryActivityReference;
import com.zndev.zn_inventory.models.other.Response;
import com.zndev.zn_inventory.repository.InvReferenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class InvReferenceApiController {

    @Autowired
    private InvReferenceRepo invReferenceRepo;

    @PostMapping("api/inv_activity_ref/add")
    private Response addInvReference(@ModelAttribute InventoryActivityReference invRef) {
        try {
            InventoryActivityReference reference = invReferenceRepo.save(invRef);
            List<InventoryActivityReference> list = new ArrayList<>();
            list.add(reference);
            return Helper.createResponse("Inventory Reference Saved.", true, list);
        } catch (Exception ex) {
            return Helper.createResponse("An Error Occurred\n" + ex.toString(), false);
        }
    }


    @GetMapping("api/inv_activity_ref/view/{inv_ref_id}")
    private Response getInvReferenceById(@PathVariable("inv_ref_id") int invRefId) {
        Optional<InventoryActivityReference> optionalInvReference = invReferenceRepo.findById(invRefId);
        List<InventoryActivityReference> list = new ArrayList<>();
        list.add(optionalInvReference.get());
        return Helper.createResponse("Request Successful", true, list);
    }

    @GetMapping("api/inv_activity_ref/page/{page}/{size}")
    private Response getInvReferenceByPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size);
        Page<InventoryActivityReference> inventoryActivityReferencePage = invReferenceRepo.findAll(pageRequest);
        Response response = Helper.createResponse("Request Successful", true);
        response.setList(inventoryActivityReferencePage.getContent());
        return response;
    }

    @GetMapping("api/inv_activity_ref/search/{search_text}/{limit}")
    private Response searchInvReference(@PathVariable("search_text") String searchText, @PathVariable("limit") int limit) {
        List<InventoryActivityReference> searchedItems = invReferenceRepo.search("%" + searchText + "%", limit);
        Response response = Helper.createResponse("Request Successful", true);
        response.setList(searchedItems);
        return response;
    }

    @GetMapping("api/inv_activity_ref/count")
    private Map<String, Long> getInvReferenceCount() {
        HashMap<String, Long> map = new HashMap<>();
        map.put("count", invReferenceRepo.count());
        return map;
    }

    @GetMapping("api/inv_activity_ref/last_id")
    private Map<String, Integer> getInvActivityReferenceLastId() {
        HashMap<String, Integer> map = new HashMap<>();
        InventoryActivityReference reference = invReferenceRepo.findTopByOrderByIdDesc();
        if (reference != null) {
            map.put("last_id", reference.getId());
        } else {
            map.put("last_id", 0);
        }
        return map;
    }

    @GetMapping("api/inv_activity_ref/viewByLocation/{location_id}/{page}/{size}")
    private Response getInvActReferenceByLocation(@PathVariable("location_id") int locationId, @PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size);
        List<InventoryActivityReference> references = invReferenceRepo.findAllByLocationOrLocationFrom(locationId, locationId, pageRequest);
        return Helper.createResponse("Request Successful", true, references);
    }

    @GetMapping("api/inv_activity_ref/countByLocation/{location_id}")
    private Map<String, Integer> getInvActivityReferenceCountByLocation(@PathVariable("location_id") int locationId) {
        HashMap<String, Integer> map = new HashMap<>();
        int count = (int) invReferenceRepo.countByLocationOrLocationFrom(locationId, locationId);
        map.put("count", count);
        return map;
    }

    @DeleteMapping("api/inv_activity_ref/delete/{inv_ref_id}")
    private Response deleteInvReference(@PathVariable("inv_ref_id") int invReferenceId) {
        try {
            invReferenceRepo.deleteById(invReferenceId);
            return Helper.createResponse("Inventory Activity Reference Deleted", true);
        } catch (Exception ex) {
            return Helper.createResponse("Error: " + ex.toString(), false);
        }
    }

    private void sampleMethod(){

    }

}
