package com.grainger.cimprofile.interview.userprofile.controllers.resources;


import java.util.UUID;

public record Profile(UUID id, String name, String email) {
}
