package com.zndev.zn_inventory.controllers.api;


import com.zndev.zn_inventory.helpers.Helper;
import com.zndev.zn_inventory.models.main.Brand;
import com.zndev.zn_inventory.models.other.Response;
import com.zndev.zn_inventory.repository.BrandsRepo;
import lombok.Singular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BrandsApiController {

    @Autowired
    private BrandsRepo brandsRepo;

    @PostMapping("api/brands/add")
    private Response addBrand(@ModelAttribute Brand brand) {
        try {
            brandsRepo.save(brand);
            return Helper.createResponse("Brand Saved.",true);
        }catch (Exception ex){
            return Helper.createResponse("An Error Occurred\n"+ex.toString(),false);
        }
    }

    @GetMapping("api/brands/view/{brand_id}")
    private Response getBrandById(@PathVariable("brand_id") int brandId){
        Optional<Brand> optionalBrand=brandsRepo.findById(brandId);
        List<Brand> list=new ArrayList<>();
        list.add(optionalBrand.get());

        return Helper.createResponse("Request Successful",true,list);
    }

    @GetMapping("api/brands/page/{page}/{size}")
    private Response getBrandsByPage(@PathVariable("page") int page, @PathVariable("size") int size){
        Pageable pageRequest= PageRequest.of(page-1,size);
        Page<Brand> brandPage=brandsRepo.findAll(pageRequest);
        Response response=Helper.createResponse("Request Successful",true);
        response.setList(brandPage.getContent());
        return response;
    }

    @GetMapping("api/brands/search/{search_text}/{limit}")
    private Response searchBrands(@PathVariable("search_text") String searchText, @PathVariable("limit") int limit){
        List<Brand> searchedItems=brandsRepo.search("%"+searchText+"%",limit);
        Response response=Helper.createResponse("Request Successful",true);
        response.setList(searchedItems);
        return  response;
    }

    @GetMapping("api/brands/count")
    private Map<String,Long> getBrandsCount(){
        HashMap<String, Long> map = new HashMap<>();
        map.put("count", brandsRepo.count());
        return map;
    }

    @PutMapping("api/brands/update/{brand_id}")
    private Response updateBrand(@PathVariable("brand_id") int brandId, @ModelAttribute Brand details){
        try {
            Optional<Brand> optionalBrand = brandsRepo.findById(brandId);
            Brand brand=optionalBrand.get();
            brand.setName(details.getName());
            brand.setDescription(details.getDescription());
            brand.setDateUpdated(new Date());

            brandsRepo.save(brand);
            return Helper.createResponse("Brand Updated.",true);
        }catch (Exception ex){
            return Helper.createResponse("Error: "+ex.toString(),false);
        }
    };

    @DeleteMapping("api/brands/delete/{brand_id}")
    private Response deleteBrand(@PathVariable("brand_id") int brandId){
        try{
            brandsRepo.deleteById(brandId);
            return Helper.createResponse("Brand Deleted",true);
        }catch (Exception ex){
            return Helper.createResponse("Error: "+ex.toString(),false);
        }
    }
}
