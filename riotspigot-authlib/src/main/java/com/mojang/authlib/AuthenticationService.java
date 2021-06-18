package com.mojang.authlib;

import com.mojang.authlib.minecraft.MinecraftSessionService;

public interface AuthenticationService
{
    UserAuthentication createUserAuthentication(Agent p0);
    
    MinecraftSessionService createMinecraftSessionService();
    
    GameProfileRepository createProfileRepository();
}
