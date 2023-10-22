package com.soulrebel.blog.rest.model;

import java.util.Date;

public record ErrorDetails(Date timestamp, String message, String details) {
}
