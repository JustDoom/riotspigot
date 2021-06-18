package com.mojang.authlib;

public interface ProfileLookupCallback
{
    void onProfileLookupSucceeded(GameProfile p0);
    
    void onProfileLookupFailed(GameProfile p0, Exception p1);
}
