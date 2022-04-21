package com.myapp.web.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("BACKEND1")
public interface AnotherClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/another")
    List<AnotherDTO> getAll();
}
