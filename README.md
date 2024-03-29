<div align="center">
<a href="https://www.spigotmc.org/resources/110500/"><img src="https://img.shields.io/badge/Minecraft%20version-1.19.4_--_1.20.1-brightgreen.svg" alt="Minecraft version"></a>
<a href="https://www.spigotmc.org/resources/110500/reviews"><img src="https://img.shields.io/spiget/rating/110500?label=Spigot%20rating" alt="Spigot rating"></a>
<a href="https://www.spigotmc.org/resources/110500/"><img src="https://img.shields.io/spiget/downloads/110500?label=Spigot%20downloads" alt="Spigot downloads"></a>
<a href="https://bstats.org/plugin/bukkit/Fancy%20Physics/18833"><img src="https://img.shields.io/bstats/servers/18833" alt="bStats"></a>
</div>
<div align="center">
  <a href="https://github.com/max1mde/FancyPhysics/blob/master/LICENSE"><img src="https://img.shields.io/github/license/max1mde/FancyPhysics.svg" alt="License"></a>  
<a href="https://github.com/max1mde/FancyPhysics/releases"><img src="https://img.shields.io/github/v/tag/max1mde/FancyPhysics.svg" alt="Version"></a>  
<a href="https://jitpack.io/#max1mde/FancyPhysics"><img src="https://jitpack.io/v/max1mde/FancyPhysics.svg" alt="jitpack"></a>  
</div>

<div align="center">
<img src="https://github.com/max1mde/images/blob/main/323395728d1b2021a47c225be37ec656e13b1111_1.png?raw=true">
</div>

> [!NOTE]
> Works **without** a resourcepack/mod!

![image](https://github.com/max1mde/images/blob/main/Neues_Projekt_-_2023-06-15T233852.757.png?raw=true)
- Block particles
- Visual crafting
- Entity death particles
- Entity damage particles
- Realistic tree destroy animation
- Realistic explosions
- Trapdoor animation (Experimental)
- Sprint door break

![image](https://github.com/max1mde/images/blob/main/Neues_Projekt_-_2023-06-15T233623.864.png?raw=true)
```
/fancyphysics reload
/fancyphysics settings RealisticExplosion enable/disable
/fancyphysics settings EntityDeathParticles enable/disable
/fancyphysics settings 3DBlockParticles enable/disable
/fancyphysics settings DamageParticles enable/disable
/fancyphysics settings ParticleRotation enable/disable
/fancyphysics settings PerformanceMode enable/disable
/fancyphysics settings RealisticTrees enable/disable
/fancyphysics settings DropSaplings enable/disable
/fancyphysics settings MaxParticleCount <value>
/fancyphysics settings TrapdoorPhysics enable/disable
/fancyphysics settings SprintDoorBreak enable/disable
/fancyphysics settings SprintGlassBreak enable/disable
/fancyphysics settings VisualCrafting enable/disable
/fancyphysics settings NaturalDropsOnExplode enable/disable
/fancyphysics settings FlyUpParticles enable/disable
/fancyphysics settings FallingBlockPhysics enable/disable
/fancyphysics settings BlockCrackOnFall enable/disable
/fancyphysics settings BlockParticleBlackList add/remove <material>
/fancyphysics settings DisabledWorldsList add/remove <world> 
```

![image](https://github.com/max1mde/images/blob/main/Neues_Projekt_-_2023-06-15T233602.684.png?raw=true)
```
fancyphysics.admin
fancyphysics.commands
```

![image](https://github.com/max1mde/images/blob/main/statsfancyphysics%20(1).png?raw=true)
<br>
Spawn your own particles or cancel/modify existing particles using events in your plugin
<br>
[Read the documentation](https://github.com/MaximFiedler/FancyPhysics/blob/master/API.md)

![image](https://github.com/max1mde/images/blob/main/Neues_Projekt_99.png?raw=true)
- Download plugin
- Copy into the servers plugins folder
- Restart your server

![image](https://github.com/max1mde/images/blob/main/Neues_Projekt_100.png?raw=true)
```yml
Physics:
  RealisticExplosion: true
  EntityDeathParticles: true
  3DBlockParticles: true
  DamageParticles: true
  ParticleRotation: true
  PerformanceMode: true
  RealisticTrees: true
  DropSaplings: true
  VisualCrafting: true
  FallingBlockPhysics: true
  NaturalDropsOnExplode: true
  MaxParticleCount: 4000
  TrapdoorPhysics: false
  SprintDoorBreak: false
  SprintGlassBreak: false
  FlyUpParticles: false
  BlockCrackOnFall: true
  BlockParticleBlackList:
  - END_ROD
  ...
  DisabledWorldsList:
  - DisabledWorld
  - AnotherDisabledWorld
```

![image](https://github.com/max1mde/images/blob/main/Neues_Projekt_-_2023-06-15T233717.092.png?raw=true)

![image](https://imgur.com/5eyNF1F.gif)

![stats](https://github.com/max1mde/images/blob/main/Neues_Projekt_-_2023-06-15T233852.757_3.png?raw=true)

<a href="https://bstats.org/plugin/bukkit/Fancy%20Physics/18833">
  <img src="https://bstats.org/signatures/bukkit/Fancy%20Physics.svg" alt="bStats" width="500">
</a>
