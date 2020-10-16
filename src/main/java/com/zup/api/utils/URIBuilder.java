package com.zup.api.utils;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class URIBuilder {
    public static URI getLocationURI(String path, String id) {
        if (id == null) {
            return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path)
                .build()
                .toUri();
        }

        return ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path(path)
            .buildAndExpand(id)
            .toUri();
    }
}
