package com.code.research.function.tokencache.facade;

import java.util.Set;

public record User(String id, String email, Set<String> roles) {}