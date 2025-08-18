package org.yunusgedik.user.Model.User;

public record LoginRequest (
    String email,
    String password
){}
