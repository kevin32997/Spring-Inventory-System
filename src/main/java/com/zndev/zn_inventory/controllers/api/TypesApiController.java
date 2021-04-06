package com.zndev.zn_inventory.controllers.api;

import com.zndev.zn_inventory.helpers.Helper;
import com.zndev.zn_inventory.helpers.ResourceHelper;
import com.zndev.zn_inventory.models.main.Type;
import com.zndev.zn_inventory.models.other.Response;
import com.zndev.zn_inventory.repository.TypesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TypesApiController {

    @Autowired
    private TypesRepo typesRepo;

    @PostMapping("api/types/add")
    private Response addType(@ModelAttribute Type type) {
        try {
            Type savedType = typesRepo.save(type);
            List<Type> typeList = new ArrayList<>();
            typeList.add(savedType);
            return Helper.createResponse("Type Saved.", true, typeList);
        } catch (Exception ex) {
            return Helper.createResponse("An Error Occurred\n" + ex.toString(), false);
        }
    }

    @GetMapping("api/types/view/{type_id}")
    private Type getTypeById(@PathVariable("type_id") int typeId) {
        Optional<Type> optionalType = typesRepo.findById(typeId);
        return optionalType.get();
    }

    @GetMapping("api/types/page/{page}/{size}")
    private Response getTypesByPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size);
        Page<Type> typePage = typesRepo.findAll(pageRequest);
        Response response = Helper.createResponse("Request Successful", true);
        response.setList(typePage.getContent());
        return response;
    }

    @GetMapping("api/types/page/{page}/{size}/sort/{sortType}/{sortBy}")
    private Response getTypesByPageSorted(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortType") String sortType,
            @PathVariable("sortBy") String sortBy) {

        Sort sort = null;

        if (sortType.equals(ResourceHelper.DIRECTION_ASCENDING)) {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        } else if (sortType.equals(ResourceHelper.DIRECTION_DESCENDING)) {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        Pageable pageRequest = PageRequest.of(page - 1, size, sort);
        Page<Type> brandPage = typesRepo.findAll(pageRequest);
        Response response = Helper.createResponse("Request Successful", true);
        response.setList(brandPage.getContent());
        return response;
    }


    @GetMapping("api/types/search/{search_text}/{limit}")
    private Response searchType(@PathVariable("search_text") String searchText, @PathVariable("limit") int limit) {
        List<Type> searchedItems = typesRepo.search("%" + searchText + "%", limit);
        Response response = Helper.createResponse("Request Successful", true);
        response.setList(searchedItems);
        return response;
    }

    @GetMapping("api/types/count")
    private Map<String, Long> getTypesCount() {
        HashMap<String, Long> map = new HashMap<>();
        map.put("count", typesRepo.count());
        return map;
    }

    @PutMapping("api/types/update/{type_id}")
    private Response updateType(@PathVariable("type_id") int typeId, @ModelAttribute Type details) {
        try {
            Optional<Type> optionalType = typesRepo.findById(typeId);
            Type type = optionalType.get();
            type.setName(details.getName());
            type.setDescription(details.getDescription());
            type.setDateUpdated(new Date());

            typesRepo.save(type);
            return Helper.createResponse("Type Updated.", true);
        } catch (Exception ex) {
            return Helper.createResponse("Error: " + ex.toString(), false);
        }
    }

    @DeleteMapping("api/types/delete/{type_id}")
    private Response deleteType(@PathVariable("type_id") int typeId) {
        try {
            typesRepo.deleteById(typeId);
            return Helper.createResponse("Type Deleted", true);
        } catch (Exception ex) {
            return Helper.createResponse("Error: " + ex.toString(), false);
        }
    }
}
