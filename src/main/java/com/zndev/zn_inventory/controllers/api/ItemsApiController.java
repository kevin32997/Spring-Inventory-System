package com.zndev.zn_inventory.controllers.api;

import com.zndev.zn_inventory.helpers.FileStorageHelper;
import com.zndev.zn_inventory.helpers.Helper;
import com.zndev.zn_inventory.helpers.ResourceHelper;
import com.zndev.zn_inventory.models.main.Brand;
import com.zndev.zn_inventory.models.main.Item;
import com.zndev.zn_inventory.models.main.Type;
import com.zndev.zn_inventory.models.other.Response;
import com.zndev.zn_inventory.repository.BrandsRepo;
import com.zndev.zn_inventory.repository.ItemsRepo;
import com.zndev.zn_inventory.repository.TypesRepo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
public class ItemsApiController {

    @Autowired
    private ItemsRepo itemsRepo;

    @Autowired
    private BrandsRepo brandsRepo;

    @Autowired
    private TypesRepo typesRepo;

    @Autowired
    private FileStorageHelper fileStorageHelper;

    @PostMapping("api/items/add")
    private Response addItem(@ModelAttribute Item item) {
        try {
            Item savedItem = itemsRepo.save(item);
            List<Item> items = new ArrayList<>();

            items.add(savedItem);
            return Helper.createResponse("Item Saved.", true, items);
        } catch (Exception ex) {
            return Helper.createResponse("An Error Occurred\n" + ex.toString(), false);
        }
    }


