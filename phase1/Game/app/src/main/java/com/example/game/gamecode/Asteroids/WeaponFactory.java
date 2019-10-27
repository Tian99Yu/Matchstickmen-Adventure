package com.example.game.gamecode.Asteroids;

public class WeaponFactory {
     public WeaponSystem getWeapon(WeaponType weaponType) {
       switch (weaponType) {
         case DEFAULT_CANNON:
           return new AutoCannon(15, 0.1, 5, 1, 90, 1 );
         default:
           return null;
       }
     }
}
