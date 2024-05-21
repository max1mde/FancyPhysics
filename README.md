<div align="center">
<a href="https://www.spigotmc.org/resources/110500/"><img src="https://img.shields.io/badge/Minecraft%20version-1.19.4_--_1.20.4-brightgreen.svg" alt="Minecraft version"></a>
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

# Links:
Modrinth: https://modrinth.com/plugin/fancy-physics  
SpigotMC: https://www.spigotmc.org/resources/110500


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
# Fancy Physics sounds like for block crack
Sounds: true
Explosion:
  Physics: true
  NaturalDrops: true
EntityDeathParticles: true
# Block break particles
Particle:
  Enabled: true
  Animation:
    # Rotate the particles
    Rotation: true
    # Blocks will just fly up and get smaller when broken
    FlyUp: false
    SpeedInTicks: 40
    EndSizeMultiplier: 0.5
  # How many particles can exist at once on the entire server
  MaxAmount: 4000
DamageParticles: true
PerformanceMode: true
Tree:
  Physics: true
  DropSaplings: true
  # This adds like multiple stages to the broken block to make it longer to fell a tree
  ChopDelay: true
  # If true a tree will fall on the ground if a block was broken below it
  GravityIfInAir: true
  # Adds all the broken blocks of a tree when fallen to the players stats
  AffectedBlocksInPlayerBreakBlocksStatistic: true
  AdvancedStemScan: false
  ScanMaxStemSize: 200
  ScanMaxLeavesSize: 260
  MaxInvalidScans: 2700
  MaxInvalidBlockDistance: 2
# Displays the currently used items in a crafting table above the actual block (One player limit)
VisualCrafting: true
# Breaks all blocks when falling
FallingBlockPhysics: true
# Adds the particle animation to blocks which fall
BlockCrackOnFall: true
# Adds a small opening and closing animation to trapdoors.. Just use this for testing not on a production server
TrapdoorPhysics: false
# When sprinting against glass or a door it will break
SprintBreak:
  Door: false
  Glass: false
# Regenerate certain blocks or trees when destroyed after a specific delay
Regeneration:
  TreeRegeneration:
    Enabled: false
    Delay: 10
  ExplosionRegeneration:
    Enabled: false
    Delay: 10
# There will be no particles for the following blocks when broken
BlockParticleBlackList:
  - END_ROD
  - POINTED_DRIPSTONE
....
# In these worlds no physics will be applied
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