    @PostMapping("/api/items/image/update/{item_id}")
    public Response uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("item_id") int itemId) {

        String randomImageName = Helper.GenerateRandomString(20);
        System.out.println("Random Generated String is " + randomImageName);
        String fileName = fileStorageHelper.storeFile(file, randomImageName);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/items/image/")
                .path(fileName)
                .toUriString();
        System.out.println("Download Uri: " + fileDownloadUri);

        // Update Item
        Optional<Item> itemOptional = itemsRepo.findById(itemId);
        itemOptional.get().setImage(fileName);
        itemsRepo.save(itemOptional.get());
        return Helper.createResponse("Image update successful", true);
    }

    /*
    @PostMapping("api/items/update/image/{item_id}")
    private Response updateItemImage(@PathVariable("item_id") int itemId, @RequestParam("image") MultipartFile multipartFile) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            Optional<Item> optionalItem = itemsRepo.findById(itemId);
            // Save Image
            Helper.saveFile("images/items",fileName,multipartFile);
            optionalItem.get().setImage(fileName);
            Item savedItem=itemsRepo.save(optionalItem.get());
            return Helper.createResponse("Item Image Saved",true);
        }catch (IOException ex){
            return Helper.createResponse("Server Error\n " + ex.toString(), false);
        }
        catch (Exception ex) {
            return Helper.createResponse("An Error Occurred\n" + ex.toString(), false);
        }
    }
    */

    @GetMapping("/api/items/image/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageHelper.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
            System.out.println("Could not determine file type.");

        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("api/items/view/{item_id}")
    private Response getItemById(@PathVariable("item_id") int itemId) {
        Optional<Item> optionalItem = itemsRepo.findById(itemId);
        Item item = optionalItem.get();

        List<Item> list = new ArrayList<>();
        try {
            Optional<Brand> optionalBrand = brandsRepo.findById(item.getBrandId());
            item.setBrandName(optionalBrand.get().getName());
        } catch (NoSuchElementException ex) {
            // do nothing or add action code

            item.setBrandName("No Data found");
        }
        try {
            Optional<Type> optionalType = typesRepo.findById(item.getTypeId());
            item.setTypeName(optionalType.get().getName());
        } catch (NoSuchElementException ex) {
            // do nothing or add action code
            item.setTypeName("No Data found");
        }
        list.add(item);


        return Helper.createResponse("Request Successfull", true, list);
    }

    @GetMapping("api/items/page/{page}/{size}")
    private Response getItemsByPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size);
        Page<Item> itemPage = itemsRepo.findAll(pageRequest);

        for (Item item : itemPage.getContent()) {
            try {
                Optional<Brand> optionalBrand = brandsRepo.findById(item.getBrandId());
                item.setBrandName(optionalBrand.get().getName());
            } catch (NoSuchElementException ex) {
                // do nothing or add action code

                item.setBrandName("No Data found");
            }
            try {
                Optional<Type> optionalType = typesRepo.findById(item.getTypeId());
                item.setTypeName(optionalType.get().getName());
            } catch (NoSuchElementException ex) {
                // do nothing or add action code
                item.setTypeName("No Data found");
            }
        }

        Response response = Helper.createResponse("Request Successful", true);
        response.setList(itemPage.getContent());
        return response;
    }

    @GetMapping("api/items/pageByTag/{tag}/{id}/{page}/{size}")
    private Response getItemsPageByBrand(@PathVariable("tag") String tag, @PathVariable("id") int id, @PathVariable("page") int page, @PathVariable("size") int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size);
        List<Item> items = new ArrayList<>();

        switch (tag) {
            case ResourceHelper.TAG_BRAND:
                items = itemsRepo.findAllByBrandId(id, pageRequest);
                break;
            case ResourceHelper.TAG_TYPE:
                items = itemsRepo.findAllByTypeId(id, pageRequest);
                break;
        }


        for (Item item : items) {
            try {
                Optional<Brand> optionalBrand = brandsRepo.findById(item.getBrandId());
                item.setBrandName(optionalBrand.get().getName());
            } catch (NoSuchElementException ex) {
                // do nothing or add action code

                item.setBrandName("No Data found");
            }
            try {
                Optional<Type> optionalType = typesRepo.findById(item.getTypeId());
                item.setTypeName(optionalType.get().getName());
            } catch (NoSuchElementException ex) {
                // do nothing or add action code
                item.setTypeName("No Data found");
            }
        }

        Response response = Helper.createResponse("Request Successful", true);
        response.setList(items);
        return response;
    }

    @GetMapping("api/items/search/{search_text}/{limit}")
    private Response searchItem(@PathVariable("search_text") String searchText, @PathVariable("limit") int limit) {
        List<Item> searchedItems = itemsRepo.search("%" + searchText + "%", limit);
        for (Item item : searchedItems) {
            try {
                Optional<Brand> optionalBrand = brandsRepo.findById(item.getBrandId());
                item.setBrandName(optionalBrand.get().getName());
            } catch (NoSuchElementException ex) {
                // do nothing or add action code

                item.setBrandName("No Data found");
            }
            try {
                Optional<Type> optionalType = typesRepo.findById(item.getTypeId());
                item.setTypeName(optionalType.get().getName());
            } catch (NoSuchElementException ex) {
                // do nothing or add action code
                item.setTypeName("No Data found");
            }
        }

        return Helper.createResponse("Request Successful", true, searchedItems);
    }

    @GetMapping("api/items/count")
    private Map<String, Long> getItemsCount() {

        HashMap<String, Long> map = new HashMap<>();
        map.put("count", itemsRepo.count());
        return map;
    }

    @GetMapping("api/items/count/{tag}/{id}")
    private Map<String, Long> getItemsCountByTag(@PathVariable("tag") String tag, @PathVariable("id") int id) {
        HashMap<String, Long> map = new HashMap<>();

        switch (tag) {
            case ResourceHelper.TAG_BRAND:
                map.put("count", itemsRepo.countByBrandId(id));
                break;
            case ResourceHelper.TAG_TYPE:
                map.put("count", itemsRepo.countByTypeId(id));
        }

        return map;
    }


    @GetMapping(value = "api/items/image/view", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage() throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("motherfucker.jpg");
        return IOUtils.toByteArray(in);
    }

    @PutMapping("api/items/update/{item_id}")
    private Response updateItem(@PathVariable("item_id") int itemId, @ModelAttribute Item details) {
        try {
            Optional<Item> optionalItem = itemsRepo.findById(itemId);
            Item item = optionalItem.get();
            item.setBrandId(details.getBrandId());
            item.setCode(details.getCode());
            item.setModel(details.getModel());
            item.setPropertyNumber(details.getPropertyNumber());
            item.setRemarks(details.getRemarks());
            item.setSerialNumber(details.getSerialNumber());
            item.setTypeId(details.getTypeId());
            item.setDateUpdated(new Date());
            itemsRepo.save(item);

            List<Item> list = new ArrayList<>();
            list.add(item);

            return Helper.createResponse("Item Updated.", true, list);
        } catch (Exception ex) {
            return Helper.createResponse("Error: " + ex.toString(), false);
        }
    }


    @DeleteMapping("api/items/delete/{item_id}")
    private Response deleteItem(@PathVariable("item_id") int itemId) {
        try {
            itemsRepo.deleteById(itemId);
            return Helper.createResponse("Item Deleted", true);
        } catch (Exception ex) {
            return Helper.createResponse("Error: " + ex.toString(), false);
        }
    }



    /*
        New Codes | Debugging
     */


}
