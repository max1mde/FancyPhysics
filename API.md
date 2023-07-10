<p align="center">
  <a href="https://github.com/MaximFiedler/FancyPhysics/blob/master/LICENSE"><img src="https://img.shields.io/github/license/MaximFiedler/FancyPhysics.svg" alt="License"></a>  
<a href="https://github.com/MaximFiedler/FancyPhysics/releases"><img src="https://img.shields.io/github/v/tag/MaximFiedler/FancyPhysics.svg" alt="Version"></a>  
<a href="https://jitpack.io/#MaximFiedler/FancyPhysics"><img src="https://jitpack.io/v/MaximFiedler/FancyPhysics.svg" alt="jitpack"></a>  
</p>


Implementation
------------

Gradle

	repositories {
		maven { url 'https://jitpack.io' }
	}

	dependencies {
	        implementation 'com.github.MaximFiedler:FancyPhysics:2.0.2'
	}
Maven

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

 	<dependency>
	    <groupId>com.github.MaximFiedler</groupId>
	    <artifactId>FancyPhysics</artifactId>
	    <version>2.0.2</version>
	</dependency>

-----

# Examples
--------------------------------------------------------------------
FancyPhysics instance
```java
API fancyPhysicsAPI = FancyPhysics.getAPI();
```
Particles
```java
fancyPhysicsAPI.getParticleGenerator().simulateBlockParticles(location, material);
fancyPhysicsAPI.getParticleGenerator().simulateBloodParticles(location, material);
fancyPhysicsAPI.getParticleGenerator().simulateSplashBloodParticles(location, material);
```
Config
```java
if(fancyPhysicsAPI.getConfig().isRealisticExplosion()) {
	// Do something
}
```
Particle list
```java
// For example remove all currently existing particles
for(BlockDisplay blockDisplay : fancyPhysicsAPI.getBlockDisplayList()) {
	blockDisplay.remove();
}
fancyPhysicsAPI.getBlockDisplayList().clear();
```
--------------------------------------------------------------------
### Events
ParticleSpawnEvent
```java
    @EventHandler
    public void onParticleSpawnEvent(ParticleSpawnEvent event) {
        // Changes the particle texture to a diamond block
        BlockDisplay display = event.getParticleDisplay();
        display.setBlock(Material.DIAMOND_BLOCK.createBlockData());
    }
```
ParticleEffectEvent
```java
    @EventHandler
    public void onParticleEffect(ParticleEffectEvent event) {
        // Do something with the list
        List<BlockDisplay> displayList = event.getParticleDisplayList();

        // You can cancel every fancy physics event
        if(event.getLocation().getWorld().getName().equals("your_custom_world")) {
            event.setCancelled(true);
        }
    }
```
--------------------------------------------------------------------
