package com.zndev.zn_inventory.controllers.api;

import com.zndev.zn_inventory.helpers.Helper;
import com.zndev.zn_inventory.models.main.Location;
import com.zndev.zn_inventory.models.other.Response;
import com.zndev.zn_inventory.repository.LocationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LocationsApiController {

    @Autowired
    private LocationsRepo locationsRepo;

    @PostMapping("api/locations/add")
    private Response addLocation(@ModelAttribute Location location) {
        try {
            Location savedLocation=locationsRepo.save(location);
            List<Location> list=new ArrayList<>();
            list.add(savedLocation);
            return Helper.createResponse("Location Saved.",true,list);
        }catch (Exception ex){
            return Helper.createResponse("An Error Occurred\n"+ex.toString(),false);
        }
    }

    @GetMapping("api/locations/view/{location_id}")
    private Response getLocationById(@PathVariable("location_id") int locationId){
        Optional<Location> optionalLocation=locationsRepo.findById(locationId);
        List<Location> list=new ArrayList<>();
        list.add(optionalLocation.get());
        return Helper.createResponse("Request Successful",true,list);
    }

    @GetMapping("api/locations/view")
    private Response getAllLocation(){
        List<Location> list= locationsRepo.findAll();
        return Helper.createResponse("Request Successful",true,list);
    }

    @GetMapping("api/locations/page/{page}/{size}")
    private Response getLocationByPage(@PathVariable("page") int page, @PathVariable("size") int size){
        Pageable pageRequest= PageRequest.of(page-1,size);
        Page<Location> locationPage=locationsRepo.findAll(pageRequest);
        Response response=Helper.createResponse("Request Successful",true);
        response.setList(locationPage.getContent());
        return response;
    }

    @GetMapping("api/locations/search/{search_text}/{limit}")
    private Response searchLocations(@PathVariable("search_text") String searchText, @PathVariable("limit") int limit){
        List<Location> searchedItems=locationsRepo.search("%"+searchText+"%",limit);
        Response response=Helper.createResponse("Request Successful",true);
        response.setList(searchedItems);
        return  response;
    }

    @GetMapping("api/locations/count")
    private Map<String,Long> getLocationsCount(){
        HashMap<String, Long> map = new HashMap<>();
        map.put("count", locationsRepo.count());
        return map;
    }

    @PutMapping("api/locations/update/{location_id}")
    private Response updateLocation(@PathVariable("location_id") int locationId, @ModelAttribute Location details){
        try {
            Optional<Location> optionalLocation = locationsRepo.findById(locationId);
            Location location=optionalLocation.get();
            location.setName(details.getName());
            location.setDescription(details.getDescription());
            location.setDateUpdated(new Date());

            locationsRepo.save(location);
            return Helper.createResponse("Location Updated.",true);
        }catch (Exception ex){
            return Helper.createResponse("Error: "+ex.toString(),false);
        }
    };

    @DeleteMapping("api/locations/delete/{location_id}")
    private Response deleteBrand(@PathVariable("location_id") int locationId){
        try{
            locationsRepo.deleteById(locationId);
            return Helper.createResponse("Location Deleted",true);
        }catch (Exception ex){
            return Helper.createResponse("Error: "+ex.toString(),false);
        }
    }
}
