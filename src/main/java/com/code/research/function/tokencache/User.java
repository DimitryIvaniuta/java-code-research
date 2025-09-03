package com.code.research.function.tokencache;

import java.util.Set;

/** Minimal user model for the demo. */
record User(String id, String email, Set<String> roles) {}