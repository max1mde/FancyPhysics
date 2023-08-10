<div align="center">
<a href="https://www.spigotmc.org/resources/110500/"><img src="https://img.shields.io/badge/Minecraft%20version-1.20_--_1.20.1-brightgreen.svg" alt="Minecraft version"></a>
<a href="https://www.spigotmc.org/resources/110500/reviews"><img src="https://img.shields.io/spiget/rating/110500?label=Spigot%20rating" alt="Spigot rating"></a>
<a href="https://www.spigotmc.org/resources/110500/"><img src="https://img.shields.io/spiget/downloads/110500?label=Spigot%20downloads" alt="Spigot downloads"></a>
<a href="https://bstats.org/plugin/bukkit/Fancy%20Physics/18833"><img src="https://img.shields.io/bstats/servers/18833" alt="bStats"></a>
</div>
<div align="center">
  <a href="https://github.com/MaximFiedler/FancyPhysics/blob/master/LICENSE"><img src="https://img.shields.io/github/license/MaximFiedler/FancyPhysics.svg" alt="License"></a>  
<a href="https://github.com/MaximFiedler/FancyPhysics/releases"><img src="https://img.shields.io/github/v/tag/MaximFiedler/FancyPhysics.svg" alt="Version"></a>  
<a href="https://jitpack.io/#MaximFiedler/FancyPhysics"><img src="https://jitpack.io/v/MaximFiedler/FancyPhysics.svg" alt="jitpack"></a>  
</div>


<div align="center">
<img src="https://media.discordapp.net/attachments/1052241511795937381/1119002915026260038/323395728d1b2021a47c225be37ec656e13b1111_1.png?width=937&height=262">
</div>

<h2 align="center">No resourcepack or mod needed!</h2>

![image](https://media.discordapp.net/attachments/1052241511795937381/1119003156915945502/Neues_Projekt_-_2023-06-15T233852.757.png?width=250&height=125)
- Block particles
- Visual crafting
- Entity death particles
- Entity damage particles
- Realistic tree destroy animation
- Realistic explosions
- Trapdoor animation (Experimental)
- Sprint door break

![image](https://media.discordapp.net/attachments/1052241511795937381/1119002916070629567/Neues_Projekt_-_2023-06-15T233623.864.png?width=250&height=125)
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
/fancyphysics settings VisualCrafting enable/disable
/fancyphysics settings NaturalDropsOnExplode enable/disable
/fancyphysics settings FlyUpParticles enable/disable
/fancyphysics settings FallingBlockPhysics enable/disable
/fancyphysics settings BlockParticleBlackList <material> add/remove
```

![image](https://media.discordapp.net/attachments/1052241511795937381/1119002916326490262/Neues_Projekt_-_2023-06-15T233602.684.png?width=250&height=125)
```
fancyphysics.admin
fancyphysics.commands
```

![image](https://media.discordapp.net/attachments/1052241511795937381/1127982083894157322/statsfancyphysics_1.png?width=250&height=125)
<br>
Spawn your own particles or cancel/modify existing particles using events in your plugin
<br>
[Read the documentation](https://github.com/MaximFiedler/FancyPhysics/blob/master/API.md)

![image](https://media.discordapp.net/attachments/1052241511795937381/1119002917005959300/Neues_Projekt_99.png?width=250&height=125)
- Download plugin
- Copy into the servers plugins folder
- Restart your server

![image](https://media.discordapp.net/attachments/1052241511795937381/1119002916662038538/Neues_Projekt_100.png?width=250&height=125)
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
  FlyUpParticles: false
  BlockParticleBlackList:
  - END_ROD
  ...
```

![image](https://media.discordapp.net/attachments/1052241511795937381/1119002915328237599/Neues_Projekt_-_2023-06-15T233717.092.png?width=250&height=125)

![image](https://imgur.com/5eyNF1F.gif)

![stats](https://media.discordapp.net/attachments/1052241511795937381/1121746751855001650/Neues_Projekt_-_2023-06-15T233852.757_3.png?width=250&height=125)

<a href="https://bstats.org/plugin/bukkit/Fancy%20Physics/18833">
  <img src="https://bstats.org/signatures/bukkit/Fancy%20Physics.svg" alt="bStats" width="500">
</a>
