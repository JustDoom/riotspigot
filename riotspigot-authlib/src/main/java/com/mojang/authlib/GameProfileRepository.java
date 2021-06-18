package com.mojang.authlib;

public interface GameProfileRepository
{
    void findProfilesByNames(String[] p0, Agent p1, ProfileLookupCallback p2);
}
